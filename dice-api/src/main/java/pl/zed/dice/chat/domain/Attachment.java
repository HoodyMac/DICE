package pl.zed.dice.chat.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Attachment {

    @Id
    @GeneratedValue
    private Long id;

    private AttachmentType type;

    @ManyToOne
    private Message message;

    public Attachment() {
    }

    public Attachment(AttachmentType type) {
        this.type = type;
    }

    public Attachment(AttachmentType type, Message message) {
        this.type = type;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }
}
