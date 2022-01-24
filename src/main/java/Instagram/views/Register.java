package Instagram.views;


import Instagram.main.Main;
import Instagram.user.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Register {

    public static void loginPage(){
        VBox loginLayout = new VBox(Main.space);
        Label title = new Label("Login");
        Label usernameLabel = new Label("Enter username");
        TextField username = new TextField();
        username.setMaxWidth((float)Main.screenWidth/4);
        Label passwordLabel = new Label("Enter password");
        TextField password = new TextField();
        password.setMaxWidth((float)Main.screenWidth/4);
        Label emptyLabel = new Label("");
        Button loginButton = new Button("login");
        loginButton.setOnAction(e -> {
            if(User.login(username.getText(), password.getText())){
                emptyLabel.setText("");
                UserProfileViews.showPage(User.currentUser.getUserProfile());
                System.out.println("show user dashboard");
            }else{
                emptyLabel.setText("username and password don't match, try again");
            }
            username.clear();
            password.clear();
        });
        Button registerButton = new Button("register");
        registerButton.setOnAction(e -> register());
        loginLayout.getChildren().addAll(title, usernameLabel, username, passwordLabel, password, emptyLabel, loginButton, registerButton);
        loginLayout.setAlignment(Pos.CENTER);
        Scene loginScene = new Scene(loginLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(loginScene);
    }

    public static void register(){
        VBox registerLayout = new VBox(Main.space);
        Label title = new Label("Register");
        Label usernameLabel = new Label("Enter username");
        TextField username = new TextField();
        username.setMaxWidth((float)Main.screenWidth/4);
        Label userIdLabel = new Label("Enter user id");
        TextField userId = new TextField();
        userId.setMaxWidth((float) Main.screenWidth/4);
        Label passwordLabel = new Label("Enter password");
        TextField password = new TextField();
        password.setMaxWidth((float) Main.screenWidth/4);
        Label verifyPasswordLabel = new Label("Enter password again");
        TextField verifyPassword = new TextField();
        verifyPassword.setMaxWidth((float)Main.screenWidth/4);
        Label emptyLabel = new Label("");
        Button registerButton = new Button("register");
        registerButton.setOnAction(e -> {
            if(!User.isUsernameUnique(username.getText()) && User.isUsernameUnique(userId.getText())) {
                emptyLabel.setText("Username must be unique");
                username.clear();
                password.clear();
                verifyPassword.clear();
            }else if(!User.isUsernameUnique(userId.getText())){
                emptyLabel.setText("user id must be unique");
            } else if(password.getText().equals("")){
                emptyLabel.setText("please enter a password");
            }else if(!password.getText().equals(verifyPassword.getText())){
                emptyLabel.setText("passwords didn't match, try again!");
                password.clear();
                verifyPassword.clear();
            }else{
                User.createAndAddToDataBase(username.getText(), password.getText());
                loginPage();
            }
        });
        Button loginButton = new Button("login");
        loginButton.setOnAction(e -> loginPage());
        registerLayout.getChildren().addAll(title, usernameLabel, username, userIdLabel, userId, passwordLabel, password);
        registerLayout.getChildren().addAll(verifyPasswordLabel, verifyPassword, emptyLabel, registerButton, loginButton);
        registerLayout.setAlignment(Pos.CENTER);
        Scene registerScene = new Scene(registerLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(registerScene);
        Main.window.show();
    }
}
