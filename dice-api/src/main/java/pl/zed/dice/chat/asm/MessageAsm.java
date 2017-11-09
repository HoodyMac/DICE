package pl.zed.dice.chat.asm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zed.dice.chat.domain.Chat;
import pl.zed.dice.chat.domain.Message;
import pl.zed.dice.chat.model.MessageViewDTO;
import pl.zed.dice.chat.model.MessageWriteDTO;
import pl.zed.dice.chat.repository.ChatRepository;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.Date;

@Component
public class MessageAsm {

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private ChatRepository chatRepository;

    public MessageViewDTO makeMessageViewDTO(Message message) {
        return new MessageViewDTO(message.getId(), message.getSender().getId(), message.getContent(), message.getCreatedAt());
    }

    public Message makeMessage(MessageWriteDTO messageWriteDTO, Long chatId) {
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        Chat chat = chatRepository.getOne(chatId);
        Date now = new Date();
        chat.setLastAction(now);
        return new Message(messageWriteDTO.getContent(), now, currentUserProfile, chat);
    }
}
