package Instagram.main;


import Instagram.user.*;
import javafx.geometry.Pos;

import javax.swing.*;
import java.util.List;

public class Main{
    public static void main(String[] args) {

        User user = User.create("adfddfafaaa","ttt");
        User.addToDataBase(user);
        UserProfile userProfile = UserProfile.create(user);
        UserProfile.addToDataBase(userProfile);
        userProfile.setNickName("tt");

        User user2 = User.create("bbddffdbbb","sds");
        User.addToDataBase(user2);
        UserProfile userProfile2 = UserProfile.create(user2);
        UserProfile.addToDataBase(userProfile2);
        userProfile2.setNickName("yy");

        UserProfileRel.follow(userProfile, userProfile2);
        UserProfileRel.unFollow(userProfile, userProfile2);

        List<UserProfile> followers = UserProfileRel.getFollowers(userProfile2);

        //UserProfileRel userProfileRel = UserProfileRel.create(userProfile, userProfile2, UserProfileRelType.FOLLOW);
        //UserProfileRel.addToDataBase(userProfileRel);
        Post post = Post.create(userProfile);
        Post.addToDataBase(post);
        UserProfilePostReaction.like(userProfile, post);
        Comment comment = Comment.create(userProfile, post, "first comment");
        Comment.addToDataBase(comment);
        Comment comment1 = Comment.create(userProfile, post, "second comment");
        Comment.addToDataBase(comment1);
        CommentRel commentRel = CommentRel.create(comment, comment1, CommentRelType.REPLAY);
        CommentRel.addToDataBase(commentRel);
        //Comment.deleteComment(comment1);
        //CommentRel.delete(commentRel);
        //Post.deletePost(post);
        User.deleteUser(user);
       /* Post post1 = Post.create(userProfile);
        Post.addToDataBase(post1);
        post1.setCaption("firstpostr88zxx");*/
    /*    Comment comment1 = Comment.create(userProfile, post, "com1$))))))))))");
        Comment comment2 = Comment.create(userProfile, post, "com2$)))))))");
        Comment.addToDataBase(comment1);
        Comment.addToDataBase(comment2);
        CommentRel commentRel = CommentRel.create(comment1, comment2, CommentRelType.REPLAY);
        CommentRel.addToDataBase(commentRel);*/
        //Post.deletePost(post);




    }


}
