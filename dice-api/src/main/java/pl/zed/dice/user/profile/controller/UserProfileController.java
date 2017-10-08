package pl.zed.dice.user.profile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.zed.dice.security.JwtTokenUtil;
import pl.zed.dice.security.JwtUser;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserProfileController {

    @Value("Authorization")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/user")
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return (JwtUser) userDetailsService.loadUserByUsername(username);
    }
<<<<<<< HEAD:dice-api/src/main/java/pl/zed/dice/security/controller/UserRestController.java

    @PostMapping("/user/save")
    public void save(@RequestBody UserDTO userDTO) throws ParseException {
        userService.save(userDTO);
    }

    @GetMapping("/user/{id}")
    public UserDTO getProfile(@PathVariable Long id){
        return userService.getUserProfile(id);
    }

    @PutMapping("/profile/{id}")
    public UserDTO editProfile(@PathVariable Long id, @RequestBody UserDTO userDTO) throws ParseException {
        return userService.editUserProfile(id, userDTO);
    }
=======
>>>>>>> ddb5e390f9f51fe29a2cb92b71189dc600afe52e:dice-api/src/main/java/pl/zed/dice/user/profile/controller/UserProfileController.java
}
