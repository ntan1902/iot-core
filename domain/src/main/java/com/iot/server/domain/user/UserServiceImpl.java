package com.iot.server.domain.user;

import com.iot.server.common.dao.UserCredentialsDao;
import com.iot.server.common.dao.UserDao;
import com.iot.server.common.dto.UserCredentialsDto;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserCredentialsDao userCredentialsDao;

    @Override
    public UserDto saveUser(UserDto userDto, String password) {
        log.info("Executing [{}]", userDto);
        UserDto savedUser = userDao.save(userDto);

        if (savedUser != null && savedUser.getId() != null) {
            UserCredentialsDto userCredentials = UserCredentialsDto.builder()
                    .userId(savedUser.getId())
                    .activateToken(UUID.randomUUID().toString())
                    .enabled(true)
                    .password(password)
                    .build();
            userCredentialsDao.save(userCredentials);
        }

        return savedUser;
    }

    @Override
    public UserDto findUserByEmail(String email) {
        log.info("Executing [{}]", email);
        return userDao.findByEmail(email);
    }

    @Override
    public UserCredentialsDto findUserCredentialsByUserId(UUID userId) {
        log.info("Executing [{}]", userId);
        return userCredentialsDao.findByUserId(userId);
    }
}
