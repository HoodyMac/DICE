package pl.zed.dice.chat.asm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zed.dice.chat.domain.Chat;
import pl.zed.dice.chat.domain.Message;
import pl.zed.dice.chat.model.ChatDTO;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChatAsm {

    @Autowired
    private SecurityContextService securityContextService;

    public ChatDTO makeChatDTO(Chat chat) {
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        UserProfile participantUserProfile = chat.getParticipants().stream().filter(userProfile -> !userProfile.getId().equals(currentUserProfile.getId())).findFirst().get();
        Map<Long, String> participantsCroppedImages = new HashMap<>();
        chat.getParticipants().forEach(userProfile -> participantsCroppedImages.put(userProfile.getId(), userProfile.getCropImage()));

        String lastMessageText = "Started chat with you";
        String lastMessageSenderPhoto = null;
        if(chat.getMessages() != null && chat.getMessages().size() > 0) {
            Message lastMessage = chat.getMessages().get(chat.getMessages().size() - 1);
            lastMessageText = lastMessage.getContent();
            lastMessageSenderPhoto = lastMessage.getSender().getCropImage();
        }
        return new ChatDTO(chat.getId(), participantUserProfile.getFullname(),
                participantUserProfile.getId(), participantUserProfile.getCropImage(),
                chat.getLastAction(), lastMessageText, lastMessageSenderPhoto, participantsCroppedImages);
    }
}
