package pl.zed.dice.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.zed.dice.security.JwtTokenUtil;
import pl.zed.dice.security.JwtUser;
import pl.zed.dice.security.model.UserDTO;
import pl.zed.dice.security.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController
@RequestMapping("/api")
public class UserRestController {

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
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return (JwtUser) userDetailsService.loadUserByUsername(username);
    }

    @PostMapping("/save")
    public void save(@RequestBody UserDTO userDTO) throws ParseException {
        userService.save(userDTO);
    }

}
