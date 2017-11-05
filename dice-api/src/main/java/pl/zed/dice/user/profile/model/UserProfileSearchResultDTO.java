package pl.zed.dice.user.profile.model;

public class UserProfileSearchResultDTO {

    private String firstName;
    private String lastName;
    private String city;
    private String work;
    private int friendsCount;
    private String cropImage;

    public UserProfileSearchResultDTO(){}

    public UserProfileSearchResultDTO(String firstName, String lastName, String city, String work, int friendsCount, String cropImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.work = work;
        this.friendsCount = friendsCount;
        this.cropImage = cropImage;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
    }
}
