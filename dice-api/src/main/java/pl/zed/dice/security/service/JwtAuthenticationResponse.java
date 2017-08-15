package pl.zed.dice.security.service;

public class JwtAuthenticationResponse {

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
