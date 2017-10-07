package pl.zed.dice.user.profile.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 2, max = 128)
    private String firstname;

    @Size(min = 2, max = 128)
    private String lastname;

    private Gender gender;

    @DateTimeFormat
    private Date birthdayDate;

    private String phoneNumber;

    private String city;

    private String origImage;

    private String cropImage;

    private String programmingLanguages;


    public UserProfile(){

    }

    public UserProfile(String firstname, String lastname, Gender gender, Date birthdayDate, String phoneNumber, String city, String origImage, String cropImage, String programmingLanguages) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.origImage = origImage;
        this.cropImage = cropImage;
        this.programmingLanguages = programmingLanguages;
    }

    public UserProfile(String firstname, String lastname, Gender gender, Date birthdayDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
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

    public String getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(String programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }
}
