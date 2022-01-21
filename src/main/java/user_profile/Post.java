package main.java.user_profile;

import main.java.main.Main;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @Column(name = "id")
    private int id;

    @Transient
    private UserProfile userProfile;

    @Column(name = "caption")
    private String caption;

    @Column(name = "username")
    private String username;

    @Transient
    private Date date;

    @Column(name = "date")
    private String sdate;

    @Column(name = "photo")
    private String photo;

    public Post() {
    }

    public Post(UserProfile userProfile, String caption, String photo) {
        this.userProfile = userProfile;
        this.caption = caption;
        this.photo = photo;
        this.username = userProfile.getUser().getUsername();
    }
    public void createPost(Post post){
        Session session = Main.factory.getCurrentSession();
        Transaction transaction = null;
        try {
            session.beginTransaction();
            session.save(post);
            session.getTransaction().commit();;
        }
        finally {
            session.close();
        }
    }
    public List<Post> searchPostByUser(User user){
        Session session = Main.factory.getCurrentSession();
        Transaction transaction = null;
        List<Post> collection1 = new ArrayList<>();
        try {
            session.beginTransaction();
            Query query1 = session.createQuery("from Post where username = \'"+user.getUsername()+"\'");
            collection1 = query1.getResultList();
            session.getTransaction().commit();
        }
        finally {
            session.close();
        }
        return collection1;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
