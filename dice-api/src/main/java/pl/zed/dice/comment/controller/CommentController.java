package pl.zed.dice.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.zed.dice.comment.model.CommentDTO;
import pl.zed.dice.comment.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO create(@RequestBody CommentDTO commentDTO){
        return commentService.create(commentDTO);
    }

    @PutMapping("/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO edit(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        return commentService.edit(id, commentDTO);
    }

    @DeleteMapping("/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
        commentService.delete(id);
    }

}
