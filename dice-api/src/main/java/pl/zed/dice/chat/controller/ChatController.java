package pl.zed.dice.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zed.dice.chat.model.ChatDTO;
import pl.zed.dice.chat.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

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
}
