package user_profile;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    private User() {
    }

    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User create(){
        User user = new User();
        return user;
    }

    public static User create(String username, String password){
        User user = new User(username, password);
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
