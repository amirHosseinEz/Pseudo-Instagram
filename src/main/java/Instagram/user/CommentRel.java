package Instagram.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CommentRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Comment from;
    private Comment to;
    private CommentRelType relType;

    public Comment getFrom() {
        return from;
    }

    public void setFrom(Comment from) {
        this.from = from;
    }

    public Comment getTo() {
        return to;
    }

    public void setTo(Comment to) {
        this.to = to;
    }

    public CommentRelType getRelType() {
        return relType;
    }

    public void setRelType(CommentRelType relType) {
        this.relType = relType;
    }
}
