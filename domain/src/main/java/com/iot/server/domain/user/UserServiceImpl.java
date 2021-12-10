package com.iot.server.domain.user;

import com.iot.server.common.dao.RoleDao;
import com.iot.server.common.dao.UserCredentialsDao;
import com.iot.server.common.dao.UserDao;
import com.iot.server.common.dto.UserCredentialsDto;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.entity.RoleEntity;
import com.iot.server.common.entity.UserCredentialsEntity;
import com.iot.server.common.entity.UserEntity;
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
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserCredentialsDao userCredentialsDao;
    private final RoleDao roleDao;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(RegisterRequest registerRequest) {
        log.info("{}", registerRequest);

        UserEntity userEntity = UserEntity.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .roles(Stream.of(AuthorityEnum.CUSTOMER)
                        .map(authority -> createRoleIfNotFound(authority.name()))
                        .collect(Collectors.toSet()))
                .build();
        UserEntity savedUser = userDao.save(userEntity);

        if (savedUser != null && savedUser.getId() != null) {
            String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

            UserCredentialsEntity userCredentials = UserCredentialsEntity.builder()
                    .user(savedUser)
                    .activateToken(UUID.randomUUID().toString())
                    .enabled(true)
                    .password(encodedPassword)
                    .build();
            userCredentialsDao.save(userCredentials);
        }

        return new UserDto(savedUser);
    }

    RoleEntity createRoleIfNotFound(String name) {
        RoleEntity roleEntity = roleDao.findByName(name);
        if (roleEntity == null) {
            roleEntity = roleDao.save(RoleEntity.builder()
                    .name(name)
                    .build());
        }
        return roleEntity;
    }

    @Override
    public UserDto findUserByEmail(String email) {
        log.info("{}", email);
        UserEntity user = userDao.findByEmail(email);
        if (user == null) {
            return null;
        }
        return new UserDto(user);
    }

    @Override
    public UserCredentialsDto findUserCredentialsByUserId(UUID userId) {
        log.info("{}", userId);
        return new UserCredentialsDto(userCredentialsDao.findByUserId(userId));
    }
}
