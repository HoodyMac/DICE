package pl.zed.dice.like.domain;

import pl.zed.dice.forum.domain.ForumQuestion;
import pl.zed.dice.forum.domain.ForumReply;
import pl.zed.dice.post.domain.Post;
import pl.zed.dice.user.profile.domain.UserProfile;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LikesEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private ForumQuestion forumQuestion;

    @ManyToOne
    private ForumReply forumReply;

    @ManyToOne
    private UserProfile user;

    public LikesEntity(){}

    public LikesEntity(Post post, UserProfile user) {
        this.post = post;
        this.user = user;
    }

    public LikesEntity(ForumQuestion forumQuestion, UserProfile user) {
        this.forumQuestion = forumQuestion;
        this.user = user;
    }

    public LikesEntity(ForumReply forumReply, UserProfile user) {
        this.forumReply = forumReply;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public ForumQuestion getForumQuestion() {
        return forumQuestion;
    }

    public void setForumQuestion(ForumQuestion forumQuestion) {
        this.forumQuestion = forumQuestion;
    }

    public ForumReply getForumReply() {
        return forumReply;
    }

    public void setForumReply(ForumReply forumReply) {
        this.forumReply = forumReply;
    }
}
