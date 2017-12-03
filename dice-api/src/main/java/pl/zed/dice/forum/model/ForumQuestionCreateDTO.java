package pl.zed.dice.forum.model;

import java.util.List;

public class ForumQuestionCreateDTO {

    private String title;
    private String description;
    private List<Long> tags;

    public ForumQuestionCreateDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }
}
