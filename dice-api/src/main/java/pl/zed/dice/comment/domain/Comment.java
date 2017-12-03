package pl.zed.dice.comment.domain;

import pl.zed.dice.comment.model.CommentDTO;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.user.profile.domain.UserProfile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserProfile owner;

    @ManyToOne
    private Post post;

    @NotNull
    private String content;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_date;

    public Comment(){}

    public Comment(UserProfile owner, Post post, String content, Date created_date) {
        this.owner = owner;
        this.post = post;
        this.content = content;
        this.created_date = created_date;
    }

    public void edit(CommentDTO commentDTO){
        this.content = commentDTO.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getOwner() {
        return owner;
    }

    public void setOwner(UserProfile owner) {
        this.owner = owner;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}
