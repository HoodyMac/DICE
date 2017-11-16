package pl.zed.dice.chat.model;

import pl.zed.dice.chat.domain.AttachmentType;

public class CodeAttachmentViewDTO extends AttachmentViewDTO {

    private String language;
    private String code;
    private String comment;

    public CodeAttachmentViewDTO(String language, String code, String comment) {
        super(AttachmentType.CODE.name());
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
