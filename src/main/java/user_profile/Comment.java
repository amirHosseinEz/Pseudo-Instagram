package main.java.user_profile;

import main.java.main.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment {
    @Transient
    private UserProfile userProfile;

    @Transient
    private Post post;

    @Id
    @Column(name = "comment")
    private int id;

    @Column(name = "post_id")
    private int postid;

    @Column(name = "text")
    private String text;

    @Transient
    private Date date;

    @Column(name = "time")
    private String sDate;

    @Column(name = "username")
    private String username;
    public Comment() {
    }

    public Comment(UserProfile userProfile, Post post, String text) {
        this.userProfile = userProfile;
        this.post = post;
        this.text = text;
        this.username = userProfile.getUser().getUsername();
    }
    public void createComment(Comment comment){
        Session session = Main.factory.getCurrentSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            session.save(comment);
            session.getTransaction().commit();;
        }
        finally {
            session.close();
        }
    }
    public List<Comment> searchCommentByPost(Post post){
        Session session = Main.factory.getCurrentSession();
        Transaction transaction = null;
        List<Comment> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from Comment where postId = \'"+post.getId()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        return collection1;
    }
    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }
}
