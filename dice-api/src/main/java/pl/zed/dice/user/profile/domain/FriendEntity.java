package pl.zed.dice.user.profile.domain;

import javax.persistence.*;

@Entity
public class FriendEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserProfile requestor;
    @ManyToOne
    private UserProfile recipient;

    @Enumerated(value = EnumType.STRING)
    private FriendRequestStatus status;

    public FriendEntity(){}

    public FriendEntity(UserProfile requestor, UserProfile recipient, FriendRequestStatus status) {
        this.requestor = requestor;
        this.recipient = recipient;
        this.status = status;
    }

    public UserProfile getRequestor() {
        return requestor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRequestor(UserProfile requestor) {
        this.requestor = requestor;
    }

    public UserProfile getRecipient() {
        return recipient;
    }

    public void setRecipient(UserProfile recipient) {
        this.recipient = recipient;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }
}
