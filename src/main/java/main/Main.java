package main.java.main;


import main.java.user_profile.Comment;
import main.java.user_profile.Post;
import main.java.user_profile.User;
import main.java.user_profile.UserProfile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class Main {
    public static SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(UserProfile.class)
            .addAnnotatedClass(Post.class)
            .addAnnotatedClass(Comment.class)
            .buildSessionFactory();
    public static void main(String[] args) {

        User user = new User("pt6", "opcvv", "pt6");
        UserProfile userProfile = new UserProfile(user);
        UserProfile.createUserPage(userProfile);
        User.createtest(user);
        //userProfile.setNickname("nicki");
        Post post = new Post(userProfile, "caption", "photo");
        post.createPost(post);
        Comment comment = new Comment(userProfile, post, "commented");
        comment.createComment(comment);
        //User.create(user)
        ;       /* try {
            transaction = session.beginTransaction();

            User user = User.create();
            user.setUsername("hello");
            user.setPassword("world");
            session.save(user);

            transaction.commit();
        }

        catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }*/
    }

}
