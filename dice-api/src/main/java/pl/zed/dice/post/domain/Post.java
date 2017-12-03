package pl.zed.dice.post.domain;

import org.hibernate.validator.constraints.NotEmpty;
import pl.zed.dice.comment.domain.Comment;
import pl.zed.dice.like.domain.LikesEntity;
import pl.zed.dice.post.model.PostDTO;
import pl.zed.dice.user.profile.domain.UserProfile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "senderId")
    private UserProfile author;

    @NotNull
    @NotEmpty
    private String content;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<LikesEntity> likesEntities;

    public Post(){}

    public Post(UserProfile author, String content, Date created_date) {
        this.author = author;
        this.content = content;
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

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public UserProfile getAuthor() {
        return author;
    }

    public void setAuthor(UserProfile author) {
        this.author = author;
    }

    public void edit(PostDTO postDTO){
        this.content = postDTO.getContent();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<LikesEntity> getLikesEntities() {
        return likesEntities;
    }

    public void setLikesEntities(List<LikesEntity> likesEntities) {
        this.likesEntities = likesEntities;
    }
}
