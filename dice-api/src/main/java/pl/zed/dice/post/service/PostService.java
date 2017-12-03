package pl.zed.dice.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.comment.asm.CommentAsm;
import pl.zed.dice.comment.model.CommentDTO;
import pl.zed.dice.exception.post.PostNotFoundException;
import pl.zed.dice.exception.post.WrongOwnerException;
import pl.zed.dice.like.asm.LikeAsm;
import pl.zed.dice.like.model.LikeDTO;
import pl.zed.dice.post.asm.PostAsm;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.post.model.PostDTO;
import pl.zed.dice.post.repository.PostRepository;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.asm.UserAsm;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.repository.UserProfileRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private PostAsm postAsm;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private CommentAsm commentAsm;

    @Autowired
    private UserAsm userAsm;

    @Autowired
    private LikeAsm likeAsm;

    public PostDTO create(PostDTO postDTO){
        final UserProfile author = securityContextService.getCurrentUserProfile();
        Post post = postAsm.makePost(postDTO, author);

        List<Post> postList = author.getPosts();
        postList.add(post);

        author.setPosts(postList);
        postRepository.saveAll(postList);

        postDTO = postAsm.makePostDTO(post);
        postDTO.setComments(Collections.emptyList());
        postDTO.setLikes(Collections.emptyList());

        return postDTO;
    }

    public List<PostDTO> getPosts(Long profileId){
        final UserProfile profile = userProfileRepository.getOne(profileId);
        List<PostDTO> postDTOS = new ArrayList<>();

        postRepository.findByAuthorOrderByIdDesc(profile).forEach(
                post -> {
                    List<CommentDTO> comments = getAndConvertComments(post);
                    List<LikeDTO> likes = getAndConvertLikes(post);
                    PostDTO postDTO = postAsm.makePostDTO(post);
                    postDTO.setComments(comments);
                    postDTO.setLikes(likes);
                    postDTOS.add(postDTO);
                });

        return postDTOS;
    }

    public void delete(Long id){
        final UserProfile userProfile = securityContextService.getCurrentUserProfile();
        Post post = postRepository.getOne(id);

        checkForIllegal(post, userProfile, id);

        postRepository.delete(post);
    }

    public PostDTO edit(Long id, PostDTO postDTO){
        final UserProfile userProfile = securityContextService.getCurrentUserProfile();
        Post post = postRepository.getOne(id);

        checkForIllegal(post, userProfile, id);

        post.edit(postDTO);
        postRepository.save(post);

        return postAsm.makePostDTO(post);
    }

    private void checkForIllegal(Post post, UserProfile profile, Long id){
        if(post == null){
            throw new PostNotFoundException(id);
        }else if(!profile.getId().equals(post.getAuthor().getId())){
            throw new WrongOwnerException();
        }
    }

    private List<CommentDTO> getAndConvertComments(Post post){
        return post.getComments().stream()
                .map(comment -> commentAsm.makeCommentDto(comment, userAsm.makeUserProfileDTO(comment.getOwner())))
                .collect(Collectors.toList());
    }

    private List<LikeDTO> getAndConvertLikes(Post post){
        return post.getLikesEntities().stream()
                .map(like -> likeAsm.makeLikeDTO(like, userAsm.makeUserProfileDTO(like.getUser())))
                .collect(Collectors.toList());
    }
}
