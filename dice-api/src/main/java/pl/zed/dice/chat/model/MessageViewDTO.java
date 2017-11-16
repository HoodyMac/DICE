package pl.zed.dice.chat.model;

import java.util.Date;
import java.util.List;

public class MessageViewDTO {

    private Long id;
    private Long senderId;
    private String content;
    private Date createdAt;
    private Long chatId;
    private List<AttachmentViewDTO> attachments;

    public MessageViewDTO(Long id, Long senderId, String content, Date createdAt, Long chatId, List<AttachmentViewDTO> attachments) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = createdAt;
        this.chatId = chatId;
        this.attachments = attachments;
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

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public List<AttachmentViewDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentViewDTO> attachments) {
        this.attachments = attachments;
    }
}
