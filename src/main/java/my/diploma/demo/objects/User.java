package my.diploma.demo.objects;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String login;
    private  String password;
    private  double balance;
    private String telegramToken;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user",fetch = FetchType.EAGER)
    private List<Bookkeeper> bookkeepers = new ArrayList<>();

    @OneToMany (cascade = CascadeType.ALL)
    private List<Title> titles = new ArrayList<>();

    public User(){}

    public User(String login,String password){
        this.login = login;
        this.password = password;
    }

    public void addBookkeeper(Bookkeeper bookkeeper){
        bookkeeper.setUser(this);
        bookkeepers.add(bookkeeper);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public String getTelegramToken() { return telegramToken; }

    public void setTelegramToken(String telegramToken) { this.telegramToken = telegramToken; }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Bookkeeper> getBookkeepers() {
        return bookkeepers;
    }

    public void setBookkeepers(List<Bookkeeper> bookkeepers) {
        this.bookkeepers = bookkeepers;
    }

}
