package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Proxy(lazy=false)
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserProfile userProfile;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Post post;

    private String text;
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentRel> commentRelsFrom;

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentRel> commentRelsTo;

    public Comment(){

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
        comment.getPost().addComment(comment);
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

    public static List<Comment> getCommentsOfPost(Post post){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Comment> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from Comment where post_id = \'"+post.getId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        return collection1;
    }
    public Long getId() {
        return id;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public static void deleteComment(Comment comment){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.remove(comment);
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public List<CommentRel> getCommentRelsFrom() {
        return commentRelsFrom;
    }

    public List<CommentRel> getCommentRelsTo() {
        return commentRelsTo;
    }

    public void addCommentRelFrom(CommentRel commentRel){
        if(commentRelsFrom == null)
            commentRelsFrom = new ArrayList<>();
        commentRelsFrom.add(commentRel);
    }
    public void addCommentRelTo(CommentRel commentRel){
        if(commentRelsTo == null)
            commentRelsTo = new ArrayList<>();
        commentRelsTo.add(commentRel);
    }
    public static Comment getCommentFromId(Long id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Comment> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from Comment where id = \'"+id+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        if(collection1.isEmpty())
            return null;
        return collection1.get(0);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userProfile=" + userProfile +
                ", post=" + post +
                ", text='" + text + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
