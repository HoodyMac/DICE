package pl.zed.dice.forum.model;

import pl.zed.dice.comment.model.CommentDTO;
import pl.zed.dice.forum.domain.Tag;
import pl.zed.dice.like.model.LikeDTO;

import java.util.Date;
import java.util.List;

public class ForumQuestionViewDTO {

    private Long id;
    private String title;
    private String authorName;
    private List<TagViewDTO> tags;
    private Date createdAt;
    private int commentsSize;
    private List<LikeDTO> likes;
    private List<CommentDTO> comments;

    public ForumQuestionViewDTO(Long id, String title, String authorName, List<TagViewDTO> tags, Date createdAt) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.tags = tags;
        this.createdAt = createdAt;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<TagViewDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagViewDTO> tags) {
        this.tags = tags;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCommentsSize() {
        return commentsSize;
    }

    public void setCommentsSize(int commentsSize) {
        this.commentsSize = commentsSize;
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
}
