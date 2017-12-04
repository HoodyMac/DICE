package pl.zed.dice.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zed.dice.forum.domain.Tag;
import pl.zed.dice.forum.model.ForumQuestionCreateDTO;
import pl.zed.dice.forum.model.ForumQuestionDetailsDTO;
import pl.zed.dice.forum.model.ForumQuestionViewDTO;
import pl.zed.dice.forum.service.ForumService;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping("/question")
    public ResponseEntity<List<ForumQuestionViewDTO>> getAllForumQuestions() {
        return ResponseEntity.ok(forumService.getAllForumQuestions() );
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<ForumQuestionDetailsDTO> getForumQuestion(@PathVariable("id") Long postId) {
        return ResponseEntity.ok(forumService.getForumQuestion(postId));
    }


    @PostMapping("/question")
    public ResponseEntity<ForumQuestionViewDTO> createForumQuestion(@RequestBody ForumQuestionCreateDTO forumQuestionCreateDTO) {
        ForumQuestionViewDTO forumQuestion = forumService.createForumQuestion(forumQuestionCreateDTO);
        return ResponseEntity.ok(forumQuestion);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> allTags = forumService.getAllTags();
        return ResponseEntity.ok(allTags);
    }
}
