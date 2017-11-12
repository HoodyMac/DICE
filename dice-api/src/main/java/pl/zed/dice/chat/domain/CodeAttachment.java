package pl.zed.dice.chat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CodeAttachment extends Attachment {

    private String language;

    @Column(length = 8192)
    private String code;

    private String comment;

    public CodeAttachment(String language, String code, String comment, Message message) {
        super(AttachmentType.CODE, message);
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
