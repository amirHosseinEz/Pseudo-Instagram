package Instagram.main;

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
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Instagram");
        window.show();
        Register.register();
    }
}