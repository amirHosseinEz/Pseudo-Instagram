package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private User user;

    private String userId;
    private String nickName;
    private Date dateOfBirth;
    private String bio;
    //TODO: add photo
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "userProfile")
    private Set<Post> posts;

    private UserProfile(){

    }

    private UserProfile(User user) {
        this.user = user;
    }

    public static UserProfile create(User user){
        if(user.getUserProfile() == null)
            return new UserProfile(user);
        return null;
    }

    public static Boolean createAndAddToDataBase(User user){
        if(user.getUserProfile() != null){
            return Boolean.FALSE;
        }
        UserProfile userProfile = new UserProfile(user);
        return addToDataBase(userProfile);
    }

    public static Boolean addToDataBase(UserProfile userProfile){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(userProfile);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
            return Boolean.FALSE;
        } finally {
            session.close();
        }
        return Boolean.TRUE;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
