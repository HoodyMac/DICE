package pl.zed.dice.chat.model;

import java.util.Date;

public class MessageViewDTO {

    private Long id;
    private Long senderId;
    private String content;
    private Date createdAt;

    public MessageViewDTO(Long id, Long senderId, String content, Date createdAt) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
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
}