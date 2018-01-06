package pl.zed.dice.forum.model;

import pl.zed.dice.like.model.LikeDTO;

import java.util.Date;
import java.util.List;

public class ForumQuestionDetailsDTO extends ForumQuestionViewDTO {

    private String description;
    private List<ForumReplyViewDTO> replies;
    private List<LikeDTO> likes;

    public ForumQuestionDetailsDTO(Long id, String title, String authorName, List<TagViewDTO> tags, Date createdAt, String description, List<ForumReplyViewDTO> replies) {
        super(id, title, authorName, tags, createdAt);
        this.description = description;
        this.replies = replies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ForumReplyViewDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<ForumReplyViewDTO> replies) {
        this.replies = replies;
    }

    @Override
    public List<LikeDTO> getLikes() {
        return likes;
    }

    @Override
    public void setLikes(List<LikeDTO> likes) {
        this.likes = likes;
    }
}
