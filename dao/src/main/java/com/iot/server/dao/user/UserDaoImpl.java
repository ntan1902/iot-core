package com.iot.server.dao.user;

import com.iot.server.common.dao.UserDao;
import com.iot.server.common.dto.UserDto;
import com.iot.server.common.enums.ReasonEnum;
import com.iot.server.common.exception.IoTException;
import com.iot.server.dao.DaoUtil;
import com.iot.server.dao.JpaAbstractDao;
import com.iot.server.dao.entity.UserEntity;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDaoImpl extends JpaAbstractDao<UserEntity, UserDto, UUID> implements UserDao {

   private final UserRepository userRepository;

   @Override
   protected Class<UserEntity> getEntityClass() {
      return UserEntity.class;
   }

   @Override
   protected JpaRepository<UserEntity, UUID> getJpaRepository() {
      return userRepository;
   }

   @Override
   public UserDto findByEmail(String email) {
      return DaoUtil.getDto(
              userRepository.findByEmail(email)
      );
   }
}
