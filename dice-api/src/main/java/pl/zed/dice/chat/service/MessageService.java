package pl.zed.dice.chat.service;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.chat.asm.MessageAsm;
import pl.zed.dice.chat.domain.Chat;
import pl.zed.dice.chat.domain.Message;
import pl.zed.dice.chat.model.MessageViewDTO;
import pl.zed.dice.chat.model.MessageWriteDTO;
import pl.zed.dice.chat.repository.ChatRepository;
import pl.zed.dice.chat.repository.MessageRepository;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageAsm messageAsm;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private SecurityContextService securityContextService;

    public List<MessageViewDTO> getMessagesForChat(Long chatId) {
        validateIfChatParticipant(chatId);

        List<Message> messages = messageRepository.findByChatId(chatId);
        return messages.stream().map(message -> messageAsm.makeMessageViewDTO(message)).collect(Collectors.toList());
    }

    public MessageViewDTO createMessageForChat(MessageWriteDTO messageDto, Long chatId) {
        validateIfChatParticipant(chatId);

        if(Strings.isNullOrEmpty(messageDto.getContent())) {
            throw new IllegalArgumentException("Message content can't be empty");
        }

        Message message = messageAsm.makeMessage(messageDto, chatId);
        messageRepository.save(message);

        return messageAsm.makeMessageViewDTO(message);
    }

    private void validateIfChatParticipant(Long chatId) {
        // validate if chat participant
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        Chat chat = chatRepository.getOne(chatId);
        boolean isChatParticipant = false;
        for (UserProfile userProfile : chat.getParticipants()) {
            if(userProfile.getId().equals(currentUserProfile.getId())) {
                isChatParticipant = true;
            }
        }
        if(!isChatParticipant) {
            throw new SecurityException("You are not part of this chat");
        }
    }
}
