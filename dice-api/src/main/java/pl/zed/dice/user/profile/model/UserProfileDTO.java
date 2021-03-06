package pl.zed.dice.user.profile.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import pl.zed.dice.post.model.PostDTO;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDTO {

    private Long userId;
    private String firstname;
    private String lastname;
    private String gender;
    private String originalImgSrc;
    private String cropImgSrc;
    private String birthdayDate;
    private String phoneNumber;
    private String city;
    private String programmingLanguages;
    private String work;
    private String education;
    private Boolean isOnline;
    private String email;
    private Integer friendsCount;
    private String friendShipStatus;
    private List<PostDTO> posts;

    public UserProfileDTO(){}

    public UserProfileDTO(Long userId, String firstname, String lastname, String gender, String originalImgSrc, String cropImgSrc, String birthdayDate, String phoneNumber, String city, String programmingLanguages, String work, String education) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.originalImgSrc = originalImgSrc;
        this.cropImgSrc = cropImgSrc;
        this.birthdayDate = birthdayDate;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.programmingLanguages = programmingLanguages;
        this.work = work;
        this.education = education;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOriginalImgSrc() {
        return originalImgSrc;
    }

    public void setOriginalImgSrc(String originalImgSrc) {
        this.originalImgSrc = originalImgSrc;
    }

    public String getCropImgSrc() {
        return cropImgSrc;
    }

    public void setCropImgSrc(String cropImgSrc) {
        this.cropImgSrc = cropImgSrc;
    }

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(String programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    public String getFriendShipStatus() {
        return friendShipStatus;
    }

    public void setFriendShipStatus(String friendShipStatus) {
        this.friendShipStatus = friendShipStatus;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
}
