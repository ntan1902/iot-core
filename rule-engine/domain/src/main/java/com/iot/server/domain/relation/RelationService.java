package com.iot.server.domain.relation;

import com.iot.server.dao.dto.RelationDto;

import java.util.List;
import java.util.UUID;

public interface RelationService {
    List<RelationDto> findRelationsByFromIds(List<UUID> fromIds);
}
