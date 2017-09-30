package pl.zed.dice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.asm.UserAsm;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.security.domain.UserProfile;
import pl.zed.dice.security.model.UserDTO;
import pl.zed.dice.security.repository.UserProfileRepository;
import pl.zed.dice.security.repository.UserRepository;

import java.text.ParseException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserAsm userAsm;

    public void save(UserDTO userDTO) throws ParseException {
        UserAccount userAccount = userAsm.convertDtoToUserAccount(userDTO);
        UserProfile userProfile = userAsm.convertDtoToUserProfile(userDTO);

        userProfileRepository.save(userProfile);
        userAccount.setProfile(userProfile);
        userRepository.save(userAccount);
    }
}