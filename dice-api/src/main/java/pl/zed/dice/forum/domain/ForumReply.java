package pl.zed.dice.forum.domain;

import pl.zed.dice.user.profile.domain.UserProfile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public ForumReply() {
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
}
