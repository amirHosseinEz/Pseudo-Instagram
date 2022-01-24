package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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
        userProfilePostReaction.getUserProfile().addUserProfilePostReaction(userProfilePostReaction);
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
    public static void like(UserProfile userProfile, Post post){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<UserProfilePostReaction> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfilePostReaction where userProfile = \'"+userProfile.getId()+"\' and post = \'"+ post.getId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        if(collection1.isEmpty()) {
            UserProfilePostReaction userProfilePostReaction = new UserProfilePostReaction(userProfile, post, UserProfilePostReactionType.LIKE);
            UserProfilePostReaction.addToDataBase(userProfilePostReaction);
        }
        else {
            if(collection1.get(0).getReactionType().equals(UserProfilePostReactionType.LIKE)){
                //already liked

            }
        }
    }
    public static void unlike(UserProfile userProfile, Post post){              // it's not dislike!
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<UserProfilePostReaction> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfilePostReaction where userProfile = \'"+userProfile.getId()+"\' and post = \'"+ post.getId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        if(collection1.isEmpty()) {
            //not liked!
        }
        else {
            if(collection1.get(0).getReactionType().equals(UserProfilePostReactionType.LIKE)){
                UserProfilePostReaction userProfilePostReaction = collection1.get(0);
                UserProfilePostReaction.delete(userProfilePostReaction);
            }
        }
    }

    public static Boolean isLiked(UserProfile userProfile, Post post){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserProfilePostReaction> criteria = builder.createQuery(UserProfilePostReaction.class);
        Root<UserProfilePostReaction> root = criteria.from(UserProfilePostReaction.class);
        Predicate userProfilePredicate = builder.equal(root.get("userProfile"), userProfile);
        Predicate postPredicate = builder.equal(root.get("post"), post);
        Predicate typePredicate = builder.equal(root.get("reactionType"), UserProfilePostReactionType.LIKE);
        criteria.select(root).where(builder.and(userProfilePredicate, postPredicate, typePredicate));
        List<UserProfilePostReaction> userProfilePostReactions = session.createQuery(criteria).getResultList();
        if(userProfilePostReactions.size() > 0 && userProfilePostReactions.get(0).getReactionType().equals(UserProfilePostReactionType.LIKE)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
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
