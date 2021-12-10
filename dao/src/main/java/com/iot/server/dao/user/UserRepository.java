package com.iot.server.dao.user;

import com.iot.server.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query(value = "" +
            "SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.roles " +
            "WHERE u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);
}
