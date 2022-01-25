package Instagram.views;

import Instagram.main.Main;
import Instagram.user.Image;
import Instagram.user.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Date;

public class EditUserProfilePage {



    public static void editUserPage(){
        ImageView imageView = new ImageView();
        if(User.currentUser.getUserProfile().getImage() != null) {
            byte[] imageArrayByte = User.currentUser.getUserProfile().getImage();
            javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(imageArrayByte));
            imageView.setImage(img);
        }
        else{
            File noImage = new File("/home/reza/Desktop/hop/Instagram/src/main/java/Instagram/user/noPicture.png");
            javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(Image.getImageInput(noImage)));
            imageView.setImage(img);
        }
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        Button setPictureButton = new Button("Set picture");
        VBox pictureVbox =new VBox(5);
        pictureVbox.getChildren().addAll(imageView,setPictureButton);
        pictureVbox.setAlignment(Pos.CENTER);

        Label bioLabel = new Label("Bio");
        TextField bioText = new TextField();
        if(User.currentUser.getUserProfile().getBio() != null)
            bioText.setPromptText(User.currentUser.getUserProfile().getBio());
        bioText.setPrefHeight(80);
        bioText.setMaxHeight(80);
        HBox bioHbox = new HBox(10);
        bioHbox.getChildren().addAll(bioLabel,bioText);
        bioHbox.setAlignment(Pos.CENTER);

        VBox pageLayout = new VBox(Main.space);
        Label title = new Label("Edit profile");

        //Label usernameLabel = new Label("New username ");
        //TextField usernameText = new TextField();
        //usernameText.setMaxWidth((float)Main.screenWidth/4);
        //HBox usernameHbox = new HBox(10);
        //usernameHbox.getChildren().addAll(usernameLabel,usernameText);
        //usernameHbox.setAlignment(Pos.CENTER);


        Label nicknameLabel = new Label("New nickname");
        TextField nicknameText = new TextField();
        if(User.currentUser.getUserProfile().getNickName() != null)
            nicknameText.setPromptText(User.currentUser.getUserProfile().getNickName());
        nicknameText.setMaxWidth((float)Main.screenWidth/4);
        HBox nicknameHbox = new HBox(10);
        nicknameHbox.getChildren().addAll(nicknameLabel,nicknameText);
        nicknameHbox.setAlignment(Pos.CENTER);


        Label birthDateLabel = new Label("BirthDate");
        DatePicker birthDatePicker = new DatePicker();
        if(User.currentUser.getUserProfile().getDateOfBirth() != null) {
           birthDatePicker.setPromptText(User.currentUser.getUserProfile().getDateOfBirth().toString());
           //System.out.println("adaaadfafdfadfafafaffadsfa"+User.currentUser.getUserProfile().getDateOfBirth().toString());
       }
        HBox birthDateHBox = new HBox(10);
        birthDateHBox.getChildren().addAll(birthDateLabel,birthDatePicker);
        birthDateHBox.setAlignment(Pos.CENTER);

        Label firstnameLabel = new Label("New firstname");
        TextField firstnameText = new TextField();
        if(User.currentUser.getUserProfile().getFirstName() != null)
            firstnameText.setPromptText(User.currentUser.getUserProfile().getFirstName());
        firstnameText.setMaxWidth((float)Main.screenWidth/4);
        HBox firstnameHbox = new HBox(10);
        firstnameHbox.getChildren().addAll(firstnameLabel,firstnameText);
        firstnameHbox.setAlignment(Pos.CENTER);


        Label lastnameLabel = new Label("New lastname");
        TextField lastnameText = new TextField();
        if(User.currentUser.getUserProfile().getLastName() != null)
            lastnameText.setPromptText(User.currentUser.getUserProfile().getLastName());
        lastnameText.setMaxWidth((float)Main.screenWidth/4);
        HBox lastnameHbox = new HBox(10);
        lastnameHbox.getChildren().addAll(lastnameLabel,lastnameText);
        lastnameHbox.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back");
        HBox buttonHbox = new HBox(30);
        buttonHbox.setAlignment(Pos.CENTER);
        buttonHbox.getChildren().addAll(saveButton,backButton);

        FileChooser fileChooser = new FileChooser();

        saveButton.setOnAction(e -> {
            String bio = bioText.getText();
            //String username = usernameText.getText();
            String firstname = firstnameText.getText();
            String lastname = lastnameText.getText();
            String nickname = nicknameText.getText();
           if(birthDatePicker.getValue() != null) {

               Date birthDate = Date.valueOf(birthDatePicker.getValue());
               User.currentUser.getUserProfile().setDateOfBirth(birthDate);
           }
;
           if(!nickname.isEmpty())
                User.currentUser.getUserProfile().setNickName(nickname);
           if(!lastname.isEmpty())
                User.currentUser.getUserProfile().setLastName(lastname);
           if(!firstname.isEmpty())
                User.currentUser.getUserProfile().setFirstName(firstname);
           if(!bio.isEmpty())
                User.currentUser.getUserProfile().setBio(bio);
            //User.currentUser.setUsername(username);
            UserProfileViews.showHomePage(User.currentUser.getUserProfile());
        });

        backButton.setOnAction(e -> {
            UserProfileViews.showHomePage(User.currentUser.getUserProfile());
        });
        setPictureButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(Main.window);
            byte [] newImageArrayByte = Image.getImageInput(selectedFile);
            javafx.scene.image.Image newImage = new javafx.scene.image.Image(new ByteArrayInputStream(newImageArrayByte));
            imageView.setImage(newImage);
            User.currentUser.getUserProfile().setImage(newImageArrayByte);
        });

        pageLayout.getChildren().addAll(title,pictureVbox,bioHbox,firstnameHbox,nicknameHbox,lastnameHbox,birthDateHBox,buttonHbox);
        pageLayout.setAlignment(Pos.CENTER);

        Scene pageScene = new Scene(pageLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(pageScene);
    }
}
