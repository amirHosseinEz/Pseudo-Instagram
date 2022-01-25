package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Proxy(lazy=false)
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private User user;

    private String nickName;
    private Date dateOfBirth;
    private String bio;
    private byte[] image;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Post> posts;

    @OneToMany(mappedBy = "from")
    private List<UserProfileRel> userProfileRelsFrom;

    @OneToMany(mappedBy = "to")
    private List<UserProfileRel> userProfileRelsTo;


    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<UserProfilePostReaction> userProfilePostReactions;

    public UserProfile(){

    }

    private UserProfile(User user) {
        this.user = user;
    }

    public static UserProfile create(User user){
        if(user.getUserProfile() == null)
            return new UserProfile(user);
        return null;
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<UserProfileRel> getUserProfileRelsFrom() {
        return userProfileRelsFrom;
    }

    public void setUserProfileRelsFrom(List<UserProfileRel> userProfileRelsFrom) {
        this.userProfileRelsFrom = userProfileRelsFrom;
    }

    public List<UserProfileRel> getUserProfileRelsTo() {
        return userProfileRelsTo;
    }

    public void setUserProfileRelsTo(List<UserProfileRel> userProfileRelsTo) {
        this.userProfileRelsTo = userProfileRelsTo;
    }

    public List<UserProfilePostReaction> getUserProfilePostReactions() {
        return userProfilePostReactions;
    }

    public void setUserProfilePostReactions(List<UserProfilePostReaction> userProfilePostReactions) {
        this.userProfilePostReactions = userProfilePostReactions;
    }

    public static List<UserProfile> getAllUserProfiles(){
       /* Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserProfile> criteria = builder.createQuery(UserProfile.class);
        return session.createQuery(criteria).getResultList();*/

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction1 = null;
        List<UserProfile> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfile ");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        return collection1;
    }

    public static Boolean createAndAddToDataBase(User user){
        if(user.getUserProfile() != null){
            return Boolean.FALSE;
        }
        UserProfile userProfile = new UserProfile(user);
        return addToDataBase(userProfile);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Boolean addToDataBase(UserProfile userProfile){
        userProfile.getUser().setUserProfile(userProfile);
        userProfile.setUser(userProfile.getUser());
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(this);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
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
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(this);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(this);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(this);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
        Session session =HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(this);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void addposts(Post post){
        if(posts == null)
            posts = new ArrayList<>();
        posts.add(post);
    }
    public void addUserProfilePostReaction(UserProfilePostReaction userProfilePostReaction){
        if(userProfilePostReactions == null)
            userProfilePostReactions = new ArrayList<>();
        userProfilePostReactions.add(userProfilePostReaction);
    }

    @Override
    public String toString() {
        return this.nickName + " " + this.user.getUsername();
    }

    public Boolean isEqual(UserProfile userProfile){
        return this.getId().equals(userProfile.getId());
    }

}
