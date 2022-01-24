package Instagram.views;

import Instagram.main.Main;
import Instagram.user.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class UserProfileViews {
    public static HBox navbar(){
        Button homePage = new Button("home page");
        Button myPosts = new Button("my posts");
        Button createPost = new Button("create post");
        Button seeAllUserProfiles = new Button("all users");
        Button logoutButton = new Button("logout");
        //TODO set proper onActions
        homePage.setOnAction(e -> showHomePage(User.currentUser.getUserProfile()));
        myPosts.setOnAction(e -> showPage(User.currentUser.getUserProfile()));
        createPost.setOnAction(e -> createPost());
        seeAllUserProfiles.setOnAction(e -> showAllUsers());
        logoutButton.setOnAction(e -> {
            Register.loginPage();
        });
        HBox hbox = new HBox(Main.space);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(homePage, myPosts, createPost, seeAllUserProfiles, logoutButton);
        return hbox;
    }


    public static VBox displayPosts(List<Post> posts, Boolean showEdit, UserProfile userProfile, int backTo){
        VBox displayPostsLayout = new VBox(Main.space);
        displayPostsLayout.setAlignment(Pos.CENTER);
        Label title = new Label("Posts");
        ObservableList<HBox> productsList = FXCollections.observableArrayList();
        for(Post post: posts){
            HBox hbox = new HBox(Main.space);
            hbox.setAlignment(Pos.CENTER);
            Label textLabel = new Label(post.getCaption());
            Label dateLabel = new Label(post.getCreateTime().toString());
            Button userButton = new Button(post.getUserProfile().toString());
            userButton.setOnAction(e -> {
                showPage(post.getUserProfile());
            });
            Button likeButton = new Button("\uD83E\uDD0D");
            likeButton.setOnAction(e -> {
                UserProfilePostReaction.create(User.currentUser.getUserProfile(), post, UserProfilePostReactionType.LIKE);
                if(backTo == 1)
                    showPage(userProfile);
                else if(backTo == 2)
                    showHomePage(User.currentUser.getUserProfile());
            });
            if(UserProfilePostReaction.isLiked(User.currentUser.getUserProfile(), post)){
                likeButton.setText("like");
                likeButton.setOnAction(e -> {
                    UserProfilePostReaction.unlike(User.currentUser.getUserProfile(), post);
                    if(backTo == 1)
                        showPage(userProfile);
                    else if(backTo == 2)
                        showHomePage(User.currentUser.getUserProfile());
                });
            }
            Button viewDetail = new Button("detail");
            viewDetail.setOnAction(e -> viewPostDetail(post));
            hbox.getChildren().addAll(textLabel, dateLabel, userButton, likeButton, viewDetail);
            if(showEdit){
                Button editButton = new Button("edit");
                editButton.setOnAction(e -> editPost(post));
                Button deleteButton = new Button("delete");
                deleteButton.setOnAction(e ->{
                    Post.deletePost(post);
                    showHomePage(User.currentUser.getUserProfile());
                });
                hbox.getChildren().addAll(editButton, deleteButton);
            }
            productsList.add(hbox);
        }
        final ListView<HBox> listView = new ListView<HBox>(productsList);
        displayPostsLayout.getChildren().addAll(title, listView);
        return displayPostsLayout;
    }

    public static void viewPostDetail(Post post){
        List<Comment> comments = Comment.getCommentsOfPost(post);
        VBox displayPostLayout = new VBox(Main.space);
        displayPostLayout.setAlignment(Pos.CENTER);
        Label title = new Label("Posts");
        Label textLabel = new Label(post.getCaption());
        Label dateLabel = new Label(post.getCreateTime().toString());
        Label userProfile = new Label(post.getUserProfile().toString());
        Label likeCount = new Label(String.valueOf(Post.getLikesCount(post)));
        Button likeButton = new Button("\uD83E\uDD0D");
        likeButton.setOnAction(e -> {
            UserProfilePostReaction.create(User.currentUser.getUserProfile(), post, UserProfilePostReactionType.LIKE);
            viewPostDetail(post);
        });
        if(UserProfilePostReaction.isLiked(User.currentUser.getUserProfile(), post)){
            likeButton.setText("like");
            likeButton.setOnAction(e -> {
                UserProfilePostReaction.unlike(User.currentUser.getUserProfile(), post);
                viewPostDetail(post);
            });
        }
        if(post.getUserProfile().equals(User.currentUser.getUserProfile())) {
            Button editButton = new Button("edit");
            editButton.setOnAction(e -> editPost(post));
            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> {
                Post.deletePost(post);
                viewPostDetail(post);
            });
        }
        ObservableList<HBox> productsList = FXCollections.observableArrayList();
        for(Comment comment: comments){
            HBox hbox = new HBox(Main.space);
            hbox.setAlignment(Pos.CENTER);
            Label commentLabel = new Label(comment.getText());
            Label commentDate = new Label(comment.getCreateTime().toString());
            Button userButton = new Button(comment.getUserProfile().toString());
            userButton.setOnAction(e -> showPage(comment.getUserProfile()));
            hbox.getChildren().addAll(commentLabel, commentDate, userButton);
            productsList.add(hbox);
        }
        final ListView<HBox> listView = new ListView<HBox>(productsList);
        TextField commentField = new TextField();
        commentField.setMaxWidth((float)Main.screenWidth/3);
        Button commentButton = new Button("comment");
        commentButton.setOnAction(e -> {
            Comment.create(User.currentUser.getUserProfile(), post, commentField.getText());
            viewPostDetail(post);
        });
        displayPostLayout.getChildren().addAll(title, textLabel, dateLabel, userProfile, likeCount, likeButton);
        displayPostLayout.getChildren().addAll(listView, commentField, commentButton, navbar());
        Scene scene = new Scene(displayPostLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(scene);
    }

    public static void showAllUsers(){
        VBox showAllUsersLayout = new VBox(Main.space);
        showAllUsersLayout.setAlignment(Pos.CENTER);
        Label title = new Label("All Users");
        ObservableList<HBox> productsList = FXCollections.observableArrayList();
        for(UserProfile userProfile: UserProfile.getAllUserProfiles()){
            HBox hbox = new HBox(Main.space);
            hbox.setAlignment(Pos.CENTER);
            Button userProfileButton = new Button(userProfile.toString());
            userProfileButton.setOnAction(e -> showPage(userProfile));
            hbox.getChildren().addAll(userProfileButton);
            if(userProfile != User.currentUser.getUserProfile()){
                Button followButton = new Button("follow");
                followButton.setOnAction(e ->{
                    UserProfileRel.follow(User.currentUser.getUserProfile(), userProfile);
                    showAllUsers();
                });
                if(UserProfileRel.isFollowed(User.currentUser.getUserProfile(), userProfile)){
                    followButton.setText("unfollow");
                    followButton.setOnAction(e ->{
                        UserProfileRel.unFollow(User.currentUser.getUserProfile(), userProfile);
                        showAllUsers();
                    });
                }
                hbox.getChildren().addAll(followButton);
            }
            productsList.add(hbox);
        }
        final ListView<HBox> listView = new ListView<HBox>(productsList);
        showAllUsersLayout.getChildren().addAll(listView, navbar());
        Scene scene = new Scene(showAllUsersLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(scene);
    }

    public static void showHomePage(UserProfile userProfile){
        List<Post> posts = Post.getFriendsPosts(userProfile);
        VBox userPageLayout = displayPosts(posts, Boolean.FALSE, userProfile, 2);
        Label userProfileLabel = new Label(userProfile.toString());
        userPageLayout.getChildren().addAll(userProfileLabel);
        if(userProfile != User.currentUser.getUserProfile()){
            Button followButton = new Button("follow");
            followButton.setOnAction(e ->{
                UserProfileRel.follow(User.currentUser.getUserProfile(), userProfile);
                showPage(userProfile);
            });
            if(UserProfileRel.isFollowed(User.currentUser.getUserProfile(), userProfile)){
                followButton.setText("unfollow");
                followButton.setOnAction(e ->{
                    UserProfileRel.unFollow(User.currentUser.getUserProfile(), userProfile);
                    showPage(userProfile);
                });
            }
            userPageLayout.getChildren().addAll(followButton);
        }
        userPageLayout.getChildren().addAll(navbar());
        Scene page = new Scene(userPageLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(page);
    }

    public static void showPage(UserProfile userProfile){
        List<Post> posts = Post.getPostsOfUser(userProfile);
        Boolean edit = Boolean.FALSE;
        if(userProfile.getUser().equals(User.currentUser)){
            edit = Boolean.TRUE;
        }
        System.out.println(userProfile);
        System.out.println(User.currentUser.getUserProfile());
        System.out.println("this is edit:" + edit);
        VBox userPageLayout = displayPosts(posts, edit, userProfile, 1);
        Label userProfileLabel = new Label(userProfile.toString());
        userPageLayout.getChildren().addAll(userProfileLabel);
        if(userProfile != User.currentUser.getUserProfile()){
            Button followButton = new Button("follow");
            followButton.setOnAction(e ->{
                UserProfileRel.follow(User.currentUser.getUserProfile(), userProfile);
                showPage(userProfile);
            });
            if(UserProfileRel.isFollowed(User.currentUser.getUserProfile(), userProfile)){
                followButton.setText("unfollow");
                followButton.setOnAction(e ->{
                    UserProfileRel.unFollow(User.currentUser.getUserProfile(), userProfile);
                    showPage(userProfile);
                });
            }
            userPageLayout.getChildren().addAll(followButton);
        }
        userPageLayout.getChildren().addAll(navbar());
        Scene page = new Scene(userPageLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(page);
    }

    public static void createPost(){
        VBox createPostLayout = new VBox(Main.space);
        createPostLayout.setAlignment(Pos.CENTER);
        Label textLabel = new Label("text");
        TextField text = new TextField();
        text.setMaxWidth((float)Main.screenWidth/4);
        Button addButton = new Button("add");
        addButton.setOnAction(e -> {
            Post post = Post.create(User.currentUser.getUserProfile());
            post.setCaption(text.getText());
            Post.addToDataBase(post);
            text.clear();
        });
        createPostLayout.getChildren().addAll(textLabel, text);
        createPostLayout.getChildren().addAll(addButton, navbar());
        Scene scene = new Scene(createPostLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(scene);
    }

    public static void editPost(Post post){
        VBox editPostLayout = new VBox(Main.space);
        editPostLayout.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("edit");
        Label currentLabel = new Label(post.getCaption());
        Label textLabel = new Label("text");
        TextField text = new TextField();
        text.setMaxWidth((float)Main.screenWidth/4);
        Button editButton = new Button("edit");
        editButton.setOnAction(e -> {
            post.setCaption(text.getText());
            editPost(post);
        });
        editPostLayout.getChildren().addAll(titleLabel, currentLabel, textLabel, text);
        editPostLayout.getChildren().addAll(editButton, navbar());
        Scene scene = new Scene(editPostLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(scene);
    }

}
