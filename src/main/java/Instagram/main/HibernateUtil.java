package Instagram.main;

import Instagram.user.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(UserProfile.class)
            .addAnnotatedClass(Post.class)
            .addAnnotatedClass(Comment.class)
            .addAnnotatedClass(UserProfileRel.class)
            .addAnnotatedClass(UserProfilePostReaction.class)
            .addAnnotatedClass(CommentRel.class)
            .buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
