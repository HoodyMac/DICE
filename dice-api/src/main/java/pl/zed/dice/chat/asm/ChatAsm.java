package pl.zed.dice.chat.asm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.zed.dice.chat.domain.Chat;
import pl.zed.dice.chat.domain.Message;
import pl.zed.dice.chat.model.ChatDTO;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;

@Component
public class ChatAsm {

    @Autowired
    private SecurityContextService securityContextService;

    public ChatDTO makeChatDTO(Chat chat) {
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        UserProfile participantUserProfile = chat.getParticipants().stream().filter(userProfile -> !userProfile.getId().equals(currentUserProfile.getId())).findFirst().get();
        Message lastMessage = chat.getMessages().get(chat.getMessages().size()-1);
        return new ChatDTO(chat.getId(), participantUserProfile.getFullname(),
                participantUserProfile.getId(), participantUserProfile.getCropImage(),
                chat.getLastAction(), lastMessage.getContent(), lastMessage.getSender().getCropImage());
    }
}
