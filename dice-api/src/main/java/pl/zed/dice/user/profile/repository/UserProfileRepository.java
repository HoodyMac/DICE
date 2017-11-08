package pl.zed.dice.user.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zed.dice.constant.Gender;
import pl.zed.dice.user.profile.domain.UserProfile;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
    Integer findByOrigImageOrCropImage(String image);

    String searchQuery = "select pr from UserProfile pr where ((" +
            "LOWER(pr.firstname) like ?1 AND LOWER(pr.lastname) like ?8) " +
            "OR (LOWER(pr.firstname) like ?8 AND LOWER(pr.lastname) like ?1) " +
            "OR (LOWER(pr.firstname) like ?1 OR LOWER(pr.lastname) like ?8) " +
            "OR (LOWER(pr.firstname) like ?8 OR LOWER(pr.lastname) like ?1) " +
            "OR (?8 is null OR ?8 like '') " +
            "OR (?1 is null OR ?1 like '')) " +
            "AND (YEAR(pr.birthdayDate) <= ?2 AND YEAR(pr.birthdayDate) >= ?3 " +
            "OR ?2 is null OR ?3 is null) " +
            "AND (pr.gender = ?4 OR ?4 is null) " +
            "AND (pr.isOnline = ?5 OR ?5 is null) " +
            "AND (LOWER(pr.city) like ?6 OR ?6 is null OR ?6 like '') " +
            "AND (?7 in pr.programmingLanguages OR pr.programmingLanguages in ?7 " +
            "OR ?7 is null OR ?7 like '')";

    @Query(searchQuery)
    List<UserProfile> search(String firstname, Integer ageFrom, Integer ageTo, Gender gender,
                             Boolean isOnline, String city, String programmingLang, String lastname);
}
