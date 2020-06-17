package my.diploma.demo.objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bookkeeper {

    @Id
    @GeneratedValue
    private long id;

    private  long chatId;

    private int stateId;

    private String requisite;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "bookkeeper",fetch = FetchType.EAGER)
    private List<MyTransaction> transactions = new ArrayList<>();

    private double balance;

    private String telegramToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Bookkeeper(){}

    public Bookkeeper(long chatId, int stateId){
        this.chatId = chatId;
        this.stateId = stateId;
    }

    public Bookkeeper(String requisite, String telegramToken){
        this.requisite = requisite;
        this.telegramToken = telegramToken;
    }

    public void addTransaction(MyTransaction transaction){
        transaction.setBookkeeper(this);
        transactions.add(transaction);
        if (transaction.getAttribute().equals("+")) {
            this.setBalance(getBalance() + transaction.getSum());
        } else
            this.setBalance(getBalance() - transaction.getSum());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getRequisite() {
        return requisite;
    }

    public void setRequisite(String requisite) {
        this.requisite = requisite;
    }

    public List<MyTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<MyTransaction> transactions) { this.transactions = transactions; }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance){ this.balance = balance; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTelegramToken() {
        return telegramToken;
    }

    public void setTelegramToken(String telegramToken) {
        this.telegramToken = telegramToken;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }
}
