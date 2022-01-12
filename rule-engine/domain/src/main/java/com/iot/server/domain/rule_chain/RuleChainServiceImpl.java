package com.iot.server.domain.rule_chain;

import com.iot.server.dao.dto.RuleChainDto;
import com.iot.server.dao.rule_chain.RuleChainDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RuleChainServiceImpl implements RuleChainService {

    private final RuleChainDao ruleChainDao;

    @Override
    public RuleChainDto findRuleChainById(UUID ruleChainId) {
        log.trace("{}", ruleChainId);
        return new RuleChainDto(ruleChainDao.findById(ruleChainId));
    }
}
