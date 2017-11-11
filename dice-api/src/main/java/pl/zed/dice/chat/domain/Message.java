package pl.zed.dice.chat.domain;

import pl.zed.dice.user.profile.domain.UserProfile;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery(name = "Message.findMessagesAfterDateForUser", query = "SELECT m FROM Message m JOIN m.chat c JOIN c.participants cp WHERE cp.id = ?1 AND NOT m.sender.id = ?1 AND m.createdAt > ?2")
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private UserProfile sender;

    @ManyToOne
    private Chat chat;

    public Message() {
    }

    public Message(String content, Date createdAt, UserProfile sender, Chat chat) {
        this.content = content;
        this.createdAt = createdAt;
        this.sender = sender;
        this.chat = chat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public UserProfile getSender() {
        return sender;
    }

    public void setSender(UserProfile sender) {
        this.sender = sender;
    }
}
