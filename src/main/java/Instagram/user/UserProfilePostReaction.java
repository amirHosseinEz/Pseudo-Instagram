//package Instagram.user;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//public class UserProfilePostReaction {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private UserProfile userProfile;
//    private Post post;
//    private UserProfilePostReactionType reactionType;
//
//    public UserProfile getUserProfile() {
//        return userProfile;
//    }
//
//    public void setUserProfile(UserProfile userProfile) {
//        this.userProfile = userProfile;
//    }
//
//    public Post getPost() {
//        return post;
//    }
//
//    public void setPost(Post post) {
//        this.post = post;
//    }
//
//    public UserProfilePostReactionType getReactionType() {
//        return reactionType;
//    }
//
//    public void setReactionType(UserProfilePostReactionType reactionType) {
//        this.reactionType = reactionType;
//    }
//}
