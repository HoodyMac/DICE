package pl.zed.dice.post.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

    private Long id;
    private String author;
    private String content;
    private String created_date;
    private String cropImg;

    public PostDTO(){}

    public PostDTO(Long id, String author, String content, String created_date, String cropImg) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.created_date = created_date;
        this.cropImg = cropImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getCropImg() {
        return cropImg;
    }

    public void setCropImg(String cropImg) {
        this.cropImg = cropImg;
    }
}
