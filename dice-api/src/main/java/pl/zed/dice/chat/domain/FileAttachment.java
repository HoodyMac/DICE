package pl.zed.dice.chat.domain;

import javax.persistence.Entity;

@Entity
public class FileAttachment extends Attachment {

    private String originalFilename;

    private String token;

    public FileAttachment() {
    }

    public FileAttachment(AttachmentType type, Message message, String originalFilename, String token) {
        super(type, message);
        this.originalFilename = originalFilename;
        this.token = token;
    }

    public FileAttachment(AttachmentType type, Message message, String token) {
        super(type, message);
        this.token = token;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
