package pl.zed.dice.chat.model;

public class MessageCreateDTO {

    private String content;
    private CodeAttachmentCreateDTO code;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CodeAttachmentCreateDTO getCode() {
        return code;
    }

    public void setCode(CodeAttachmentCreateDTO code) {
        this.code = code;
    }
}
