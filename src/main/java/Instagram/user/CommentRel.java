package Instagram.user;

import Instagram.main.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CommentRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Comment from;

    @ManyToOne(cascade = CascadeType.ALL)
    private Comment to;

    @Enumerated(EnumType.STRING)
    private CommentRelType relType;

    private CommentRel(){
    }

    private CommentRel(Comment from, Comment to, CommentRelType relType) {
        this.from = from;
        this.to = to;
        this.relType = relType;
    }

    public static CommentRel create(Comment from, Comment to, CommentRelType relType){
        return new CommentRel(from, to, relType);
    }

    public static Boolean addToDataBase(CommentRel commentRel){
        commentRel.getFrom().addCommentRelFrom(commentRel);
        commentRel.getTo().addCommentRelFrom(commentRel);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(commentRel);
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

    public static Boolean delete(CommentRel commentRel){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(commentRel);
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
    public static Comment getMainComment(Comment comment){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<CommentRel> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from CommentRel where from_id = \'"+comment.getId()+"\' and relType = \'"+ CommentRelType.REPLAY+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        if(collection1.isEmpty())
            return null;
        return collection1.get(0).getTo();
    }

    public Comment getFrom() {
        return from;
    }

    public void setFrom(Comment from) {
        this.from = from;
    }

    public Comment getTo() {
        return to;
    }

    public void setTo(Comment to) {
        this.to = to;
    }

    public CommentRelType getRelType() {
        return relType;
    }

    public void setRelType(CommentRelType relType) {
        this.relType = relType;
    }
}
