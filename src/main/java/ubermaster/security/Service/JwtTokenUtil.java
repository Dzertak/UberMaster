package ubermaster.security.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ubermaster.entity.security.JwtUser;

import java.io.Serializable;
import java.util.Date;

import static ubermaster.security.SecurityConstants.EXPIRATION_TIME;
import static ubermaster.security.SecurityConstants.SECRET;

@Component
public class JwtTokenUtil implements Serializable {
    @Autowired
    private TimeProvider timeProvider;
    private String secret = SECRET;
    private Long expiration = EXPIRATION_TIME;

    private Claims parseToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return body;
    }

    public String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return parseToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(timeProvider.now());
    }


    public String generateToken(JwtUser jwtUser) {
        final Date createdDate = timeProvider.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .setId(String.valueOf(jwtUser.getId()));
        claims.put("role", jwtUser.getAuthorities().toString());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        final Date createdDate = timeProvider.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = parseToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
        );
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
