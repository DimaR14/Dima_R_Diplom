package my.diploma.demo.objects;

import javax.persistence.*;

@Entity
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    String name;

    public Title(){}

    public Title(String name){
        this.name = name;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
