package com.iot.server.dao.user;

import com.iot.server.common.dao.UserCredentialsDao;
import com.iot.server.common.dto.UserCredentialsDto;
import com.iot.server.dao.JpaAbstractDao;
import com.iot.server.common.entity.UserCredentialsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserCredentialsDaoImpl extends JpaAbstractDao<UserCredentialsEntity,UUID> implements UserCredentialsDao {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    protected JpaRepository<UserCredentialsEntity, UUID> getJpaRepository() {
        return userCredentialsRepository;
    }

    @Override
    public UserCredentialsEntity findByUserId(UUID userId) {
        return userCredentialsRepository.findByUserId(userId).orElse(null);
    }
}
