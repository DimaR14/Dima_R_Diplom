package my.diploma.demo.objects;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String login;
    private  String password;
    private  double balance;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account",fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "account")
    private List<Title> titles = new ArrayList<>();

    public Account(){}

    public Account(String login, String password){
        this.login = login;
        this.password = password;
    }

    public void addUser(User user){
        user.setAccount(this);
        users.add(user);
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

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
