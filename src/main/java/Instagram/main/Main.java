package Instagram.main;

import Instagram.user.Post;
import Instagram.user.User;
import Instagram.user.UserProfileRel;
import Instagram.views.Register;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static int screenWidth = 600;
    public static int screenHeight = 500;
    public static int space = 10;
    public static Stage window;

    public static void main(String[] args) {
        launch(args);
        HibernateUtil.getSessionFactory().openSession();
        User user = User.getUserByUserPass("5", "5");
        if(User.login("5", "5")){
            System.out.println(User.currentUser);
            System.out.println(User.getUserByUserPass("1", "1").getUserProfile());
            UserProfileRel.follow(User.getUserByUserPass("1", "1").getUserProfile(), User.currentUser.getUserProfile());
            for(Post post: Post.getFriendsPosts(User.currentUser.getUserProfile())){
                System.out.println(post.getId());
            }
        }
        HibernateUtil.getSessionFactory().close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Instagram");
        window.show();
        Register.register();
    }
}