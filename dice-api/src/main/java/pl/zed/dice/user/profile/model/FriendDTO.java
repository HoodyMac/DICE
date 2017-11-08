package pl.zed.dice.user.profile.model;

public class FriendDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private int friendsCount;
    private String cropImage;

    public FriendDTO(){}

    public FriendDTO(Long id, String firstName, String lastName, String cropImage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
