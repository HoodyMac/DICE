package pl.zed.dice.user.profile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.zed.dice.security.JwtTokenUtil;
import pl.zed.dice.security.JwtUser;
import pl.zed.dice.security.service.UserService;
import pl.zed.dice.user.profile.model.UserDTO;
import pl.zed.dice.user.profile.model.UserProfileDTO;
import pl.zed.dice.user.profile.model.UserProfileSearchDTO;
import pl.zed.dice.user.profile.model.UserProfileSearchResultDTO;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserProfileController {

    @Value("Authorization")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String email = jwtTokenUtil.getEmailFromToken(token);
        return (JwtUser) userDetailsService.loadUserByUsername(email);
    }

    @PostMapping("/user/save")
    @ResponseStatus(HttpStatus.OK)
    public void save(@RequestBody UserDTO userDTO) throws ParseException {
        userService.save(userDTO);
    }

    @GetMapping("/profile/{id}")
    public UserProfileDTO getProfile(@PathVariable Long id){
        return userService.getUserProfile(id);
    }

    @PutMapping("/profile")
    public UserProfileDTO editProfile(@RequestBody UserProfileDTO userProfileDTO) throws ParseException {
        return userService.editUserProfile(userProfileDTO);
    }

    @PutMapping("/account")
    public void editUserAccountPassword(@RequestBody UserDTO userDTO){
        if(userDTO.getPassword() == null){
            userService.editUserAccountEmail(userDTO);
        }else
            userService.editUserPassword(userDTO);
    }

    @GetMapping("/me")
    public UserProfileDTO getMySelf(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getMyProfile(auth.getName());
    }

    @PostMapping("/search")
    public List<UserProfileSearchResultDTO> search(@RequestBody UserProfileSearchDTO userProfileSearchDTO){
        return userService.search(userProfileSearchDTO);
    }
}
