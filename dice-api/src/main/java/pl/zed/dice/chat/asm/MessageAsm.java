package pl.zed.dice.chat.asm;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zed.dice.chat.domain.Attachment;
import pl.zed.dice.chat.domain.Chat;
import pl.zed.dice.chat.domain.CodeAttachment;
import pl.zed.dice.chat.domain.Message;
import pl.zed.dice.chat.model.AttachmentViewDTO;
import pl.zed.dice.chat.model.MessageCreateDTO;
import pl.zed.dice.chat.model.MessageViewDTO;
import pl.zed.dice.chat.repository.ChatRepository;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageAsm {

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private AttachmentAsm attachmentAsm;

    public MessageViewDTO makeMessageViewDTO(Message message) {
        List<AttachmentViewDTO> attachments =
                message.getAttachments().stream().map(
                                attachment -> attachmentAsm.makeAttachmentViewDto(attachment)
                        ).collect(Collectors.toList());
        return new MessageViewDTO(
                message.getId(),
                message.getSender().getId(),
                message.getContent(),
                message.getCreatedAt(),
                message.getChat().getId(),
                attachments);
    }

    public Message makeMessage(MessageCreateDTO messageCreateDTO, Long chatId) {
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        Chat chat = chatRepository.getOne(chatId);
        Date now = new Date();
        chat.setLastAction(now);
        Message message = new Message(messageCreateDTO.getContent(), now, currentUserProfile, chat);
        CodeAttachment codeAttachment = attachmentAsm.makeCodeAttachment(messageCreateDTO.getCode());
        codeAttachment.setMessage(message);
        List<Attachment> attachments = Lists.newArrayList(codeAttachment);
        message.setAttachments(attachments);
        return message;
    }
}
