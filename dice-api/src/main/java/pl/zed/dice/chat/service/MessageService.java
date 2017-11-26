package pl.zed.dice.chat.service;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.zed.dice.chat.asm.MessageAsm;
import pl.zed.dice.chat.domain.AttachmentType;
import pl.zed.dice.chat.domain.Chat;
import pl.zed.dice.chat.domain.FileAttachment;
import pl.zed.dice.chat.domain.Message;
import pl.zed.dice.chat.model.MessageCreateDTO;
import pl.zed.dice.chat.model.MessageViewDTO;
import pl.zed.dice.chat.repository.ChatRepository;
import pl.zed.dice.chat.repository.MessageRepository;
import pl.zed.dice.image.storage.StorageService;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private static final String DOT_SEPARATOR = ".";
    private static final String FILES_FOLDER = "files/";

    @Autowired
    private MessageAsm messageAsm;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private StorageService storageService;

    public List<MessageViewDTO> getMessagesForChat(Long chatId) {
        validateIfChatParticipant(chatId);

        List<Message> messages = messageRepository.findByChatId(chatId);
        return messages.stream().map(message -> messageAsm.makeMessageViewDTO(message)).collect(Collectors.toList());
    }

    public MessageViewDTO createMessageForChat(MessageCreateDTO messageDto, MultipartFile[] multipartFiles, Long chatId) throws IOException {
        validateIfChatParticipant(chatId);

        if (Strings.isNullOrEmpty(messageDto.getContent())) {
            throw new IllegalArgumentException("Message content can't be empty");
        }

        Message message = messageAsm.makeMessage(messageDto, chatId);
        for (MultipartFile multipartFile : multipartFiles) {
            String token = saveFile(multipartFile);
            message.getAttachments().add(new FileAttachment(AttachmentType.FILE, message, multipartFile.getOriginalFilename(), token));
        }
        messageRepository.save(message);

        return messageAsm.makeMessageViewDTO(message);
    }

    public List<MessageViewDTO> refreshMessages(Long lastAction) {
        Date lastActionAsDate = new Date(lastAction);
        UserProfile currentUserProfile = securityContextService.getCurrentUserProfile();

        List<Message> newMessages = messageRepository.findMessagesAfterDateForUser(currentUserProfile.getId(), lastActionAsDate);
        return newMessages.stream().map(message -> messageAsm.makeMessageViewDTO(message)).collect(Collectors.toList());
    }

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

    private String saveFile(MultipartFile file) throws IOException {
        String token = generateFilename(file.getInputStream(), file.getOriginalFilename());
        storageService.store(file.getInputStream(), FILES_FOLDER + token);
        return token;
    }

    private String generateFilename(InputStream inputStream, String originalFileName) throws IOException {
        String token = DigestUtils.md5DigestAsHex(inputStream)
                + DOT_SEPARATOR
                + Files.getFileExtension(originalFileName).toLowerCase();
        inputStream.close();
        return token;
    }
}
