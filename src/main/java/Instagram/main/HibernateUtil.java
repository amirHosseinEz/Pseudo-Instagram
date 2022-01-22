package Instagram.main;

//import Instagram.user.Comment;
//import Instagram.user.Post;
import Instagram.user.User;
import Instagram.user.UserProfile;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(UserProfile.class)
//            .addAnnotatedClass(Post.class)
//            .addAnnotatedClass(Comment.class)
            .buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
