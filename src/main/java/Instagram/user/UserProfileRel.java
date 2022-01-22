package Instagram.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserProfileRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UserProfile from;
    private UserProfile to;
    private UserProfileRelType relType;

    public UserProfile getFrom() {
        return from;
    }

    public void setFrom(UserProfile from) {
        this.from = from;
    }

    public UserProfile getTo() {
        return to;
    }

    public void setTo(UserProfile to) {
        this.to = to;
    }

    public UserProfileRelType getRelType() {
        return relType;
    }

    public void setRelType(UserProfileRelType relType) {
        this.relType = relType;
    }
}
