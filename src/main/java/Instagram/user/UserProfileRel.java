package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
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

    public UserProfileRel(UserProfile from, UserProfile to) {
        this.from = from;
        this.to = to;
    }

    public UserProfileRel create(UserProfile from, UserProfile to){
        return new UserProfileRel(from, to);
    }
    public static void follow(UserProfile from, UserProfile to){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<UserProfileRel> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfileRel where from_id = \'"+from.getUserId()+"\' and to_id = \'"+to.getUserId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        if(collection1.isEmpty()){
            UserProfileRel userProfileRel = new UserProfileRel(from, to);
            userProfileRel.setRelType(UserProfileRelType.FOLLOW);
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
    public List<UserProfile> getFollowers(UserProfile userProfile){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<UserProfileRel> collection1 = new ArrayList<>();
        List<UserProfile> collection2 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfileRel where to_id = \'"+userProfile+"\' and UserProfileRelType = \'"+UserProfileRelType.FOLLOW+"\'");
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
    public static void block(UserProfile from, UserProfile to){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<UserProfileRel> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from UserProfileRel where from_id = \'"+from.getUserId()+"\' and to_id = \'"+to.getUserId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        if(collection1.isEmpty()){
            UserProfileRel userProfileRel = new UserProfileRel(from, to);
            userProfileRel.setRelType(UserProfileRelType.BLOCK);
            UserProfileRel.addToDataBase(userProfileRel);
        }
        else {
            if(collection1.get(0).getRelType().equals(UserProfileRelType.BLOCK)){
                //already blocked

            }else {
                //he's followed
            }
        }
    }
    public static void addToDataBase(UserProfileRel userProfileRel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(userProfileRel);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
