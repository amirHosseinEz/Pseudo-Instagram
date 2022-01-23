package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private UserProfile userProfile;

    private String caption;
    private LocalDateTime createTime;
    //TODO: photo

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;

    private Post(){

    }

    private Post(UserProfile userProfile) {
        this.userProfile = userProfile;
        this.createTime = LocalDateTime.now();
    }

    public static Post create(){
        return new Post();
    }

    public static Post create(UserProfile userProfile){
        return new Post(userProfile);
    }

    public static Boolean createAndAddToDataBase(UserProfile userProfile){
        Post post = new Post(userProfile);
        return addToDataBase(post);
    }

    public static Boolean addToDataBase(Post post){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(post);
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

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;

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
    public static List<Post> getPostsOfUser(UserProfile userProfile){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Post> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from Post where userProfile_id = \'"+userProfile.getId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        return collection1;
    }
    public static void deletePost(Post post){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.remove(post);
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
    }

    public Long getId() {
        return id;
    }
    public void addposts(Comment comment){
        if(comments == null)
            comments = new ArrayList<>();
        comments.add(comment);
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}
