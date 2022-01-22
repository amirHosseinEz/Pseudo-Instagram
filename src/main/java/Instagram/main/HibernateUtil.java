package Instagram.main;

import Instagram.user.Comment;
import Instagram.user.Post;
import Instagram.user.User;
import Instagram.user.UserProfile;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {



    public static SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(UserProfile.class)
            .addAnnotatedClass(Post.class)
            .addAnnotatedClass(Comment.class)
            .buildSessionFactory();

}
