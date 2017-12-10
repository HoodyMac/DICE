package pl.zed.dice.chat.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.chat.asm.ChatAsm;
import pl.zed.dice.chat.domain.Chat;
import pl.zed.dice.chat.domain.ChatType;
import pl.zed.dice.chat.model.ChatDTO;
import pl.zed.dice.chat.repository.ChatRepository;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.repository.UserProfileRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private ChatAsm chatAsm;

    public List<ChatDTO> getAll() {
        Long currentUserProfileId = securityContextService.getCurrentUserProfile().getId();
        List<Chat> chats = chatRepository.findChatWithParticipant(currentUserProfileId);
        return chats.stream()
                .map(chat -> chatAsm.makeChatDTO(chat))
                .collect(Collectors.toList());
    }

    public ChatDTO create(Long participantId) {
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();
        UserProfile participantUserProfile = userProfileRepository.getOne(participantId);

        if(currentUserProfile.getId().equals(participantId)) {
            throw new IllegalArgumentException("Chat create chat with yourself");
        }

        Chat chatWithSameParticipants = chatRepository.findChatByParticipants(currentUserProfile.getId(), participantUserProfile.getId());
        if(chatWithSameParticipants != null) {
            throw new IllegalArgumentException("Chat is already created");
        }

        Chat chat = new Chat(ChatType.PM, new Date());
        List<UserProfile> participants = Lists.newArrayList(currentUserProfile, participantUserProfile);
        chat.setParticipants(participants);

        chatRepository.save(chat);
        return chatAsm.makeChatDTO(chat);
    }
}
