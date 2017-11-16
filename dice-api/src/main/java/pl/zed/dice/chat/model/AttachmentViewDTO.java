package pl.zed.dice.chat.model;

public abstract class AttachmentViewDTO {

    private String type;

    public AttachmentViewDTO(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
