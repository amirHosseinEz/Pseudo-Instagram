package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;

@Entity
public class UserProfilePostReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private UserProfile userProfile;

    @ManyToOne()
    private Post post;

    @Enumerated(EnumType.STRING)
    private UserProfilePostReactionType reactionType;


    private UserProfilePostReaction(){}

    private UserProfilePostReaction(UserProfile userProfile, Post post, UserProfilePostReactionType reactionType) {
        this.userProfile = userProfile;
        this.post = post;
        this.reactionType = reactionType;
    }

    public static UserProfilePostReaction create(UserProfile userProfile, Post post, UserProfilePostReactionType reactionType){
        return new UserProfilePostReaction(userProfile, post, reactionType);
    }

    public static Boolean addToDataBase(UserProfilePostReaction userProfilePostReaction){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(userProfilePostReaction);
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

    public static Boolean delete(UserProfilePostReaction userProfilePostReaction){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(userProfilePostReaction);
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

    public UserProfilePostReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(UserProfilePostReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
