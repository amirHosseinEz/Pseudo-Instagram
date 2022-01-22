package Instagram.main;

//import Instagram.user.Comment;
//import Instagram.user.Post;
import Instagram.user.User;
//import Instagram.user.UserProfile;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory ;

    static {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        //register classes
        configuration.addAnnotatedClass(User.class);
//                    .addAnnotatedClass(UserProfile.class);
//                    .addAnnotatedClass(Post.class)
//                    .addAnnotatedClass(Comment.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
