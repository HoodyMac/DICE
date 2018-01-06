package pl.zed.dice.like.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.zed.dice.like.model.LikeDTO;
import pl.zed.dice.like.service.LikeService;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/{id}")
    public LikeDTO like(@PathVariable Long id, @RequestBody String type){
        if (type.contains("post")) {
            return likeService.createLikeForPost(id);
        }else if (type.contains("question")) {
            return likeService.createLikeForQuestion(id);
        }else
            return likeService.createLikeForReply(id);
    }
}
