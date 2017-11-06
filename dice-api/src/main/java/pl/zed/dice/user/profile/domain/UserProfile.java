package pl.zed.dice.user.profile.domain;

import pl.zed.dice.user.profile.model.UserProfileDTO;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@NamedQuery(name = "UserProfile.findByOrigImageOrCropImage", query = "SELECT count(u.id) FROM UserProfile u WHERE LOWER(u.cropImage) = LOWER(?1) or LOWER(u.origImage) = LOWER(?1)")
public class UserProfile {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 2, max = 128)
    private String firstname;

    @Size(min = 2, max = 128)
    private String lastname;

    //@Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    private Date birthdayDate;

    private String phoneNumber;

    private String city;

    private String origImage;

    private String cropImage;

    private String programmingLanguages;

    private String work;

    private String education;

    private Boolean isOnline;

    @ManyToMany
    List<FriendEntity> friends;

    public UserProfile(){

    }

    public UserProfile(String firstname, String lastname, Gender gender, Date birthdayDate, String phoneNumber, String city, String origImage, String cropImage, String programmingLanguages, String work, String education) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdayDate = birthdayDate;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.origImage = origImage;
        this.cropImage = cropImage;
        this.programmingLanguages = programmingLanguages;
        this.work = work;
        this.education = education;
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

    public void edit(UserProfileDTO userProfileDTO) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(userProfileDTO.getBirthdayDate());
        this.firstname = userProfileDTO.getFirstname();
        this.lastname = userProfileDTO.getLastname();
        this.gender = userProfileDTO.getGender().equalsIgnoreCase("female") ? Gender.FEMALE : Gender.MALE;
        this.birthdayDate = date;
        this.city = userProfileDTO.getCity();
        this.education = userProfileDTO.getEducation();
        this.work = userProfileDTO.getWork();
        this.programmingLanguages = userProfileDTO.getProgrammingLanguages();
        this.phoneNumber = userProfileDTO.getPhoneNumber();
    }

    public List<FriendEntity> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendEntity> friends) {
        this.friends = friends;
    }

    public String getFullname() {
        return firstname + ' ' + lastname;
    }
}
