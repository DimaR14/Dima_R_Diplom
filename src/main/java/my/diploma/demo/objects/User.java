package my.diploma.demo.objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    private  long chatId;

    private int stateId;

    private String requisite;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "user",fetch = FetchType.EAGER)
    private List<MyTransaction> transactions = new ArrayList<>();

    private double balance;

    private String telegramToken;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public User(){}

    public User(long chatId, int stateId){
        this.chatId = chatId;
        this.stateId = stateId;
    }

    public User(String requisite, String telegramToken){
        this.requisite = requisite;
        this.telegramToken = telegramToken;
    }

    public void addTransaction(MyTransaction transaction){
        transaction.setUser(this);
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
