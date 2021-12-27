package com.iot.server.domain.role;

import com.iot.server.common.dao.RoleDao;
import com.iot.server.common.dto.RoleDto;
import com.iot.server.common.entity.RoleEntity;
import com.iot.server.common.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Override
    public void save(RoleDto roleDto) {
        roleDao.save(new RoleEntity(roleDto));
    }
}
