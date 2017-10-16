package pl.zed.dice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zed.dice.exception.user.UserAlreadyExistsException;
import pl.zed.dice.exception.user.UserNotFoundException;
import pl.zed.dice.security.asm.UserAccountAsm;
import pl.zed.dice.security.model.UserInfoDTO;
import pl.zed.dice.user.profile.asm.UserAsm;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.model.UserDTO;
import pl.zed.dice.user.profile.model.UserProfileDTO;
import pl.zed.dice.user.profile.repository.UserProfileRepository;
import pl.zed.dice.security.repository.UserAccountRepository;

import java.text.ParseException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserAsm userAsm;

    @Autowired
    private UserAccountAsm userAccountAsm;

    public void save(UserDTO userDTO) throws ParseException {
        if(userAccountRepository.findByEmail(userDTO.getEmail()) == null) {
            UserProfile userProfile = userAsm.makeUserProfile(userDTO);
            userProfileRepository.save(userProfile);

            UserAccount userAccount = userAsm.makeUserAccount(userDTO);
            userAccount.setProfile(userProfile);
            userAccountRepository.save(userAccount);
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

    public UserProfileDTO editUserProfile(Long id, UserProfileDTO userProfileDTO) throws ParseException {
        UserProfile userProfile = userProfileRepository.getOne(id);
        userProfile.edit(userProfileDTO);
        userProfileRepository.save(userProfile);
        return userAsm.makeUserProfileDTO(userProfile);
    }

    public UserInfoDTO getUserInfo(String email) {
        UserAccount userAccount = userAccountRepository.findByEmail(email);
        return userAccountAsm.convertAccountToUserInfoDTO(userAccount);
    }

    public UserProfileDTO getMyProfile(String email){
        UserAccount userAccount = userAccountRepository.findByEmail(email);
        return userAsm.makeUserProfileDTO(userAccount.getProfile());
    }
}
