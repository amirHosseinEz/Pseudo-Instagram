package Instagram.user;

import javax.persistence.Entity;

@Entity
public enum UserProfileRelType {

    FOLLOW,
    UNFOLLOW,
    BLOCK,
    UNBLOCK,

}