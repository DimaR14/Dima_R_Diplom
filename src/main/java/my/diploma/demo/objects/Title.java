package my.diploma.demo.objects;

import javax.persistence.*;

@Entity
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    String name;

    public Title(){}

    public Title(String name){
        this.name = name;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
