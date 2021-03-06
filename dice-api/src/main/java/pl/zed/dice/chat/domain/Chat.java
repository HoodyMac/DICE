package pl.zed.dice.chat.domain;

import pl.zed.dice.user.profile.domain.UserProfile;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Chat.findChatByParticipants", query = "SELECT c FROM Chat c JOIN c.participants cp WHERE cp.id IN (?1, ?2) GROUP BY c.id HAVING COUNT(c.id) = 2"),
        @NamedQuery(name = "Chat.findChatWithParticipant", query = "SELECT c FROM Chat c JOIN c.participants cp WHERE cp.id = ?1")
})
public class Chat {

    @Id
    @GeneratedValue
    private Long id;

    private ChatType type;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAction;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<UserProfile> participants;

    public Chat() {
    }

    public Chat(ChatType type, Date lastAction) {
        this.type = type;
        this.lastAction = lastAction;
    }

    public Chat(ChatType type, String name, Date lastAction) {
        this.type = type;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastAction() {
        return lastAction;
    }

    public void setLastAction(Date lastAction) {
        this.lastAction = lastAction;
    }

    public List<UserProfile> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserProfile> participants) {
        this.participants = participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
