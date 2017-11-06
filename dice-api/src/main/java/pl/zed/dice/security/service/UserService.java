package pl.zed.dice.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zed.dice.exception.user.UserAlreadyExistsException;
import pl.zed.dice.exception.user.UserNotFoundException;
import pl.zed.dice.exception.user.WrongOldPasswordException;
import pl.zed.dice.exception.userProfile.FriendShipDoesNotExist;
import pl.zed.dice.security.asm.UserAccountAsm;
import pl.zed.dice.security.model.UserInfoDTO;
import pl.zed.dice.security.repository.UserAccountRepository;
import pl.zed.dice.user.profile.asm.UserAsm;
import pl.zed.dice.security.domain.UserAccount;
import pl.zed.dice.user.profile.domain.FriendEntity;
import pl.zed.dice.user.profile.domain.FriendRequestStatus;
import pl.zed.dice.user.profile.domain.Gender;
import pl.zed.dice.user.profile.domain.UserProfile;
import pl.zed.dice.user.profile.model.*;
import pl.zed.dice.user.profile.repository.FriendShipRepository;
import pl.zed.dice.user.profile.repository.UserProfileRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @Autowired
    private FriendShipRepository friendShipRepository;

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

        Integer ageFrom = null;

        if(userProfileSearchDTO.getAgeFrom() != null) {
            ageFrom = LocalDate.now().getYear()-userProfileSearchDTO.getAgeFrom();
        }

        Integer ageTo = null;

        if(userProfileSearchDTO.getAgeTo() != null) {
            ageTo = LocalDate.now().getYear()-userProfileSearchDTO.getAgeTo();
        }

        Gender gender = null;

        if(userProfileSearchDTO.getGender() != null) {
            String genderDto = userProfileSearchDTO.getGender();
                    gender = genderDto.equalsIgnoreCase("female") ?
                    Gender.FEMALE : genderDto.equalsIgnoreCase("male") ? Gender.MALE : null;
        }

        if(ageTo == null && ageFrom != null){
            ageTo = 1000;
        }

        if(ageFrom == null && ageTo != null){
            ageFrom = LocalDate.now().getYear();
        }

        String fullName = userProfileSearchDTO.getFullName();
        User user = new User(fullName, fullName);

        if(fullName != null && !fullName.equals("") && fullName.contains(" ")) {
            user = new User(userProfileSearchDTO.getFullName().substring(0, fullName.indexOf(" ")),
                    userProfileSearchDTO.getFullName().substring(fullName.indexOf(" ")+1));
        }

        userProfileRepository.search(user.getFirstname(), ageFrom, ageTo, gender,
                userProfileSearchDTO.getOnline(), userProfileSearchDTO.getCity(),
                userProfileSearchDTO.getProgrammingLanguages(), user.getSurname())
        .forEach(p -> result.add(userAsm.makeUserProfileSearchResultDTO(p)));

        return result;
    }

    private class User{
        private String firstname;
        private String surname;

        public User(String firstname, String surname) {
            this.firstname = firstname;
            this.surname = surname;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getSurname() {
            return surname;
        }
    }

    public void sendFriendRequest(Long id){
        String requestorName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserProfile requestor = userRepository.findByEmail(requestorName).getProfile();
        UserProfile recipient = userProfileRepository.getOne(id);

        if(friendShipRepository.getFriendShip(requestor, recipient) == null) {
            friendShipRepository.save(new FriendEntity(requestor, recipient, FriendRequestStatus.SENT));
        }
    }

    public void acceptFriendRequest(Long id){
        String recipientName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserProfile recipient = userRepository.findByEmail(recipientName).getProfile();
        UserProfile requestor = userProfileRepository.getOne(id);

        FriendEntity friendEntity = friendShipRepository.getFriendShip(requestor, recipient);

        if(friendEntity != null) {
            friendEntity.setStatus(FriendRequestStatus.ACCEPTED);
        }else
            throw new FriendShipDoesNotExist();

        friendShipRepository.save(friendEntity);
    }

    public void rejectFriendRequest(Long id){
        String recipientName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserProfile recipient = userRepository.findByEmail(recipientName).getProfile();
        UserProfile requestor = userProfileRepository.getOne(id);

        FriendEntity friendEntity = friendShipRepository.getFriendShip(requestor, recipient);

        if(friendEntity != null) {
            friendEntity.setStatus(FriendRequestStatus.REJECTED);
        }else
            throw new FriendShipDoesNotExist();

        friendShipRepository.save(friendEntity);
    }

    public void removeFriend(Long id){
        String recipientName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserProfile recipient = userRepository.findByEmail(recipientName).getProfile();
        UserProfile requestor = userProfileRepository.getOne(id);

        FriendEntity friendEntity = friendShipRepository.getFriendShip(requestor, recipient);

        if(friendEntity != null) {
            friendShipRepository.delete(friendEntity);
        }else
            throw new FriendShipDoesNotExist();
    }

    public List<FriendDTO> getMyFriends(){
        String recipientName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserProfile recipient = userRepository.findByEmail(recipientName).getProfile();
        List<FriendDTO> friendDTOS = new ArrayList<>();

        friendShipRepository.getFriends(recipient).forEach(f ->{
            if(f.getRecipient() == recipient) {
                friendDTOS.add(userAsm.makeFriendDTO(f.getRequestor()));
            }else
                friendDTOS.add(userAsm.makeFriendDTO(f.getRecipient()));
        });

        return friendDTOS;
    }

    public List<FriendDTO> getFriends(Long id){
        UserProfile recipient = userProfileRepository.getOne(id);
        List<FriendDTO> friendDTOS = new ArrayList<>();

        friendShipRepository.getFriends(recipient).forEach(f ->
                friendDTOS.add(userAsm.makeFriendDTO(f.getRequestor()))
        );

        return friendDTOS;
    }
}
