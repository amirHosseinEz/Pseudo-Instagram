package Instagram.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public static Stage window;
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Instagram");
        window.show();
    }
}
