package pl.zed.dice.comment.asm;

import org.springframework.stereotype.Component;
import pl.zed.dice.comment.domain.Comment;
import pl.zed.dice.comment.model.CommentDTO;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.model.UserProfileDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CommentAsm {

    public Comment makeComment(CommentDTO commentDTO, Post post, UserProfile profile){
        return new Comment(profile, post, commentDTO.getContent(), new Date());
    }

    public CommentDTO makeCommentDto(Comment comment, UserProfileDTO userProfileDTO){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String created_date = format.format(comment.getCreated_date());
        return new CommentDTO(comment.getId(), comment.getContent(), userProfileDTO, created_date);
    }

}
