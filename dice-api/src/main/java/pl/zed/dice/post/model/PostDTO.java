package pl.zed.dice.post.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import pl.zed.dice.comment.model.CommentDTO;
import pl.zed.dice.like.model.LikeDTO;
import pl.zed.dice.user.profile.model.UserProfileDTO;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

    private Long id;
    private UserProfileDTO author;
    private String content;
    private String created_date;
    private String cropImg;
    private int commentsSize;
    private List<LikeDTO> likes;
    private List<CommentDTO> comments;

    public PostDTO(){}

    public PostDTO(Long id, UserProfileDTO author, String content, String created_date, String cropImg) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.created_date = created_date;
        this.cropImg = cropImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfileDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserProfileDTO author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCropImg() {
        return cropImg;
    }

    public void setCropImg(String cropImg) {
        this.cropImg = cropImg;
    }

    public List<LikeDTO> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeDTO> likes) {
        this.likes = likes;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public int getCommentsSize() {
        return commentsSize;
    }

    public void setCommentsSize(int commentsSize) {
        this.commentsSize = commentsSize;
    }
}
