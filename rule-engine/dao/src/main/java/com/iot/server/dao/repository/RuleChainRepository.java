package com.iot.server.dao.repository;

import com.iot.server.dao.entity.rule_chain.RuleChainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RuleChainRepository extends JpaRepository<RuleChainEntity, UUID> {
}
