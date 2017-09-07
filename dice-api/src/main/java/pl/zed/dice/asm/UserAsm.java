package pl.zed.dice.asm;

import org.springframework.stereotype.Component;
import pl.zed.dice.security.domain.Gender;
import pl.zed.dice.security.domain.User;
import pl.zed.dice.security.domain.UserProfile;
import pl.zed.dice.security.model.UserDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserAsm {

    public User convertDtoToUser(UserDTO userDTO){
        return new User(userDTO.getUsername(), userDTO.getPassword());
    }

    public UserProfile convertDtoToUserProfile(UserDTO userDTO) throws ParseException {
        DateFormat format = new SimpleDateFormat("MMMM d, yyyy");
        Date date = format.parse(userDTO.getDate());

        return new UserProfile(userDTO.getFirstName(), userDTO.getLastName(),
                userDTO.getGender().equalsIgnoreCase("Female")? Gender.FEMALE : Gender.MALE, date);
    }

}
