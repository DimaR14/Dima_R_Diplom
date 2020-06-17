package my.diploma.demo.chatbot;

import my.diploma.demo.objects.Bookkeeper;
import my.diploma.demo.objects.MyTransaction;
import my.diploma.demo.objects.Title;
import my.diploma.demo.objects.User;
import my.diploma.demo.service.BookkeeperService;
import my.diploma.demo.service.MyTransactionService;
import my.diploma.demo.service.TitleService;
import my.diploma.demo.service.UserService;
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

    private final BookkeeperService bookkeeperService;
    private final UserService userService;
    private final TitleService titleService;
    private final MyTransactionService myTransactionService;

    public ChatBot(BookkeeperService bookkeeperService, UserService userService, TitleService titleService,MyTransactionService myTransactionService) {
        this.bookkeeperService = bookkeeperService;
        this.userService = userService;
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

        Bookkeeper bookkeeper  = bookkeeperService.findByChatId(chatId);

        if (checkIfCommand(bookkeeper, text))
            return;

        BotContext context;
        BotState state;

        if (bookkeeper == null) {
            state = BotState.getInitialState();

            bookkeeper = new Bookkeeper(chatId, state.ordinal());
            bookkeeperService.addBookkeeper(bookkeeper);

            context = BotContext.of(this, bookkeeper, text);
            state.enter(context);

            LOGGER.info("NEW user register:" + chatId);
        } else {
            context = BotContext.of(this, bookkeeper, text);
            state = BotState.byId(bookkeeper.getStateId());

            LOGGER.info("UPDATE" + state);
        }

        if (state.equals(state.byId(1))) {
            String login = context.getInput();
            if (userService.existsByLogin(login) == true) {
                bookkeeper.setUser(userService.findByLogin(login));
            } else {
                sendMessage(chatId, "Wrong Login");
                return;
            }
        }

        if (state.equals(state.byId(2))) {
            if (bookkeeperService.existsByTelegramTokenAndUser(context.getInput(),bookkeeper.getUser()) == true) {
                Bookkeeper bookkeeper1 = bookkeeperService.findByTelegramToken(context.getInput());
                List<MyTransaction> myTransactionList = myTransactionService.getAllTransactionByBookkeeper(bookkeeper1);

                for(MyTransaction transaction : myTransactionList){
                    bookkeeperService.deleteMyTransaction(transaction,bookkeeper1);
                    myTransactionService.deleteTransaction(transaction.getId());
                }
                for(MyTransaction transaction :myTransactionList){
                   transaction.setBookkeeper(bookkeeper);
                    bookkeeperService.addMyTransaction(transaction,bookkeeper);
                }

                bookkeeper.setRequisite(bookkeeper1.getRequisite());
                bookkeeper.setTelegramToken(context.getInput());

                bookkeeperService.deleteBookkeeperById(bookkeeper1.getId());

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
                bookkeeper.addTransaction(myTransaction);
            } catch (NumberFormatException ex) {
                sendMessage(chatId, "WRONG PARAMETERS");
                return;
            }

            Title title1 = new Title(title);
            title1.setUser(bookkeeper.getUser());
            if (titleService.existsByNameAndUser(title1.getName(), bookkeeper.getUser().getId()) == false) {
                titleService.addTitle(title1);
            }
        }

        state.handleInput(context);

        do {
            state = state.nextState();
            state.enter(context);
        } while (!state.isInputNeeded());

        bookkeeper.setStateId(state.ordinal());
        bookkeeperService.updateBookkeeper(bookkeeper);
        userService.refresh(bookkeeper.getUser().getLogin());
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

    private boolean checkIfCommand(Bookkeeper bookkeeper, String text) {
        if(bookkeeper == null)
            return  false;

        if(text.startsWith(RESTART)){
            LOGGER.info("Restart...");
            restart(bookkeeper);
            return  true;
        }
        return false;
    }

    private void restart(Bookkeeper bookkeeper){
        bookkeeper.setChatId((long)(Math.random()*1000000000 - 1000000000));
        bookkeeper.setStateId(0);
        bookkeeperService.updateBookkeeper(bookkeeper);
    }

}
