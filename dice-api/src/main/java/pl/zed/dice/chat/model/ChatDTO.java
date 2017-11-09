package pl.zed.dice.chat.model;

import java.util.Date;

public class ChatDTO {

    private Long id;
    private String name;
    private Long participantId;
    private String cropImg;
    private Date lastAction;

    public ChatDTO(Long id, String name, Long participantId, String cropImg, Date lastAction) {
        this.id = id;
        this.name = name;
        this.participantId = participantId;
        this.cropImg = cropImg;
        this.lastAction = lastAction;
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
}
