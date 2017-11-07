package pl.zed.dice.user.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zed.dice.user.profile.domain.FriendEntity;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.List;

@Repository
public interface FriendShipRepository extends JpaRepository<FriendEntity, Long> {

    @Query("select f from FriendEntity f where (f.requestor = ?1 AND f.recipient = ?2) " +
            "OR (f.requestor = ?2 AND f.recipient = ?1)")
    FriendEntity getFriendShip(UserProfile requestor, UserProfile recipient);

    @Query("select f from FriendEntity f where (f.requestor = ?1 OR f.recipient = ?1) AND f.status = 'ACCEPTED'")
    List<FriendEntity> getFriends(UserProfile recipient);

    @Query("select f from FriendEntity f where f.recipient = ?1 AND f.status = 'REJECTED'")
    List<FriendEntity> getFollowers(UserProfile recipient);

    @Query("select f from FriendEntity f where f.recipient = ?1 AND f.status = 'SENT'")
    List<FriendEntity> getFriendRequests(UserProfile userProfile);
}
