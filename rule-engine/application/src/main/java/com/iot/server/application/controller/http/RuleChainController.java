package com.iot.server.application.controller.http;

import com.iot.server.application.controller.handler.GetRuleChainByIdHandler;
import com.iot.server.application.controller.request.GetRuleChainByIdRequest;
import com.iot.server.application.controller.response.GetRuleChainByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RuleChainController {

    private final GetRuleChainByIdHandler getRuleChainByIdHandler;

    @GetMapping("/rule-chain/{ruleChainId}")
    public ResponseEntity<GetRuleChainByIdResponse> getRuleChainById(@PathVariable("ruleChainId") String ruleChainId) {
        return ResponseEntity.ok(
                getRuleChainByIdHandler.handleRequest(GetRuleChainByIdRequest.builder()
                        .ruleChainId(ruleChainId)
                        .build())
        );
    }
}
