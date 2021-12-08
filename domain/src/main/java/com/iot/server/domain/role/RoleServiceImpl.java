package com.iot.server.domain.role;

import com.iot.server.common.dao.RoleDao;
import com.iot.server.common.dto.RoleDto;
import com.iot.server.common.enums.AuthorityEnum;
import com.iot.server.common.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Override
    public void save(RoleDto roleDto) {
        roleDao.save(roleDto);
    }
}
