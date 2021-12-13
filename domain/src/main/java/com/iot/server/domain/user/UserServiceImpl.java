package com.iot.server.domain.user;

import com.iot.server.common.dao.client.ClientDao;
import com.iot.server.common.dao.client.entity.TenantEntity;
import com.iot.server.common.dao.db.RoleDao;
import com.iot.server.common.dao.db.UserCredentialsDao;
import com.iot.server.common.dao.db.UserDao;
import com.iot.server.common.dto.UserCredentialsDto;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.entity.RoleEntity;
import com.iot.server.common.entity.UserCredentialsEntity;
import com.iot.server.common.entity.UserEntity;
import com.iot.server.common.enums.AuthorityEnum;
import com.iot.server.common.enums.ReasonEnum;
import com.iot.server.common.exception.IoTException;
import com.iot.server.common.model.SecurityUser;
import com.iot.server.common.model.TokenAuthentication;
import com.iot.server.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    public static final String ENABLED = "enabled";
    public static final String ROLES = "roles";

    private final UserDao userDao;
    private final UserCredentialsDao userCredentialsDao;
    private final RoleDao roleDao;
    private final ClientDao clientDao;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userDto, String password) {
        log.info("{}", userDto);

        if (!userDao.existsByEmail(userDto.getEmail())) {
            UserEntity userEntity = getUserEntity(userDto);
            UserEntity savedUser = userDao.save(userEntity);

            if (savedUser != null && savedUser.getId() != null) {
                String encodedPassword = passwordEncoder.encode(password);

                UserCredentialsEntity userCredentials = getUserCredentialsEntity(savedUser, encodedPassword);
                userCredentialsDao.save(userCredentials);
            }

            return new UserDto(savedUser);
        } else {
            throw new IoTException(ReasonEnum.INVALID_PARAMS, "Email is already existed");
        }
    }

    private String getAccessToken() {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return securityUser.getAccessToken();
    }

    private TenantEntity getTenantEntity(UserEntity userEntity) {
        return TenantEntity.builder()
                .id(userEntity.getId())
                .userId(userEntity.getId())
                .email(userEntity.getEmail())
                .deleted(false)
                .createUid(userEntity.getId())
                .updateUid(null)
                .build();
    }

    private UserCredentialsEntity getUserCredentialsEntity(UserEntity savedUser, String encodedPassword) {
        return UserCredentialsEntity.builder()
                .user(savedUser)
                .activateToken(UUID.randomUUID().toString())
                .enabled(true)
                .password(encodedPassword)
                .build();
    }

    private UserEntity getUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .roles(Stream.of(AuthorityEnum.TENANT)
                        .map(authority -> createRoleIfNotFound(authority.name()))
                        .collect(Collectors.toSet()))
                .build();
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
    public UserDto findUserWithRolesByEmail(String email) {
        log.info("{}", email);
        UserEntity userEntity = userDao.findByEmail(email);
        if (userEntity == null) {
            return null;
        }

        UserDto userDto = new UserDto(userEntity);

        Map<String, Object> extraInfo = new HashMap<>();
        extraInfo.put(ROLES, userEntity.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet()));

        userDto.setExtraInfo(extraInfo);
        return userDto;
    }

    @Override
    public UserCredentialsDto findUserCredentialsByUserId(UUID userId) {
        log.info("{}", userId);
        UserCredentialsEntity userCredentialsEntity = userCredentialsDao.findByUserId(userId);
        if (userCredentialsEntity != null) {

            UserCredentialsDto userCredentialsDto = new UserCredentialsDto(userCredentialsEntity);
            userCredentialsDto.setUserId(userId);
            return userCredentialsDto;
        } else {
            log.error("User credentials is not found [{}]", userId);
            throw new IoTException(ReasonEnum.INVALID_PARAMS, "User credentials is not found");
        }
    }

    @Override
    public UserDto findUserWithExtraInfoById(UUID userId) {
        log.info("{}", userId);

        UserEntity userEntity = userDao.findById(userId);

        if (userEntity != null) {
            UserDto userDto = new UserDto(userEntity);

            UserCredentialsDto userCredentialsDto = findUserCredentialsByUserId(userId);

            Map<String, Object> extraInfo = new HashMap<>();
            extraInfo.put(ENABLED, userCredentialsDto.isEnabled());
            extraInfo.put(ROLES, userEntity.getRoles()
                    .stream()
                    .map(RoleEntity::getName)
                    .collect(Collectors.toSet()));

            userDto.setExtraInfo(extraInfo);
            return userDto;
        } else {
            log.error("User is not found [{}]", userId);
            throw new IoTException(ReasonEnum.INVALID_PARAMS, "User is not found");
        }
    }
}
