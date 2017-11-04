package pl.zed.dice.user.profile.model;

public class UserProfileSearchDTO {

    private String fullName;
    private Integer ageFrom;
    private Integer ageTo;
    private String gender;
    private Boolean isOnline;
    private String city;

    public UserProfileSearchDTO(){}

    public UserProfileSearchDTO(String fullName, Integer ageFrom, Integer ageTo,
                                String gender, Boolean isOnline, String city) {
        this.fullName = fullName;
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
        this.gender = gender;
        this.isOnline = isOnline;
        this.city = city;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(Integer ageFrom) {
        this.ageFrom = ageFrom;
    }

    public Integer getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Integer ageTo) {
        this.ageTo = ageTo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
