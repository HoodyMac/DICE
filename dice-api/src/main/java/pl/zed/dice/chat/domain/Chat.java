package pl.zed.dice.chat.domain;

import pl.zed.dice.user.profile.domain.UserProfile;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Chat {

    @Id
    @GeneratedValue
    private Long id;

    private ChatType type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAction;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<UserProfile> participants;

    public Chat(ChatType type, Date lastAction) {
        this.type = type;
        this.lastAction = lastAction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public Date getLastAction() {
        return lastAction;
    }

    public void setLastAction(Date lastAction) {
        this.lastAction = lastAction;
    }
}
