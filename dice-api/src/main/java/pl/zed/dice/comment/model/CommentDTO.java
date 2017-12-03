package pl.zed.dice.comment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import pl.zed.dice.user.profile.model.UserProfileDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {

    private Long id;

    private String content;

    private UserProfileDTO owner;

    private String created_date;

    private Long post;

    public CommentDTO(){}

    public CommentDTO(Long id, String content, UserProfileDTO owner, String created_date) {
        this.id = id;
        this.content = content;
        this.owner = owner;
        this.created_date = created_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserProfileDTO getOwner() {
        return owner;
    }

    public void setOwner(UserProfileDTO owner) {
        this.owner = owner;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }
}
