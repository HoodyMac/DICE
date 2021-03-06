package pl.zed.dice.user.profile.asm;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.constant.Gender;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.model.FriendDTO;
import pl.zed.dice.user.profile.model.UserDTO;
import pl.zed.dice.user.profile.model.UserProfileDTO;
import pl.zed.dice.user.profile.model.UserProfileSearchResultDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserAsm {

    public UserAccount makeUserAccount(UserDTO userDTO){
        return new UserAccount(
                userDTO.getEmail(),
                new BCryptPasswordEncoder().encode(userDTO.getPassword()),
                true, new Date());
    }

    public UserProfile makeUserProfile(UserDTO userDTO) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(userDTO.getBirthdayDate());

        return new UserProfile(userDTO.getFirstname(), userDTO.getLastname(),
                userDTO.getGender().equalsIgnoreCase("Female")? Gender.FEMALE : Gender.MALE, date);
    }

    public UserProfileDTO makeUserProfileDTO(UserProfile userProfile){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String birthDayDate = format.format(userProfile.getBirthdayDate());
        return new UserProfileDTO(userProfile.getId(), userProfile.getFirstname(), userProfile.getLastname(),
                userProfile.getGender().toString(), userProfile.getOrigImage(), userProfile.getCropImage(),
                birthDayDate, userProfile.getPhoneNumber(),
                userProfile.getCity(), userProfile.getProgrammingLanguages(), userProfile.getWork(),
                userProfile.getEducation());
    }

    public UserProfileSearchResultDTO makeUserProfileSearchResultDTO(UserProfile userProfile){
        return new UserProfileSearchResultDTO(userProfile.getId(), userProfile.getFirstname(), userProfile.getLastname(),
                userProfile.getCity(), userProfile.getWork(), userProfile.getCropImage());
    }

    public FriendDTO makeFriendDTO(UserProfile userProfile){
        return new FriendDTO(userProfile.getId(), userProfile.getFirstname(), userProfile.getLastname(),
                userProfile.getCropImage(), userProfile.getFriends().size());
    }
}
