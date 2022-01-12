package com.iot.server.dao.rule_chain;

import com.iot.server.common.dao.JpaAbstractDao;
import com.iot.server.dao.entity.rule_chain.RuleChainEntity;
import com.iot.server.dao.repository.RuleChainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RuleChainDaoImpl extends JpaAbstractDao<RuleChainEntity, UUID> implements RuleChainDao {

    private final RuleChainRepository ruleChainRepository;

    @Override
    protected JpaRepository<RuleChainEntity, UUID> getJpaRepository() {
        return ruleChainRepository;
    }

}
