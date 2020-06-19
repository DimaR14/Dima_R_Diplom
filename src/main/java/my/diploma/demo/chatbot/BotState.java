package my.diploma.demo.chatbot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public enum BotState {

    Start {
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Hello!");
        }


        @Override
        public BotState nextState() {
            return Login;
        }
    },

    Login {

        private BotState next;

        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Enter your Login please:");
        }

        @Override
        public void handleInput(BotContext context) {
            next = Password;
        }


        @Override
        public BotState nextState() {
            return next;
        }
    },

    Password {
        private BotState next;

        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Enter your Telegram Token:");
        }

        @Override
        public void handleInput(BotContext context) {
            next = Bookkeeper;
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },


    Bookkeeper {
        private BotState next;


        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Enter your transaction:");
        }

        @Override
        public void handleInput(BotContext context) {
            next = Bookkeeper;
        }

        @Override
        public BotState nextState() {
            return next;
        }
    };


    private static BotState[] states;
    private final boolean inputNeeded;


    BotState() {
        this.inputNeeded = true;
    }

    BotState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }

    public static BotState getInitialState() {
        return byId(0);
    }

    public static BotState byId(int id) {
        if (states == null) {
            states = BotState.values();
        }

        return states[id];
    }

    protected void sendMessage(BotContext context, String text) {
        SendMessage message = new SendMessage()
                .setChatId(context.getUser().getChatId())
                .setText(text);
        try {
            context.getBot().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean isInputNeeded() {
        return inputNeeded;
    }

    public void handleInput(BotContext context) {
        // do nothing by default
    }

    public abstract void enter(BotContext context);

    public abstract BotState nextState();

    protected List<String> checkTransaction(String text) throws NumberFormatException {
        String[] subString = text.split(" ");
        if (subString[0].equals("+") || subString[0].equals("-")) {
            String attribute = subString[0];
            String sum = String.valueOf(subString[1]);
            List<String> tr = new ArrayList<>();
            tr.add(attribute);
            tr.add(sum);
            if (subString.length > 2) {
                String title = String.valueOf(subString[2]);
                tr.add(title);
            }
            return tr;
        } else
            return null;
    }

    protected List<String> checkTransaction1(String text) {
        String[] subString = text.split(" ");
        char[] string1 = text.toCharArray();
        char[] string2 = new char[100];
        if (string1[0] == '+' || string1[0] == '-') {
            String attribute = String.valueOf(string1[0]);
            subString[0].getChars(1, subString[0].length(), string2, 0);
            String sum = String.valueOf(string2);
            List<String> tr = new ArrayList<>();
            tr.add(attribute);
            tr.add(sum);
            if (subString.length > 1) {
                String title = subString[1];
                tr.add(title);
            }
            return tr;
        } else
            return null;
    }
}
