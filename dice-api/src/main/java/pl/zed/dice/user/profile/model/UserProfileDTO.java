package pl.zed.dice.user.profile.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDTO {

    private Long userId;
    private String firstname;
    private String lastname;
    private String gender;
    private String origImage;
    private String cropImage;
    private String birthdayDate;
    private String phoneNumber;
    private String city;
    private String programmingLanguages;
    private String work;
    private String education;
    private Boolean isOnline;

    public UserProfileDTO(){}

    public UserProfileDTO(String firstname, String lastname, String gender, String origImage,
                          String cropImage, String birthdayDate, String phoneNumber, String city, String programmingLanguages){
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.origImage = origImage;
        this.cropImage = cropImage;
        this.birthdayDate = birthdayDate;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.programmingLanguages = programmingLanguages;
    }

    //for editing
    public UserProfileDTO(String firstname, String lastname, String gender, String origImage, String cropImage, String birthdayDate, String phoneNumber, String city, String programmingLanguages, String work, String education) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.origImage = origImage;
        this.cropImage = cropImage;
        this.birthdayDate = birthdayDate;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.programmingLanguages = programmingLanguages;
        this.work = work;
        this.education = education;
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

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrigImage() {
        return origImage;
    }

    public void setOrigImage(String origImage) {
        this.origImage = origImage;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
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

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
