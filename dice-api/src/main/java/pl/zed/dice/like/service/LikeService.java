package pl.zed.dice.like.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.like.repository.LikeRepostitory;
import pl.zed.dice.like.asm.LikeAsm;
import pl.zed.dice.like.domain.LikesEntity;
import pl.zed.dice.like.model.LikeDTO;
import pl.zed.dice.post.asm.PostAsm;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.post.repository.PostRepository;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.asm.UserAsm;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepostitory likeRepostitory;

    @Autowired
    private UserAsm userAsm;

    @Autowired
    private PostAsm postAsm;

    @Autowired
    private LikeAsm likeAsm;

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private PostRepository postRepository;

    public LikeDTO create(Long id){
        final UserProfile user = securityContextService.getCurrentUserProfile();
        Post post = postRepository.getOne(id);
        LikesEntity likesEntity = likeRepostitory.findOneByPostAndUser(post, user);

        if (likesEntity == null) {
            List<LikesEntity> likesEntities = post.getLikesEntities();
            likesEntity = new LikesEntity(post, user);
            likesEntities.add(likesEntity);
            post.setLikesEntities(likesEntities);
            likeRepostitory.saveAll(likesEntities);
        } else
            likeRepostitory.delete(likesEntity);

        return likeAsm.makeLikeDTO(likesEntity, userAsm.makeUserProfileDTO(user));
    }

}
