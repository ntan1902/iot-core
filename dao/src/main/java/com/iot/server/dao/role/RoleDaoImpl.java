package com.iot.server.dao.role;

import com.iot.server.common.dao.RoleDao;
import com.iot.server.common.dto.RoleDto;
import com.iot.server.dao.JpaAbstractDao;
import com.iot.server.common.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoleDaoImpl extends JpaAbstractDao<RoleEntity, UUID> implements RoleDao {

    private final RoleRepository roleRepository;

    @Override
    protected JpaRepository<RoleEntity, UUID> getJpaRepository() {
        return roleRepository;
    }

    @Override
    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }
}
