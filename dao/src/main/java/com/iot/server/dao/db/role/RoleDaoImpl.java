package com.iot.server.dao.db.role;

import com.iot.server.common.dao.db.RoleDao;
import com.iot.server.dao.db.JpaAbstractDao;
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
