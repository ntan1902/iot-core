package com.iot.server.domain.user;

import com.iot.server.common.dao.RoleDao;
import com.iot.server.common.dao.UserCredentialsDao;
import com.iot.server.common.dao.UserDao;
import com.iot.server.common.dto.RoleDto;
import com.iot.server.common.dto.UserCredentialsDto;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.enums.AuthorityEnum;
import com.iot.server.common.request.RegisterRequest;
import com.iot.server.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserCredentialsDao userCredentialsDao;
    private final RoleDao roleDao;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto registerUser(RegisterRequest registerRequest) {
        log.info("{}", registerRequest);


        UserDto userDto = UserDto.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .roles(Stream.of(AuthorityEnum.CUSTOMER)
                        .map(authority -> createRoleIfNotFound(authority.name()))
                        .collect(Collectors.toSet()))
                .build();
        UserDto savedUser = userDao.save(userDto);

        if (savedUser != null && savedUser.getId() != null) {
            String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

            UserCredentialsDto userCredentials = UserCredentialsDto.builder()
                    .userId(savedUser.getId())
                    .activateToken(UUID.randomUUID().toString())
                    .enabled(true)
                    .password(encodedPassword)
                    .build();
            userCredentialsDao.save(userCredentials);
        }

        return savedUser;
    }

    @Transactional
    RoleDto createRoleIfNotFound(String name) {
        RoleDto roleDto = roleDao.findByName(name);
        if (roleDto == null) {
            roleDto = roleDao.save(RoleDto.builder()
                    .name(name)
                    .build());
        }
        return roleDto;
    }

    @Override
    @Transactional
    public UserDto findUserByEmail(String email) {
        log.info("{}", email);
        return userDao.findByEmail(email);
    }

    @Override
    public UserCredentialsDto findUserCredentialsByUserId(UUID userId) {
        log.info("{}", userId);
        return userCredentialsDao.findByUserId(userId);
    }
}
