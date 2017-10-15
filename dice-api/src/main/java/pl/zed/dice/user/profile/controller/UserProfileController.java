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

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

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

    @GetMapping("/user/{id}")
    public UserProfileDTO getProfile(@PathVariable Long id){
        return userService.getUserProfile(id);
    }

    @PutMapping("/profile/{id}")
    public UserProfileDTO editProfile(@PathVariable Long id, @RequestBody UserProfileDTO userProfileDTO) throws ParseException {
        return userService.editUserProfile(id, userProfileDTO);
    }

    @GetMapping("/me")
    public UserProfileDTO getMySelf(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getMyProfile(auth.getName());
    }
}
