package pl.zed.dice.asm;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.zed.dice.security.domain.Gender;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.security.domain.UserProfile;
import pl.zed.dice.security.model.UserDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserAsm {

    public UserAccount convertDtoToUserAccount(UserDTO userDTO){
        return new UserAccount(userDTO.getEmail(), new BCryptPasswordEncoder().encode(userDTO.getPassword()), true, new Date());
    }

    public UserProfile convertDtoToUserProfile(UserDTO userDTO) throws ParseException {
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy");
        Date date = format.parse(userDTO.getBirthdayDate());

        return new UserProfile(userDTO.getFirstname(), userDTO.getLastname(),
                userDTO.getGender().equalsIgnoreCase("Female")? Gender.FEMALE : Gender.MALE, date);
    }

    public UserDTO getUserProfileDto(UserProfile userProfile){
        return new UserDTO(userProfile.getFirstname(), userProfile.getLastname(),
                userProfile.getGender().toString(), userProfile.getOrigImage(), userProfile.getCropImage(),
                userProfile.getBirthdayDate().toString(), userProfile.getPhoneNumber(),
                userProfile.getCity(), userProfile.getProgrammingLanguages());
    }
}
