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
import pl.zed.dice.security.repository.UserAccountRepository;
import pl.zed.dice.user.profile.asm.UserAsm;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.user.profile.domain.Gender;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.model.UserDTO;
import pl.zed.dice.user.profile.model.UserProfileDTO;
import pl.zed.dice.user.profile.model.UserProfileSearchDTO;
import pl.zed.dice.user.profile.model.UserProfileSearchResultDTO;
import pl.zed.dice.user.profile.repository.UserProfileRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserAccountRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserAsm userAsm;

    @Autowired
    private UserAccountAsm userAccountAsm;

    @Autowired
    private SecurityContextService securityContextService;

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
        UserProfile userProfile = securityContextService.getCurrentUserProfile();
        userProfile.edit(userProfileDTO);
        userProfileRepository.save(userProfile);
        return userAsm.makeUserProfileDTO(userProfile);
    }

    public UserAccount editUserAccountEmail(UserDTO userDTO){
        UserAccount userAccount = securityContextService.getCurrentUserAccount();
        userAccount.editUserEmail(userDTO);
        userRepository.saveAndFlush(userAccount);
        return userAccount;
    }

    public void editUserPassword(UserDTO userDTO){
        UserAccount userAccount = securityContextService.getCurrentUserAccount();

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
        userProfileDTO.setEmail(userAccount.getEmail());
        return userProfileDTO;
    }

    public List<UserProfileSearchResultDTO> search(UserProfileSearchDTO userProfileSearchDTO){
        List<UserProfileSearchResultDTO> result = new ArrayList<>();

        Date dateFrom = null;
        if(userProfileSearchDTO.getAgeTo() != null) {
            dateFrom = new Date();
            dateFrom.setYear(new Date().getYear() - userProfileSearchDTO.getAgeFrom());
        }
        Date dateTo = null;
        if(userProfileSearchDTO.getAgeFrom() != null) {
            dateTo = new Date();
            dateTo.setYear(new Date().getYear() - userProfileSearchDTO.getAgeTo());
        }

        Gender gender = null;

        if(userProfileSearchDTO.getGender() != null) {
            gender = userProfileSearchDTO.getGender().equalsIgnoreCase("female") ? Gender.FEMALE : Gender.MALE;
        }

        userProfileRepository.search(userProfileSearchDTO.getFullName(), dateFrom, dateTo, gender,
                userProfileSearchDTO.getOnline(), userProfileSearchDTO.getCity())
        .forEach(p -> result.add(userAsm.makeUserProfileSearchResultDTO(p)));

        return result;
    }
}
