package pl.zed.dice.like.asm;

import org.springframework.stereotype.Component;
import pl.zed.dice.like.domain.LikesEntity;
import pl.zed.dice.like.model.LikeDTO;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.model.UserProfileDTO;

@Component
public class LikeAsm {

    public LikesEntity makeLike(UserProfile user, Post post){
        return new LikesEntity(post, user);
    }

    public LikeDTO makeLikeDTO(LikesEntity likesEntity, UserProfileDTO user){
        return new LikeDTO(likesEntity.getId(), user);
    }
}
