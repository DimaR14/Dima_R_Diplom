package my.diploma.demo.chatbot;

import my.diploma.demo.objects.User;
import my.diploma.demo.objects.MyTransaction;
import my.diploma.demo.objects.Title;
import my.diploma.demo.service.UserService;
import my.diploma.demo.service.MyTransactionService;
import my.diploma.demo.service.TitleService;
import my.diploma.demo.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Date;
import java.util.List;


@Component
@PropertySource("classpath:telegram.properties")
public class ChatBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LogManager.getLogger(ChatBot.class);

    private static final String RESTART = "restart";

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    private final UserService userService;
    private final AccountService accountService;
    private final TitleService titleService;
    private final MyTransactionService myTransactionService;

    public ChatBot(UserService userService, AccountService accountService, TitleService titleService, MyTransactionService myTransactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.titleService = titleService;
        this.myTransactionService = myTransactionService;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText())
            return;
        ;
        final String text = update.getMessage().getText();
        final long chatId = update.getMessage().getChatId();

        User user = userService.findByChatId(chatId);

        if (checkIfCommand(user, text))
            return;

        BotContext context;
        BotState state;

        if (user == null) {
            state = BotState.getInitialState();

            user = new User(chatId, state.ordinal());
            userService.addUser(user);

            context = BotContext.of(this, user, text);
            state.enter(context);

            LOGGER.info("NEW user register:" + chatId);
        } else {
            context = BotContext.of(this, user, text);
            state = BotState.byId(user.getStateId());

            LOGGER.info("UPDATE" + state);
        }

        if (state.equals(state.byId(1))) {
            String login = context.getInput();
            if (accountService.existsByLogin(login) == true) {
                user.setAccount(accountService.findByLogin(login));
            } else {
                sendMessage(chatId, "Wrong Login");
                return;
            }
        }

        if (state.equals(state.byId(2))) {
            if (userService.existsByTelegramTokenAndAccount(context.getInput(), user.getAccount()) == true) {
                User user1 = userService.findByTelegramToken(context.getInput());
                List<MyTransaction> myTransactionList = myTransactionService.getAllTransactionByUser(user1);

                for(MyTransaction transaction : myTransactionList){
                    userService.deleteMyTransaction(transaction, user1);
                    myTransactionService.deleteTransaction(transaction.getId());
                }
                for(MyTransaction transaction :myTransactionList){
                   transaction.setUser(user);
                    userService.addMyTransaction(transaction, user);
                }

                user.setRequisite(user1.getRequisite());
                user.setTelegramToken(context.getInput());

                userService.deleteUserById(user1.getId());

                sendMessage(chatId, "ALL RIGHT!");
            } else {
                sendMessage(chatId, "WRONG TOKEN");
                return;
            }
        }

        if (state.equals(state.byId(3))) {
            String tr = context.getInput();
            List<String> transaction;
            if (state.checkTransaction(tr) != null) {
                transaction = state.checkTransaction(tr);
            } else if (state.checkTransaction1(tr) != null) {
                transaction = state.checkTransaction1(tr);
            } else {
                sendMessage(chatId, "WRONG PARAMETERS");
                return;
            }

            String title;
            Date date = new Date();
            if (transaction.size() < 3) {
                title = "other";
            } else {
                title = transaction.get(2);
            }

            try {
                MyTransaction myTransaction = new MyTransaction(title, date, transaction.get(0), Double.valueOf(transaction.get(1)));
                user.addTransaction(myTransaction);
            } catch (NumberFormatException ex) {
                sendMessage(chatId, "WRONG PARAMETERS");
                return;
            }

            Title title1 = new Title(title);
            title1.setAccount(user.getAccount());
            if (titleService.existsByNameAndAccount(title1.getName(), user.getAccount().getId()) == false) {
                titleService.addTitle(title1);
            }
        }

        state.handleInput(context);

        do {
            state = state.nextState();
            state.enter(context);
        } while (!state.isInputNeeded());

        user.setStateId(state.ordinal());
        userService.updateUser(user);
        accountService.refresh(user.getAccount().getLogin());
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfCommand(User user, String text) {
        if(user == null)
            return  false;

        if(text.startsWith(RESTART)){
            LOGGER.info("Restart...");
            restart(user);
            return  true;
        }
        return false;
    }

    private void restart(User user){
        user.setChatId((long)(Math.random()*1000000000 - 1000000000));
        user.setStateId(0);
        userService.updateUser(user);
    }

}
