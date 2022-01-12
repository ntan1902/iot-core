package com.iot.server.dao.rule_node;

import com.iot.server.common.dao.JpaAbstractDao;
import com.iot.server.dao.entity.rule_node.RuleNodeEntity;
import com.iot.server.dao.repository.RuleNodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RuleNodeDaoImpl extends JpaAbstractDao<RuleNodeEntity, UUID> implements RuleNodeDao {

    private final RuleNodeRepository ruleNodeRepository;

    @Override
    protected JpaRepository<RuleNodeEntity, UUID> getJpaRepository() {
        return ruleNodeRepository;
    }

}
