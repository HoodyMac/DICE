package pl.zed.dice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.security.asm.UserAccountAsm;
import pl.zed.dice.security.model.UserInfoDTO;
import pl.zed.dice.user.profile.asm.UserAsm;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.model.UserDTO;
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

    @Autowired
    private UserAccountAsm userAccountAsm;

    public void save(UserDTO userDTO) throws ParseException {
        UserAccount userAccount = userAsm.convertDtoToUserAccount(userDTO);
        UserProfile userProfile = userAsm.convertDtoToUserProfile(userDTO);

        userProfileRepository.save(userProfile);
        userAccount.setProfile(userProfile);
        userRepository.save(userAccount);
    }

    public UserDTO getUserProfile(Long id){
        UserProfile userProfile = userProfileRepository.findById(id).get();
        return userAsm.getUserProfileDto(userProfile);
    }

    public UserDTO editUserProfile(Long id, UserDTO userDTO) throws ParseException {
        UserProfile userProfile = userProfileRepository.findById(id).get();
        userProfile.edit(userDTO);
        userProfileRepository.save(userProfile);
        return userAsm.getUserProfileDto(userProfile);
    }

    public UserInfoDTO getUserInfo(String username) {
        UserAccount userAccount = userRepository.findByUsername(username);
        return userAccountAsm.convertAccountToUserInfoDTO(userAccount);
    }
}
