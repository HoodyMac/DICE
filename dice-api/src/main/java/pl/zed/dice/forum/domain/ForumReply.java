package pl.zed.dice.forum.domain;

import pl.zed.dice.like.domain.LikesEntity;
import pl.zed.dice.user.profile.domain.UserProfile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class ForumReply {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "senderId")
    private UserProfile author;

    @ManyToOne
    private ForumQuestion forumQuestion;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "forumReply")
    private List<LikesEntity> likesEntities;

    public ForumReply() {
    }

    public ForumReply(String content, UserProfile author, Date createdAt) {
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
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

    public UserProfile getAuthor() {
        return author;
    }

    public void setAuthor(UserProfile author) {
        this.author = author;
    }

    public ForumQuestion getForumQuestion() {
        return forumQuestion;
    }

    public void setForumQuestion(ForumQuestion forumQuestion) {
        this.forumQuestion = forumQuestion;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<LikesEntity> getLikesEntities() {
        return likesEntities;
    }

    public void setLikesEntities(List<LikesEntity> likesEntities) {
        this.likesEntities = likesEntities;
    }
}
