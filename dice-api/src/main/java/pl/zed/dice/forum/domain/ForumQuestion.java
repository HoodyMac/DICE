package pl.zed.dice.forum.domain;

import pl.zed.dice.like.domain.LikesEntity;
import pl.zed.dice.user.profile.domain.UserProfile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ForumQuestion {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "senderId")
    private UserProfile author;

    @ManyToMany
    @JoinTable(
            name="QUESTION_TAGS",
            joinColumns=@JoinColumn(name="POST_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="TAG_ID", referencedColumnName="ID"))
    private List<Tag> tags;

    @OneToMany(mappedBy = "forumQuestion")
    private List<ForumReply> replies = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "forumQuestion")
    private List<LikesEntity> likesEntities;

    public ForumQuestion() {
    }

    public ForumQuestion(String title, String content, Date createdAt, UserProfile author, List<Tag> tags) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<ForumReply> getReplies() {
        return replies;
    }

    public void setReplies(List<ForumReply> replies) {
        this.replies = replies;
    }

    public List<LikesEntity> getLikesEntities() {
        return likesEntities;
    }

    public void setLikesEntities(List<LikesEntity> likesEntities) {
        this.likesEntities = likesEntities;
    }
}
