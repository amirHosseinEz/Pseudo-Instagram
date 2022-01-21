package main.java.user_profile;


import main.java.main.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    private User() {
    }

    //////////
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public static User create(){
        User user = new User();
        return user;
    }

    public static User create(String username, String password, String email){
        User user = new User(username, password, email);
        return user;
    }
    public static User createtest(User user){
        Session session = Main.factory.getCurrentSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from User where username = \'"+user.username+"\' or email =\'"+ user.email + "\'");
            List<User> collection1 = query1.getResultList();
            if(!collection1.isEmpty()) {
                System.out.println("username or email are already taken!");
                return null;
            }

            session.save(user);
            session.getTransaction().commit();;
        }
        finally {
            session.close();
        }
        return user;
    }
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        //TODO: check username uniqueness
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        //TODO: validate email
        this.email = email;
    }
}
