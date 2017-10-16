package pl.zed.dice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zed.dice.exception.user.UserAlreadyExistsException;
import pl.zed.dice.exception.user.UserNotFoundException;
import pl.zed.dice.exception.user.WrongOldPasswordException;
import pl.zed.dice.security.asm.UserAccountAsm;
import pl.zed.dice.security.model.UserInfoDTO;
import pl.zed.dice.user.profile.asm.UserAsm;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.model.UserDTO;
import pl.zed.dice.user.profile.model.UserProfileDTO;
import pl.zed.dice.security.repository.UserProfileRepository;
import pl.zed.dice.security.repository.UserRepository;

import java.text.ParseException;
import java.util.Optional;

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
        if(userRepository.findByEmail(userDTO.getEmail()) == null) {
            UserProfile userProfile = userAsm.makeUserProfile(userDTO);
            userProfileRepository.save(userProfile);

            UserAccount userAccount = userAsm.makeUserAccount(userDTO);
            userAccount.setProfile(userProfile);
            userRepository.save(userAccount);
        }else
            throw new UserAlreadyExistsException(userDTO.getEmail());
    }

    public UserProfileDTO getUserProfile(Long id){
        Optional<UserProfile> userProfile = userProfileRepository.findById(id);
        if(userProfile.isPresent()){
            return userAsm.makeUserProfileDTO(userProfile.get());
        }else
            throw new UserNotFoundException(id);
    }

    public UserProfileDTO editUserProfile(UserProfileDTO userProfileDTO) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserProfile userProfile = userRepository.findByEmail(auth.getName()).getProfile();
        userProfile.edit(userProfileDTO);
        userProfileRepository.save(userProfile);
        return userAsm.makeUserProfileDTO(userProfile);
    }

    public void editUserAccountEmail(UserDTO userDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userRepository.findByEmail(auth.getName());
        userAccount.editUserEmail(userDTO);
        userRepository.save(userAccount);
    }

    public void editUserPassword(UserDTO userDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAccount userAccount = userRepository.findByEmail(auth.getName());

        if(new BCryptPasswordEncoder().matches(userDTO.getOldPassword(), userAccount.getPassword())){
            userAccount.editUserPassword(userDTO);
            userRepository.save(userAccount);
        }else
            throw new WrongOldPasswordException();
    }

    public UserInfoDTO getUserInfo(String email) {
        UserAccount userAccount = userRepository.findByEmail(email);
        return userAccountAsm.convertAccountToUserInfoDTO(userAccount);
    }

    public UserProfileDTO getMyProfile(String email){
        UserAccount userAccount = userRepository.findByEmail(email);
        UserProfileDTO userProfileDTO = userAsm.makeUserProfileDTO(userAccount.getProfile());
        userProfileDTO.setEducation(userAccount.getEmail());
        return userProfileDTO;
    }
}
