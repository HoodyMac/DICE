package pl.zed.dice.like.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.forum.domain.ForumQuestion;
import pl.zed.dice.forum.domain.ForumReply;
import pl.zed.dice.forum.repository.ForumQuestionRepository;
import pl.zed.dice.forum.repository.ForumReplyRepository;
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

import java.util.ArrayList;
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

    @Autowired
    private ForumQuestionRepository forumQuestionRepository;

    @Autowired
    private ForumReplyRepository forumReplyRepository;

    public LikeDTO createLikeForPost(Long id){
        final UserProfile user = securityContextService.getCurrentUserProfile();
        Post post = postRepository.getOne(id);
        LikesEntity likesEntity = likeRepostitory.findOneByPostAndUser(post, user);
        return handleLikeCreation(post, user, likesEntity);
    }

    public LikeDTO createLikeForQuestion(Long id) {
        final UserProfile user = securityContextService.getCurrentUserProfile();
        ForumQuestion question = forumQuestionRepository.getOne(id);
        LikesEntity likesEntity = likeRepostitory.findOneByForumQuestionAndUser(question, user);
        return handleLikeCreation(question, user, likesEntity);
    }

    public LikeDTO createLikeForReply(Long id) {
        final UserProfile user = securityContextService.getCurrentUserProfile();
        ForumReply reply = forumReplyRepository.getOne(id);
        LikesEntity likesEntity = likeRepostitory.findOneByForumReplyAndUser(reply, user);
        return handleLikeCreation(reply, user, likesEntity);
    }

    private LikeDTO handleLikeCreation(Object post, UserProfile user, LikesEntity likesEntity) {
        if (likesEntity == null) {
            likesEntity = doLike(post, user);
        } else
            likeRepostitory.delete(likesEntity);

        return likeAsm.makeLikeDTO(likesEntity, userAsm.makeUserProfileDTO(user));
    }

    private LikesEntity doLike(Object post, UserProfile user) {
        LikesEntity likesEntity = null;
        if(post instanceof Post) {
            likesEntity = handleLikesForPost(((Post) post), user);
        }else if(post instanceof ForumQuestion) {
            likesEntity = handleLikesForQuestion(((ForumQuestion) post), user);
        }else  if(post instanceof ForumReply) {
            likesEntity = handleLikesForReply(((ForumReply) post), user);
        }
        return likesEntity;
    }

    private LikesEntity handleLikesForPost(Post post, UserProfile user) {
        List<LikesEntity> likesEntities = post.getLikesEntities();
        LikesEntity likesEntity = new LikesEntity(post, user);
        likesEntities.add(likesEntity);
        post.setLikesEntities(likesEntities);
        likeRepostitory.saveAll(likesEntities);
        return likesEntity;
    }

    private LikesEntity handleLikesForQuestion(ForumQuestion post, UserProfile user) {
        List<LikesEntity> likesEntities = post.getLikesEntities();
        LikesEntity likesEntity = new LikesEntity(post, user);
        likesEntities.add(likesEntity);
        post.setLikesEntities(likesEntities);
        likeRepostitory.saveAll(likesEntities);
        return likesEntity;
    }

    private LikesEntity handleLikesForReply(ForumReply post, UserProfile user) {
        List<LikesEntity> likesEntities = post.getLikesEntities();
        LikesEntity likesEntity = new LikesEntity(post, user);
        likesEntities.add(likesEntity);
        post.setLikesEntities(likesEntities);
        likeRepostitory.saveAll(likesEntities);
        return likesEntity;
    }
}
