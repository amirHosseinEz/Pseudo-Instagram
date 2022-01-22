package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Entity
public class User {

    public static User currentUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;

    private String username;
    private String password;
    private String phoneNum;
    private String email;


    private User() {
    }

    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User create(){
        return new User();
    }

    public static User create(String username, String password){
        return new User(username, password);
    }

    public static Boolean createAndAddToDataBase(String username, String password){
        User user = new User(username, password);
        if(isUsernameUnique(username)){
            addToDataBase(user);
            UserProfile.createAndAddToDataBase(user);
            return Boolean.TRUE;
        }
        else{
            return Boolean.FALSE;
        }
    }

    public static Boolean addToDataBase(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
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

    public UserProfile getUserProfile(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserProfile> criteria = builder.createQuery(UserProfile.class);
        Root<UserProfile> root = criteria.from(UserProfile.class);
        criteria.select(root).where(builder.equal(root.get("user"), this));
        List<UserProfile> userProfiles = session.createQuery(criteria).getResultList();
        if(userProfiles.size() > 0){
            return userProfiles.get(0);
        }
        return null;
    }

    public static User getUserByUserPass(String username, String password){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        Predicate usernamePredicate = builder.equal(root.get("username"), username);
        Predicate passwordPredicate = builder.equal(root.get("password"), password);
        criteria.select(root).where(builder.and(usernamePredicate, passwordPredicate));
        List<User> users = session.createQuery(criteria).getResultList();
        if(users.size() > 0){
            return users.get(0);
        }
        return null;
    }

    public static Boolean isUsernameUnique(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(builder.equal(root.get("username"), username));
        List<User> users = session.createQuery(criteria).getResultList();
        if(users.size() != 0){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
