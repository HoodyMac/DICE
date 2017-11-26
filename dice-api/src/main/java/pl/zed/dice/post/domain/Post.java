package pl.zed.dice.post.domain;

import org.apache.tomcat.jni.Local;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import pl.zed.dice.post.model.PostDTO;
import pl.zed.dice.user.profile.domain.UserProfile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    private UserProfile author;

    @NotNull
    @NotEmpty
    private String content;

    @NotNull
    @DateTimeFormat
    private Date created_date;

    public Post(){}

    public Post(UserProfile author, String content, Date created_date) {
        this.author = author;
        this.content = content;
        this.created_date = created_date;
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

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public UserProfile getAuthor() {
        return author;
    }

    public void setAuthor(UserProfile author) {
        this.author = author;
    }

    public void edit(PostDTO postDTO){
        this.content = postDTO.getContent();
    }
}
