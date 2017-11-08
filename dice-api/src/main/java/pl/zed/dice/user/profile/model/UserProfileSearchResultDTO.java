package pl.zed.dice.user.profile.model;

public class UserProfileSearchResultDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private String work;
    private int friendsCount;
    private String cropImage;
    private String friendShipStatus;

    public UserProfileSearchResultDTO(){}

    public UserProfileSearchResultDTO(Long id, String firstName, String lastName, String city, String work, String cropImage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.work = work;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFriendShipStatus() {
        return friendShipStatus;
    }

    public void setFriendShipStatus(String friendShipStatus) {
        this.friendShipStatus = friendShipStatus;
    }
}
