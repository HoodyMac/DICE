package pl.zed.dice.forum.model;

import pl.zed.dice.comment.model.CommentDTO;
import pl.zed.dice.like.model.LikeDTO;

import java.util.Date;
import java.util.List;

public class ForumReplyViewDTO {

    private Long id;
    private String content;
    private Long authorId;
    private String authorName;
    private Date createdAt;
    private int commentsSize;
    private List<LikeDTO> likes;
    private List<CommentDTO> comments;

    public ForumReplyViewDTO(Long id, String content, Long authorId, String authorName, Date createdAt) {
        this.id = id;
        this.content = content;
        this.authorId = authorId;
        this.authorName = authorName;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
