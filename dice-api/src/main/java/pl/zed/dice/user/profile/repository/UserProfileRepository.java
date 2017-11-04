package pl.zed.dice.user.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zed.dice.user.profile.domain.Gender;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.Date;
import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
    Integer findByOrigImageOrCropImage(String image);
    String searchQuery = "select pr from UserProfile pr where CONCAT(pr.firstname, ' ', pr.lastname) like ?1 " +
            "OR (pr.birthdayDate between ?2 AND ?3 OR pr.gender = ?4 " +
            "OR pr.isOnline = ?5 AND pr.city like ?6)";

    @Query(searchQuery)
    List<UserProfile> search(String fullName, Date dateFrom, Date dateTo, Gender gender,
                             Boolean isOnline, String city);
}
