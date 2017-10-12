package pl.zed.dice.security.service;

import pl.zed.dice.security.model.UserInfoDTO;

public class JwtAuthenticationResponse {

    private final String token;
    private final UserInfoDTO userInfo;

    public JwtAuthenticationResponse(String token, UserInfoDTO userInfo) {
        this.token = token;
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public UserInfoDTO getUserInfo() {
        return userInfo;
    }
}
