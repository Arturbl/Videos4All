package com.Videos4All.Videos4All.util;

import com.Videos4All.Videos4All.model.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtTokenUtil {

    private static final long TOKEN_EXPIRATION_LIMIT = 864000 * 1000; // 1 day
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value(value = "${app.jwt.secret}")
    private String SECRET_KEY;

//    public static void main(String[] args) {
//        Jwt parse = Jwts.parser().setSigningKey("secret").parse("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMjA1YmUyOS00NzIzLTRiNWItOWM4YS1kMWM5YjM4NDBkZTQsYWRtaW4iLCJpc3MiOiJTYXQgTWF5IDI3IDEzOjM5OjM1IFdFU1QgMjAyMyIsImV4cCI6MTY4NjA1NTE3NX0.syedeVF4u0l0_ip-uTaPt3ZSgl33vJR7aW9AJesEV1MoY2f44aVOSEGPFacofgOxW1L46MWHypApI0jbH1nBtg");
//        System.out.println(parse.getBody());
//        System.out.println(parse.getHeader());
//    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
                .claim("clientId", user.getAccessToken())
                .setIssuer(String.valueOf(new Date()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_LIMIT))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }

        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public User getUserDetails(String token) {
        User userDetails = new User();
        String[] jwtSubject = getSubject(token).split(",");
        userDetails.setId(jwtSubject[0]);
        userDetails.setUsername(jwtSubject[1]);
        return userDetails;
    }


}
