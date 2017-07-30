package pl.zed.dice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    @Value("secret")
    private String secret;

    @Value("604800")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    public Date getCreatedDateFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        return new Date((Long) claims.get("created_at"));
    }

    public Date getExpirationDateFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordRest) {
        return lastPasswordRest != null && created.before(lastPasswordRest);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("sub", userDetails.getUsername());
        claims.put("created_at", new Date());

        return doGenerateToken(claims);
    }

    private String doGenerateToken(Map<String, Object> claims) {
        final Date createdDate = (Date) claims.get("created_at");
        final Date expirationDate = new Date(createdDate.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        claims.put("created_at", new Date());
        return doGenerateToken(claims);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;

        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);

        return username.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());
    }
}
