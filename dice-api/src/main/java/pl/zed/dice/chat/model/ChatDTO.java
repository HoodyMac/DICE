package pl.zed.dice.chat.model;

import java.util.Date;

public class ChatDTO {

    private Long id;
    private String name;
    private Long participantId;
    private String cropImg;
    private Date lastAction;
    private String lastMessage;
    private String lastMessageSenderPhoto;

    public ChatDTO(Long id, String name, Long participantId, String cropImg, Date lastAction, String lastMessage, String lastMessageSenderPhoto) {
        this.id = id;
        this.name = name;
        this.participantId = participantId;
        this.cropImg = cropImg;
        this.lastAction = lastAction;
        this.lastMessage = lastMessage;
        this.lastMessageSenderPhoto = lastMessageSenderPhoto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCropImg() {
        return cropImg;
    }

    public void setCropImg(String cropImg) {
        this.cropImg = cropImg;
    }

    public Date getLastAction() {
        return lastAction;
    }

    public void setLastAction(Date lastAction) {
        this.lastAction = lastAction;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageSenderPhoto() {
        return lastMessageSenderPhoto;
    }

    public void setLastMessageSenderPhoto(String lastMessageSenderPhoto) {
        this.lastMessageSenderPhoto = lastMessageSenderPhoto;
    }
}
