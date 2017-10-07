package pl.zed.dice.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zed.dice.user.profile.domain.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
}
