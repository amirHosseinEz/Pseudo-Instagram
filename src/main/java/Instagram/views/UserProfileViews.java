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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserProfileViews {
    public static HBox navbar(){
        Button homePage = new Button("home page");
        Button myPosts = new Button("my posts");
        Button createPost = new Button("create post");
        Button seeAllUserProfiles = new Button("all users");
        Button logoutButton = new Button("logout");
        Button editProfile = new Button("edit profile");
        //TODO set proper onActions
        homePage.setOnAction(e -> showHomePage(User.currentUser.getUserProfile()));
        editProfile.setOnAction(e -> EditUserProfilePage.editUserPage());
        myPosts.setOnAction(e -> showPage(User.currentUser.getUserProfile()));
        createPost.setOnAction(e -> createPost());
        seeAllUserProfiles.setOnAction(e -> showAllUsers());
        logoutButton.setOnAction(e -> {
            Register.loginPage();
        });
        HBox hbox = new HBox(Main.space);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(homePage, myPosts, createPost, seeAllUserProfiles, logoutButton,editProfile);
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
            Button likeButton = new Button("Like");
            likeButton.setOnAction(e -> {
                UserProfilePostReaction userProfilePostReaction = UserProfilePostReaction.create(User.currentUser.getUserProfile(), post, UserProfilePostReactionType.LIKE);
                UserProfilePostReaction.addToDataBase(userProfilePostReaction);
                if(backTo == 1)
                    showPage(userProfile);
                else if(backTo == 2)
                    showHomePage(User.currentUser.getUserProfile());
            });
            if(UserProfilePostReaction.isLiked(User.currentUser.getUserProfile(), post)){
                likeButton.setText("Liked");
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
        //TODO: comment reply

        byte [] ImageArrayByte = post.getPhoto();
        javafx.scene.image.Image image = new javafx.scene.image.Image(new ByteArrayInputStream(ImageArrayByte));
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setImage(image);

        List<Comment> comments = Comment.getCommentsOfPost(post);
        VBox displayPostLayout = new VBox(Main.space);
        displayPostLayout.setAlignment(Pos.CENTER);
        Label title = new Label("Posts");
        Label textLabel = new Label(post.getCaption());
        Label dateLabel = new Label(post.getCreateTime().toString());
        Label userProfile = new Label(post.getUserProfile().toString());
        Label likeCount = new Label(String.valueOf(Post.getLikesCount(post)));
        Button likeButton = new Button("Like");
        likeButton.setOnAction(e -> {
            UserProfilePostReaction.create(User.currentUser.getUserProfile(), post, UserProfilePostReactionType.LIKE);
            likeButton.setText("Liked");
            viewPostDetail(post);
        });
        if(UserProfilePostReaction.isLiked(User.currentUser.getUserProfile(), post)){
            likeButton.setText("Liked");
            likeButton.setOnAction(e -> {
                UserProfilePostReaction.unlike(User.currentUser.getUserProfile(), post);
                viewPostDetail(post);
            });
        }
        displayPostLayout.getChildren().addAll(title,imageView, textLabel, dateLabel, userProfile, likeCount, likeButton);
        if(post.getUserProfile().isEqual(User.currentUser.getUserProfile())) {
            Button editButton = new Button("edit");
            editButton.setOnAction(e -> editPost(post));
            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(e -> {
                Post.deletePost(post);
                showHomePage(User.currentUser.getUserProfile());
            });
            displayPostLayout.getChildren().addAll(editButton, deleteButton);
        }
        ObservableList<HBox> commentList = FXCollections.observableArrayList();
        for(Comment comment: comments){
            HBox hbox = new HBox(Main.space);
            hbox.setAlignment(Pos.CENTER);
            Label commentId = new Label(Long.toString(comment.getId())+ "@");
            hbox.getChildren().addAll(commentId);
            if(CommentRel.getMainComment(comment) != null){
                Label commentRepliedToLabel = new Label("Replied to : " + Long.toString(CommentRel.getMainComment(comment).getId()) + "@ <---- ");
                hbox.getChildren().addAll(commentRepliedToLabel);
            }

            Label commentLabel = new Label(comment.getText());
            Label commentDate = new Label(comment.getCreateTime().toString());
            Button userButton = new Button(comment.getUserProfile().toString());
            userButton.setOnAction(e -> showPage(comment.getUserProfile()));
            hbox.getChildren().addAll(commentLabel, commentDate, userButton);
            commentList.add(hbox);
        }
        final ListView<HBox> listView = new ListView<HBox>(commentList);
        TextField commentField = new TextField();
        commentField.setMaxWidth((float)Main.screenWidth/3);
        Button commentButton = new Button("comment");
        commentButton.setOnAction(e -> {

            if(listView.getSelectionModel().getSelectedItem() != null) {
                HBox repliedTo = listView.getSelectionModel().getSelectedItem();
                String repliedToString = repliedTo.getChildren().get(0).toString();

                int index = repliedToString.substring(0,repliedToString.length()-2).indexOf('\'');
                long repliedToId = Long.valueOf(repliedToString.substring(index+1,repliedToString.length()-2));
                System.out.println("long is : "+Long.toString(repliedToId));

                Comment repliedToComment = Comment.getCommentFromId(repliedToId);
                Comment mainComment = Comment.create(User.currentUser.getUserProfile(), post, commentField.getText());
                CommentRel commentRel = CommentRel.create(mainComment,repliedToComment,CommentRelType.REPLAY);
                CommentRel.addToDataBase(commentRel);
                System.out.println("from "+commentRel.getFrom().getId());
                System.out.println("to "+commentRel.getTo().getId());

            }
            else {
                Comment comment = Comment.create(User.currentUser.getUserProfile(), post, commentField.getText());
                Comment.addToDataBase(comment);
            }
            viewPostDetail(post);
        });
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
            if(!userProfile.isEqual(User.currentUser.getUserProfile())){
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
                Button blockButton = new Button("block");
                blockButton.setOnAction(e ->{
                    UserProfileRel.block(User.currentUser.getUserProfile(), userProfile);
                    showPage(userProfile);
                });
                if(UserProfileRel.isBlocked(User.currentUser.getUserProfile(), userProfile)){
                    blockButton.setText("unblock");
                    blockButton.setOnAction(e ->{
                        UserProfileRel.unBlock(User.currentUser.getUserProfile(), userProfile);
                        showHomePage(User.currentUser.getUserProfile());
                    });
                }
                hbox.getChildren().addAll(followButton, blockButton);
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
        System.out.println(posts.size());
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        VBox userPageLayout = displayPosts(posts, Boolean.FALSE, userProfile, 2);
        Label userProfileLabel = new Label(userProfile.toString());
        userPageLayout.getChildren().addAll(userProfileLabel);
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
        VBox userPageLayout = displayPosts(posts, edit, userProfile, 1);
        Label userProfileLabel = new Label(userProfile.toString());
        userPageLayout.getChildren().addAll(userProfileLabel);
        if(!userProfile.isEqual(User.currentUser.getUserProfile())){
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
            Button blockButton = new Button("block");
            blockButton.setOnAction(e ->{
                UserProfileRel.block(User.currentUser.getUserProfile(), userProfile);
                showHomePage(User.currentUser.getUserProfile());
            });
            if(UserProfileRel.isBlocked(User.currentUser.getUserProfile(), userProfile)){
                blockButton.setText("unblock");
                blockButton.setOnAction(e ->{
                    UserProfileRel.unBlock(User.currentUser.getUserProfile(), userProfile);
                    showHomePage(User.currentUser.getUserProfile());
                });
            }
            userPageLayout.getChildren().addAll(followButton, blockButton);
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
        //TODO: add photo
        File noImage = new File("/home/reza/Desktop/hop/Instagram/src/main/java/Instagram/user/noPicture.png");
        javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(Image.getImageInput(noImage)));
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setImage(img);
        Button setPictureButton = new Button("Set picture");
        VBox pictureVbox =new VBox(5);
        pictureVbox.getChildren().addAll(imageView,setPictureButton);
        pictureVbox.setAlignment(Pos.CENTER);
        Post post = Post.create(User.currentUser.getUserProfile());
        addButton.setOnAction(e -> {
            Post.addToDataBase(post);
            post.setCaption(text.getText());
            text.clear();
        });
        FileChooser fileChooser = new FileChooser();
        setPictureButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(Main.window);
            byte [] newImageArrayByte = Image.getImageInput(selectedFile);
            javafx.scene.image.Image newImage = new javafx.scene.image.Image(new ByteArrayInputStream(newImageArrayByte));
            imageView.setImage(newImage);
            post.setPhoto(newImageArrayByte);
        });
        createPostLayout.getChildren().addAll(pictureVbox);
        createPostLayout.getChildren().addAll(textLabel, text);
        createPostLayout.getChildren().addAll(addButton, navbar());
        Scene scene = new Scene(createPostLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(scene);
    }

    public static void editPost(Post post){
        byte [] ImageArrayByte = post.getPhoto();
        javafx.scene.image.Image image = new javafx.scene.image.Image(new ByteArrayInputStream(ImageArrayByte));
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setImage(image);
        Button changePictureButton = new Button("Change picture");
        VBox pictureVbox = new VBox(5);
        pictureVbox.getChildren().addAll(imageView,changePictureButton);
        pictureVbox.setAlignment(Pos.CENTER);

        VBox editPostLayout = new VBox(Main.space);
        editPostLayout.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("edit");
        Label currentLabel = new Label(post.getCaption());
        Label textLabel = new Label("text");
        TextField text = new TextField();
        text.setMaxWidth((float)Main.screenWidth/4);
        Button editButton = new Button("edit");

        FileChooser fileChooser = new FileChooser();
        changePictureButton.setOnAction(e ->{
            File selectedFile = fileChooser.showOpenDialog(Main.window);
            byte [] newImageArrayByte = Image.getImageInput(selectedFile);
            javafx.scene.image.Image newImage = new javafx.scene.image.Image(new ByteArrayInputStream(newImageArrayByte));
            imageView.setImage(newImage);
            post.setPhoto(newImageArrayByte);
        });
        editButton.setOnAction(e -> {
            post.setCaption(text.getText());
            editPost(post);
        });
        editPostLayout.getChildren().addAll(titleLabel,pictureVbox, currentLabel, textLabel, text);
        editPostLayout.getChildren().addAll(editButton, navbar());
        Scene scene = new Scene(editPostLayout, Main.screenWidth, Main.screenHeight);
        Main.window.setScene(scene);
    }

}
