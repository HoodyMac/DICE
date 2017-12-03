package pl.zed.dice.forum.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @ManyToMany(mappedBy="tags")
    @JsonIgnore
    private List<ForumQuestion> questions;

    public Tag() {
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

    public List<ForumQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<ForumQuestion> questions) {
        this.questions = questions;
    }
}
