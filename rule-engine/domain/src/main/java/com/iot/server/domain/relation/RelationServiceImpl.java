package com.iot.server.domain.relation;

import com.iot.server.common.entity.BaseEntity;
import com.iot.server.dao.dto.RelationDto;
import com.iot.server.dao.entity.relation.RelationCompositeKey;
import com.iot.server.dao.entity.relation.RelationEntity;
import com.iot.server.dao.entity.rule_node.RuleNodeEntity;
import com.iot.server.dao.relation.RelationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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

    @Override
    public List<RelationDto> updateRelations(List<RelationEntity> relationEntities, List<RuleNodeEntity> ruleNodeEntities) {
        log.trace("{}, {}", relationEntities, ruleNodeEntities);

        List<UUID> ruleNodeIds = ruleNodeEntities.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        Map<RelationCompositeKey, Integer> relationIndexMap = getRelationIndexMap(relationEntities);
        List<RelationEntity> foundRelations = relationDao.findAllByFromIdsOrToIds(ruleNodeIds);
        List<RelationEntity> toDelete = new ArrayList<>();

        for (RelationEntity foundRelation : foundRelations) {
            RelationCompositeKey key = RelationCompositeKey.builder()
                    .fromId(foundRelation.getFromId())
                    .fromType(foundRelation.getFromType())
                    .toId(foundRelation.getToId())
                    .toType(foundRelation.getToType())
                    .build();

            Integer index = relationIndexMap.get(key);
            if (index == null) {
                toDelete.add(foundRelation);
            }
        }

        if (!toDelete.isEmpty()) {
            relationDao.deleteRelations(toDelete);
        }

        return relationDao.saveAll(relationEntities)
                .stream()
                .map(RelationDto::new)
                .collect(Collectors.toList());
    }

    private Map<RelationCompositeKey, Integer> getRelationIndexMap(List<RelationEntity> relationEntities) {
        List<RelationCompositeKey> relationCompositeKeys = relationEntities.stream()
                .map(relationEntity -> RelationCompositeKey.builder()
                        .fromId(relationEntity.getFromId())
                        .fromType(relationEntity.getFromType())
                        .toId(relationEntity.getToId())
                        .toType(relationEntity.getToType())
                        .name(relationEntity.getName())
                        .build())
                .collect(Collectors.toList());
        return relationCompositeKeys.stream()
                .collect(
                        Collectors.toMap(key -> key, relationCompositeKeys::indexOf));
    }
}
