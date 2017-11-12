package pl.zed.dice.chat.model;

public class CodeAttachmentCreateDTO {

    private String language;
    private String code;
    private String comment;

    public CodeAttachmentCreateDTO() {
    }

    public CodeAttachmentCreateDTO(String language, String code, String comment) {
        this.language = language;
        this.code = code;
        this.comment = comment;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
