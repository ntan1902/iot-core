package com.iot.server.application.security.provider;

import com.iot.server.application.security.model.SecurityUser;
import com.iot.server.application.service.SecurityService;
import com.iot.server.common.dto.UserCredentialsDto;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final SecurityService securityService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        return authenticateEmailAndPassword(email, password);
    }

    private Authentication authenticateEmailAndPassword(String email, String password) {
        UserDto user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found: " + email);
        }
        if (user.getAuthority() == null)
            throw new InsufficientAuthenticationException("User has no authority assigned");

        UserCredentialsDto userCredentials = userService.findUserCredentialsByUserId(user.getId());
        if (userCredentials == null) {
            throw new UsernameNotFoundException("User credentials is not found: " + user.getId());
        }

        securityService.validateUserCredentials(userCredentials, email, password);

        SecurityUser securityUser = new SecurityUser(user, userCredentials.isEnabled());

        return new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
