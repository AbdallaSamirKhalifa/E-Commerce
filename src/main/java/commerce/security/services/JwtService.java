package commerce.security.services;

import commerce.security.services.utils.KeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    @Value("${app.security.jwt.access-token-expiration}")
    private long accessTokenExpiration;

    public JwtService() throws Exception {
        this.privateKey = KeyUtils.loadPrivateKey("keys/private-key.pem");
        this.publicKey = KeyUtils.loadPublicKey("keys/public-key.pem");
    }

    public String generateAccessToken(UserDetails user) {
        String username = user.getUsername();
        Map<String, Object> claims = Map.of("ROLES", user.getAuthorities());
        return buildAccessToken(username, claims);
    }

    private String buildAccessToken(String username, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant expiration = now.plus(this.accessTokenExpiration, ChronoUnit.MILLIS);
        return Jwts.builder()
                .signWith(this.privateKey)
                .expiration(Date.from(expiration))
                .subject(username)
                .claims(claims)
                .issuedAt(Date.from(now))
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        Claims claims = this.getClaims(token);
        String username = claims.getSubject();
        Date expiration = claims.getExpiration();

        return username != null && username.equals(user.getUsername()) && expiration.after(Date.from(Instant.now()));
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(Date.from(Instant.now()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(this.publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
