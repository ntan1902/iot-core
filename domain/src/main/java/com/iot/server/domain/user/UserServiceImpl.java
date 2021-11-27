package com.iot.server.domain.user;

import com.iot.server.common.dao.UserCredentialsDao;
import com.iot.server.common.dao.UserDao;
import com.iot.server.common.service.UserService;
import com.iot.server.common.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserCredentialsDao userCredentialsDao;

    @Override
    public UserDto save(UserDto userDto) {
        return null;
    }
}
