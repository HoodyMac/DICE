package pl.zed.dice.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.zed.dice.chat.model.CodeAttachmentCreateDTO;
import pl.zed.dice.chat.service.AttachmentService;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/{chatId}/code")
    public void createCodeAttachment(@PathVariable("chatId") Long chatId, @RequestBody CodeAttachmentCreateDTO codeAttachmentCreateDTO) {
        attachmentService.createCodeAttachment(chatId, codeAttachmentCreateDTO);
    }
}
