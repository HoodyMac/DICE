package pl.zed.dice.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.zed.dice.post.model.PostDTO;
import pl.zed.dice.post.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public PostDTO create(@RequestBody PostDTO postDTO){
        return postService.create(postDTO);
    }

    @GetMapping("/{id}")
    public List<PostDTO> getPosts(@PathVariable Long id){
        return postService.getPosts(id);
    }

    @DeleteMapping("/post/{id}")
    public void delete(@PathVariable Long id){
        postService.delete(id);
    }

    @PutMapping("/post/{id}")
    public PostDTO edit(@PathVariable Long id, @RequestBody PostDTO postDTO){
        return postService.edit(id, postDTO);
    }

}
