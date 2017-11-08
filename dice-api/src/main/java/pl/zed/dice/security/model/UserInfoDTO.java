package pl.zed.dice.security.model;

import pl.zed.dice.constant.Gender;

public class UserInfoDTO {

    private String email;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Long userAccountId;
    private Long userProfileId;

    public UserInfoDTO(String email, String firstName, String lastName, Gender gender, Long userAccountId, Long userProfileId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.userAccountId = userAccountId;
        this.userProfileId = userProfileId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }
}
