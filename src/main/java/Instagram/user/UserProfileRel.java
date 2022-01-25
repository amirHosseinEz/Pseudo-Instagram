package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserProfileRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private UserProfile from;

    @ManyToOne()
    private UserProfile to;

    @Enumerated(EnumType.STRING)
    private UserProfileRelType relType;

    private UserProfileRel() {
    }

    private UserProfileRel(UserProfile from, UserProfile to, UserProfileRelType relType) {
        this.from = from;
        this.to = to;
        this.relType = relType;
    }

    public static UserProfileRel create(UserProfile from, UserProfile to, UserProfileRelType userProfileRelType){
        return new UserProfileRel(from, to, userProfileRelType);
    }

    public static void addToDataBase(UserProfileRel userProfileRel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(userProfileRel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void follow(UserProfile from, UserProfile to){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<UserProfileRel> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfileRel where from_id = \'"+from.getId()+"\' and to_id = \'"+to.getId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        if(collection1.isEmpty()){
            UserProfileRel userProfileRel = new UserProfileRel(from, to, UserProfileRelType.FOLLOW);
            UserProfileRel.addToDataBase(userProfileRel);
        }
        else {
            if(collection1.get(0).getRelType().equals(UserProfileRelType.FOLLOW)){
                //already followed

            }else {
                //he's blocked
            }
        }
    }
    public static void unFollow(UserProfile from, UserProfile to){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<UserProfileRel> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfileRel where from_id = \'"+from.getId()+"\' and to_id = \'"+to.getId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        if(collection1.isEmpty()){
            // not followed
        }
        else {
            if(collection1.get(0).getRelType().equals(UserProfileRelType.FOLLOW)){
                UserProfileRel userProfileRel = collection1.get(0);
                UserProfileRel.delete(userProfileRel);
            }else {
                //he's blocked
            }
        }
    }

    public static void block(UserProfile from, UserProfile to){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<UserProfileRel> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfileRel where from_id = \'"+from.getId()+"\' and to_id = \'"+to.getId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        if(collection1.isEmpty()){
            UserProfileRel userProfileRel = new UserProfileRel(from, to, UserProfileRelType.BLOCK);
            UserProfileRel.addToDataBase(userProfileRel);
        }
        else {
            if(collection1.get(0).getRelType().equals(UserProfileRelType.BLOCK)){
                //already blocked

            }else {
                //he's followed!
            }
        }
    }
    public static void unBlock(UserProfile from, UserProfile to){

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<UserProfileRel> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfileRel where from_id = \'"+from.getId()+"\' and to_id = \'"+to.getId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        if(collection1.isEmpty()){
            // not blocked!
        }
        else {
            if(collection1.get(0).getRelType().equals(UserProfileRelType.BLOCK)){
                UserProfileRel userProfileRel = collection1.get(0);
                UserProfileRel.delete(userProfileRel);
            }else {
                //he's followed!
            }
        }
    }

    public static void delete(UserProfileRel userProfileRel){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(userProfileRel);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static List<UserProfile> getFollowers(UserProfile userProfile){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction1 = null;
        List<UserProfileRel> collection1 = new ArrayList<>();
        List<UserProfile> collection2 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfileRel where to_id = \'"+userProfile.getId()+"\' and relType = \'"+UserProfileRelType.FOLLOW+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
            for(UserProfileRel userProfileRel: collection1){
                collection2.add(userProfileRel.getFrom());
            }
        }
        finally {
            session.close();
        }
        return collection2;
    }
    public static List<UserProfile> getFollowings(UserProfile userProfile){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction1 = null;
        List<UserProfileRel> collection1 = new ArrayList<>();
        List<UserProfile> collection2 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfileRel where from_id = \'"+userProfile.getId()+"\' and relType = \'"+UserProfileRelType.FOLLOW+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
            for(UserProfileRel userProfileRel: collection1){
                collection2.add(userProfileRel.getTo());
            }
        }
        finally {
            session.close();
        }
        return collection2;
    }

    public static Boolean isFollowed(UserProfile from, UserProfile to){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserProfileRel> criteria = builder.createQuery(UserProfileRel.class);
        Root<UserProfileRel> root = criteria.from(UserProfileRel.class);
        Predicate usernamePredicate = builder.equal(root.get("from"), from);
        Predicate passwordPredicate = builder.equal(root.get("to"), to);
        criteria.select(root).where(builder.and(usernamePredicate, passwordPredicate));
        List<UserProfileRel> userProfileRels = session.createQuery(criteria).getResultList();
        if(userProfileRels.size() > 0 && userProfileRels.get(0).getRelType().equals(UserProfileRelType.FOLLOW)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static Boolean isBlocked(UserProfile from, UserProfile to){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserProfileRel> criteria = builder.createQuery(UserProfileRel.class);
        Root<UserProfileRel> root = criteria.from(UserProfileRel.class);
        Predicate usernamePredicate = builder.equal(root.get("from"), from);
        Predicate passwordPredicate = builder.equal(root.get("to"), to);
        criteria.select(root).where(builder.and(usernamePredicate, passwordPredicate));
        List<UserProfileRel> userProfileRels = session.createQuery(criteria).getResultList();
        if(userProfileRels.size() > 0 && userProfileRels.get(0).getRelType().equals(UserProfileRelType.BLOCK)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getFrom() {
        return from;
    }

    public void setFrom(UserProfile from) {
        this.from = from;
    }

    public UserProfile getTo() {
        return to;
    }

    public void setTo(UserProfile to) {
        this.to = to;
    }

    public UserProfileRelType getRelType() {
        return relType;
    }

    public void setRelType(UserProfileRelType relType) {
        this.relType = relType;
    }
}
