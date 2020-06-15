package my.diploma.demo.chatbot;

import my.diploma.demo.objects.Bookkeeper;

public class BotContext {
    private final ChatBot bot;
    private final Bookkeeper bookkeeper;
    private final String input;

    public static BotContext of(ChatBot bot, Bookkeeper bookkeeper, String text) {
        return new BotContext(bot, bookkeeper, text);
    }

    private BotContext(ChatBot bot, Bookkeeper bookkeeper, String input) {
        this.bot = bot;
        this.bookkeeper = bookkeeper;
        this.input = input;
    }

    public ChatBot getBot() {
        return bot;
    }

    public Bookkeeper getBookkeeper() {
        return bookkeeper;
    }

    public String getInput() {
        return input;
    }
}

