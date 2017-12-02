package pl.zed.dice.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.zed.dice.chat.model.ChatDTO;
import pl.zed.dice.chat.model.MessageViewDTO;
import pl.zed.dice.chat.model.MessageCreateDTO;
import pl.zed.dice.chat.service.ChatService;
import pl.zed.dice.chat.service.MessageService;
import pl.zed.dice.image.storage.StorageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private StorageService storageService;

    @GetMapping("/all")
    public ResponseEntity<List<ChatDTO>> getAll() {
        List<ChatDTO> chats = chatService.getAll();
        return ResponseEntity.ok(chats);
    }

    @PostMapping("/{participantId}")
    public ResponseEntity<ChatDTO> create(@PathVariable("participantId") Long participantId) {
        ChatDTO chatDTO = chatService.create(participantId);
        return ResponseEntity.ok(chatDTO);
    }

    @GetMapping("/messages/{chatId}")
    public ResponseEntity<List<MessageViewDTO>> getMessages(@PathVariable("chatId") Long chatId) {
        List<MessageViewDTO> messagesForChat = messageService.getMessagesForChat(chatId);
        return ResponseEntity.ok(messagesForChat);
    }

    @PostMapping("/messages/{chatId}")
    public ResponseEntity<MessageViewDTO> createMessage(@RequestParam("message") String messageDtoJson, @PathVariable("chatId") Long chatId, @RequestParam("files[]") MultipartFile[] multipartFiles) throws IOException {
        for (MultipartFile multipartFile : multipartFiles) {
            System.out.println(multipartFile.getOriginalFilename());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        MessageCreateDTO messageDto = objectMapper.readValue(messageDtoJson, MessageCreateDTO.class);
        MessageViewDTO message = messageService.createMessageForChat(messageDto, multipartFiles, chatId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/messages/refresh/{lastAction}")
    public ResponseEntity<List<MessageViewDTO>> refreshMessages(@PathVariable("lastAction") Long lastAction) {
        List<MessageViewDTO> messages = messageService.refreshMessages(lastAction);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/file/{filename:.+}/")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable("filename") String filename) throws IOException {
        Resource file = storageService.loadAsResource("files/" + filename);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @GetMapping("/file/{filename:.+}/{original:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename, @PathVariable("original") String originalFilename) throws IOException {
        Resource file = storageService.loadAsResource("files/" + filename);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+originalFilename+"\"")
                .body(file);
    }
}
