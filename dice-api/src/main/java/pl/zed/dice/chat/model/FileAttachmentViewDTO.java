package pl.zed.dice.chat.model;

public class FileAttachmentViewDTO extends AttachmentViewDTO {

    private String originalFilename;
    private String token;

    public FileAttachmentViewDTO(String type, String originalFilename, String token) {
        super(type);
        this.originalFilename = originalFilename;
        this.token = token;
    }

    public FileAttachmentViewDTO(String type, String token) {
        super(type);
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
