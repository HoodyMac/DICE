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
    String searchQuery = "select pr from UserProfile pr where (CONCAT(pr.firstname, ' ', pr.lastname) like ?1 " +
            "OR ?1 is null OR ?2 is null) " +
            "AND (YEAR(pr.birthdayDate) <= ?2 AND YEAR(pr.birthdayDate) >= ?3 OR ?2 is null OR ?3 is null) " +
            "AND (pr.gender = ?4 OR ?4 is null) " +
            "AND (pr.isOnline = ?5 OR ?5 is null) " +
            "AND (pr.city like ?6 OR ?6 is null) " +
            "AND (?7 in pr.programmingLanguages OR ?7 is null)";

    @Query(searchQuery)
    List<UserProfile> search(String fullName, Integer ageFrom, Integer ageTo, Gender gender,
                             Boolean isOnline, String city, String programmingLang);
}
