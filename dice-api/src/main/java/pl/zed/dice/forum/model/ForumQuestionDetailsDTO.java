package pl.zed.dice.forum.model;

import java.util.Date;
import java.util.List;

public class ForumQuestionDetailsDTO extends ForumQuestionViewDTO {

    private String description;

    public ForumQuestionDetailsDTO(Long id, String title, String authorName, List<TagViewDTO> tags, Date createdAt, String description) {
        super(id, title, authorName, tags, createdAt);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
