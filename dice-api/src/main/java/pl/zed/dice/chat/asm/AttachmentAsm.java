package pl.zed.dice.chat.asm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zed.dice.chat.domain.Attachment;
import pl.zed.dice.chat.domain.AttachmentType;
import pl.zed.dice.chat.domain.CodeAttachment;
import pl.zed.dice.chat.domain.FileAttachment;
import pl.zed.dice.chat.model.AttachmentViewDTO;
import pl.zed.dice.chat.model.CodeAttachmentCreateDTO;
import pl.zed.dice.chat.model.CodeAttachmentViewDTO;
import pl.zed.dice.chat.model.FileAttachmentViewDTO;
import pl.zed.dice.security.service.SecurityContextService;

@Component
public class AttachmentAsm {

    @Autowired
    private SecurityContextService securityContextService;

    public CodeAttachment makeCodeAttachment(CodeAttachmentCreateDTO codeAttachmentCreateDTO) {
        return new CodeAttachment(
                codeAttachmentCreateDTO.getLanguage(),
                codeAttachmentCreateDTO.getCode(),
                codeAttachmentCreateDTO.getComment());
    }

    public AttachmentViewDTO makeAttachmentViewDto(Attachment attachment) {
        if (attachment.getType().equals(AttachmentType.CODE)) {
            CodeAttachment codeAttachment = (CodeAttachment) attachment;
            return new CodeAttachmentViewDTO(codeAttachment.getLanguage(), codeAttachment.getCode(), codeAttachment.getComment());
        } else if(attachment.getType().equals(AttachmentType.FILE)) {
            FileAttachment fileAttachment = (FileAttachment) attachment;
            return new FileAttachmentViewDTO(AttachmentType.FILE.name(), fileAttachment.getOriginalFilename(), fileAttachment.getToken());
        }
        return null;
    }
}
