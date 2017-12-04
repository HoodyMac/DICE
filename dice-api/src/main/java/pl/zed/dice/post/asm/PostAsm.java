package pl.zed.dice.post.asm;

import org.springframework.stereotype.Component;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.post.model.PostDTO;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.model.UserProfileDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PostAsm {

    public Post makePost(PostDTO postDTO, UserProfile author){
        return new Post(author, postDTO.getContent(), new Date());
    }

    public PostDTO makePostDTO(Post post, UserProfileDTO user){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String created_date = format.format(post.getCreated_date());
        return new PostDTO(post.getId(), user, post.getContent(), created_date,
                post.getAuthor().getCropImage());
    }

}
