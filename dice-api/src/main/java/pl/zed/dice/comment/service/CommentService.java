package pl.zed.dice.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.comment.asm.CommentAsm;
import pl.zed.dice.comment.domain.Comment;
import pl.zed.dice.comment.model.CommentDTO;
import pl.zed.dice.comment.repository.CommentRepository;
import pl.zed.dice.exception.post.WrongOwnerException;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.post.repository.PostRepository;
import pl.zed.dice.security.service.SecurityContextService;
import pl.zed.dice.user.profile.asm.UserAsm;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentAsm commentAsm;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SecurityContextService securityContextService;

    @Autowired
    private UserAsm userAsm;

    public CommentDTO create(CommentDTO commentDTO){
        final UserProfile userProfile = securityContextService.getCurrentUserProfile();
        final Post post = postRepository.getOne(commentDTO.getPost());
        Comment comment = commentAsm.makeComment(commentDTO, post, userProfile);
        List<Comment> comments = post.getComments();
        comments.add(comment);
        post.setComments(comments);

        commentRepository.saveAll(comments);
        return commentAsm.makeCommentDto(comment, userAsm.makeUserProfileDTO(userProfile));
    }

    public CommentDTO edit(Long id, CommentDTO commentDTO){
        final UserProfile userProfile = securityContextService.getCurrentUserProfile();
        Comment comment = commentRepository.getOne(id);

        checkForIllegal(userProfile, comment.getOwner());

        comment.edit(commentDTO);
        comment = commentRepository.save(comment);

        return commentAsm.makeCommentDto(comment, userAsm.makeUserProfileDTO(userProfile));
    }

    public void delete(Long id){
        UserProfile profile = securityContextService.getCurrentUserProfile();
        Comment comment = commentRepository.getOne(id);

        checkForIllegal(profile, comment.getOwner());

        commentRepository.delete(comment);
    }

    private void checkForIllegal(UserProfile loggedUser, UserProfile owner){
        if (!loggedUser.getId().equals(owner.getId())){
            throw new WrongOwnerException();
        }
    }

}
