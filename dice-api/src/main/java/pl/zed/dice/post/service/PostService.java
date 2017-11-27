package pl.zed.dice.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.exception.post.PostNotFoundException;
import pl.zed.dice.exception.post.WrongOwnerException;
import pl.zed.dice.post.asm.PostAsm;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.post.model.PostDTO;
import pl.zed.dice.post.repository.PostRepository;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.repository.UserProfileRepository;

import java.util.ArrayList;
import java.util.List;

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

    public PostDTO create(PostDTO postDTO){
        final UserProfile author = securityContextService.getCurrentUserProfile();
        Post post = postAsm.makePost(postDTO, author);

        List<Post> postList = author.getPosts();
        postList.add(post);

        author.setPosts(postList);

        postRepository.saveAll(postList);

        return postAsm.makePostDTO(post);
    }

    public List<PostDTO> getPosts(Long profileId){
        final UserProfile profile = userProfileRepository.getOne(profileId);
        List<PostDTO> postDTOS = new ArrayList<>();

        profile.getPosts().forEach(post -> postDTOS.add(postAsm.makePostDTO(post)));

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
}