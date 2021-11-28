package com.iot.server.application.security.jwt;

import com.iot.server.application.config.JwtConfig;
import com.iot.server.application.security.model.SecurityUser;
import com.iot.server.common.enums.AuthorityEnum;
import com.iot.server.common.enums.ReasonEnum;
import com.iot.server.common.exception.IoTException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtFactory {
    public static final String AUTHORITIES = "authorities";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String ENABLED = "enabled";
    private final JwtConfig jwtConfig;

    public String createAccessToken(SecurityUser securityUser) {
        if (!StringUtils.hasText(securityUser.getEmail()))
            throw new IllegalArgumentException("Cannot create access token without email");

        if (securityUser.getAuthority() == null)
            throw new IllegalArgumentException("User doesn't have any privileges");

        Claims claims = Jwts.claims()
                .setSubject(securityUser.getId().toString());
        claims.put(AUTHORITIES,
                securityUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
        claims.put(FIRST_NAME, securityUser.getFirstName());
        claims.put(LAST_NAME, securityUser.getLastName());
        claims.put(EMAIL, securityUser.getEmail());
        claims.put(ENABLED, securityUser.isEnabled());

        ZonedDateTime currentTime = ZonedDateTime.now();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(Date.from(currentTime.toInstant()))
                .setExpiration(Date.from(currentTime.plusSeconds(jwtConfig.getAccessTokenExp()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSigningKey())
                .compact();
    }

    public SecurityUser parseAccessJwtToken(String accessToken) {
        Jws<Claims> jwsClaims = parseTokenClaims(accessToken);
        Claims claims = jwsClaims.getBody();
        String subject = claims.getSubject();

        @SuppressWarnings("unchecked")
        List<String> scopes = claims.get(AUTHORITIES, List.class);
        if (scopes == null || scopes.isEmpty()) {
            throw new IllegalArgumentException("JWT Token doesn't have any scopes");
        }

        SecurityUser securityUser = new SecurityUser();
        securityUser.setId(UUID.fromString(subject));
        securityUser.setAuthority(AuthorityEnum.getAuthority(scopes.get(0)));
        securityUser.setFirstName(claims.get(FIRST_NAME, String.class));
        securityUser.setLastName(claims.get(LAST_NAME, String.class));
        securityUser.setEmail(claims.get(EMAIL, String.class));
        securityUser.setEnabled(claims.get(ENABLED, Boolean.class));

        return securityUser;
    }

    public String createRefreshToken(SecurityUser securityUser) {
        if (!StringUtils.hasText(securityUser.getEmail())) {
            throw new IllegalArgumentException("Cannot create refresh token without email");
        }

        ZonedDateTime currentTime = ZonedDateTime.now();

        Claims claims = Jwts.claims().setSubject(securityUser.getId().toString());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtConfig.getIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.toInstant()))
                .setExpiration(Date.from(currentTime.plusSeconds(jwtConfig.getRefreshTokenExp()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSigningKey())
                .compact();
    }

    public SecurityUser parseRefreshToken(String refreshToken) {
        Jws<Claims> jwsClaims = parseTokenClaims(refreshToken);
        Claims claims = jwsClaims.getBody();
        String subject = claims.getSubject();

        SecurityUser securityUser = new SecurityUser();
        securityUser.setId(UUID.fromString(subject));
        return securityUser;
    }

    public Jws<Claims> parseTokenClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            log.debug("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            log.debug("JWT Token is expired", expiredEx);
            throw new IoTException(ReasonEnum.EXPIRED_TOKEN, "JWT Token expired");
        }
    }
}
