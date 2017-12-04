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
    private UserAsm userAsm;

    public PostDTO create(PostDTO postDTO){
        final UserProfile author = securityContextService.getCurrentUserProfile();
        Post post = postAsm.makePost(postDTO, author);

        List<Post> postList = author.getPosts();
        postList.add(post);

        author.setPosts(postList);
        postRepository.saveAll(postList);

        postDTO = postAsm.makePostDTO(post, userAsm.makeUserProfileDTO(author));
        postDTO.setComments(Collections.emptyList());
        postDTO.setLikes(Collections.emptyList());

        return postDTO;
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

        return postAsm.makePostDTO(post, userAsm.makeUserProfileDTO(userProfile));
    }

    private void checkForIllegal(Post post, UserProfile profile, Long id){
        if(post == null){
            throw new PostNotFoundException(id);
        }else if(!profile.getId().equals(post.getAuthor().getId())){
            throw new WrongOwnerException();
        }
    }
}
