package pl.zed.dice.exception.userProfile;

import org.omg.SendingContext.RunTime;

public class FriendShipDoesNotExist extends RuntimeException {
    public FriendShipDoesNotExist() {
        super("Friendship does not exist");
    }
}
