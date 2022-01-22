package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    private String text;
    private LocalDateTime createTime;

    private Comment(){

    }

    private Comment(UserProfile userProfile, Post post, String text) {
        this.userProfile = userProfile;
        this.post = post;
        this.text = text;

        this.createTime = LocalDateTime.now();
    }

    private Comment(UserProfile userProfile, Post post) {
        this.userProfile = userProfile;
        this.post = post;

        this.createTime = LocalDateTime.now();
    }

    public static Comment create(UserProfile userProfile, Post post){
        return new Comment(userProfile, post);
    }

    public static Comment create(UserProfile userProfile, Post post, String text){
        return new Comment(userProfile, post, text);
    }

    public static Boolean addToDataBase(Comment comment){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(comment);
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
