package pl.zed.dice.post.asm;

import org.springframework.stereotype.Component;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.post.model.PostDTO;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.Date;

@Component
public class PostAsm {

    public Post makePost(PostDTO postDTO, UserProfile author){
        return new Post(author, postDTO.getContent(), new Date());
    }

    public PostDTO makePostDTO(Post post){
        return new PostDTO(post.getId(), post.getAuthor().getFirstname() + " " + post.getAuthor().getLastname(),
                post.getContent(), post.getCreated_date().toString(), post.getAuthor().getCropImage());
    }

}
