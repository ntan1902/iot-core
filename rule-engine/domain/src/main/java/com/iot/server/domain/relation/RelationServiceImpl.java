package com.iot.server.domain.relation;

import com.iot.server.dao.dto.RelationDto;
import com.iot.server.dao.relation.RelationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RelationServiceImpl implements RelationService {

    private final RelationDao relationDao;

    @Override
    public List<RelationDto> findRelationsByFromIds(List<UUID> fromIds) {
        log.trace("{}", fromIds);
        return relationDao.findAllByFromIds(fromIds)
                .stream()
                .map(relationEntity -> new RelationDto(relationEntity))
                .collect(Collectors.toList());
    }
}
