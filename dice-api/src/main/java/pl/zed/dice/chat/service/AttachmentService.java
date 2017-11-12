package pl.zed.dice.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.chat.asm.AttachmentAsm;
import pl.zed.dice.chat.domain.Chat;
import pl.zed.dice.chat.domain.Message;
import pl.zed.dice.chat.model.CodeAttachmentCreateDTO;
import pl.zed.dice.chat.repository.ChatRepository;
import pl.zed.dice.chat.repository.MessageRepository;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;

@Service
public class AttachmentService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AttachmentAsm attachmentAsm;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private SecurityContextService securityContextService;

    public void createCodeAttachment(Long chatId, CodeAttachmentCreateDTO codeAttachmentCreateDTO) {
        validateIfChatParticipant(chatId);

        Chat chat = chatRepository.getOne(chatId);
        Message message = attachmentAsm.createMessageWithCodeAttachment(codeAttachmentCreateDTO, chat);
        messageRepository.save(message);
    }

    @SuppressWarnings("Duplicates")
    private void validateIfChatParticipant(Long chatId) {
        // validate if chat participant
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        Chat chat = chatRepository.getOne(chatId);
        boolean isChatParticipant = false;
        for (UserProfile userProfile : chat.getParticipants()) {
            if (userProfile.getId().equals(currentUserProfile.getId())) {
                isChatParticipant = true;
            }
        }
        if (!isChatParticipant) {
            throw new SecurityException("You are not part of this chat");
        }
    }
}
