package pl.zed.dice.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zed.dice.chat.model.ChatDTO;
import pl.zed.dice.chat.model.MessageViewDTO;
import pl.zed.dice.chat.model.MessageWriteDTO;
import pl.zed.dice.chat.service.ChatService;
import pl.zed.dice.chat.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

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
    public ResponseEntity<MessageViewDTO> createMessage(@RequestBody MessageWriteDTO messageDto, @PathVariable("chatId") Long chatId) {
        MessageViewDTO message = messageService.createMessageForChat(messageDto, chatId);
        return ResponseEntity.ok(message);
    }

}
