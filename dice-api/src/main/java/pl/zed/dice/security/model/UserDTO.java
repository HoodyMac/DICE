package pl.zed.dice.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long userId;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String gender;
    private String origImage;
    private String cropImage;
    private String birthdayDate;
    private String phoneNumber;
    private String city;
    private String programmingLanguages;

    public UserDTO(){}

    //for registration
    public UserDTO(String email, String password, String firstname, String lastname, String gender, String birthdayDate){
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
    }

    public UserDTO(String firstname, String lastname, String gender, String origImage,
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
    public UserDTO(String email, String password, String firstname, String lastname, String gender, String origImage,
                   String cropImage, String birthdayDate, String phoneNumber, String city, String programmingLanguages) {
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
