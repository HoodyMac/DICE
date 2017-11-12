package pl.zed.dice.chat.asm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zed.dice.chat.domain.Attachment;
import pl.zed.dice.chat.domain.Chat;
import pl.zed.dice.chat.domain.CodeAttachment;
import pl.zed.dice.chat.domain.Message;
import pl.zed.dice.chat.model.CodeAttachmentCreateDTO;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AttachmentAsm {

    @Autowired
    private SecurityContextService securityContextService;

    public Message createMessageWithCodeAttachment(CodeAttachmentCreateDTO codeAttachmentCreateDTO, Chat chat) {
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        Message message = new Message("", new Date(), currentUserProfile, chat);
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(new CodeAttachment(
                codeAttachmentCreateDTO.getLanguage(),
                codeAttachmentCreateDTO.getCode(),
                codeAttachmentCreateDTO.getComment(),
                message
        ));
        message.setAttachments(attachments);
        return message;
    }
}
