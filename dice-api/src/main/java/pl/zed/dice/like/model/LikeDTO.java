package pl.zed.dice.like.model;

import pl.zed.dice.user.profile.model.UserProfileDTO;

public class LikeDTO {

    private Long id;
    private UserProfileDTO user;

    public LikeDTO(){}

    public LikeDTO(Long id, UserProfileDTO user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfileDTO getUser() {
        return user;
    }

    public void setUser(UserProfileDTO user) {
        this.user = user;
    }
}
