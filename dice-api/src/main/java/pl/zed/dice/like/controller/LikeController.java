package pl.zed.dice.like.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zed.dice.like.model.LikeDTO;
import pl.zed.dice.like.service.LikeService;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/{id}")
    public LikeDTO like(@PathVariable Long id){
        return likeService.create(id);
    }
}
