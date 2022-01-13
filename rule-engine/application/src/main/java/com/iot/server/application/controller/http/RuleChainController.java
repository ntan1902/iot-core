package com.iot.server.application.controller.http;

import com.iot.server.application.controller.handler.GetRuleChainByIdHandler;
import com.iot.server.application.controller.handler.GetRuleChainsHandler;
import com.iot.server.application.controller.request.GetRuleChainByIdRequest;
import com.iot.server.application.controller.request.GetRuleChainsRequest;
import com.iot.server.application.controller.response.GetRuleChainByIdResponse;
import com.iot.server.application.controller.response.GetRuleChainsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RuleChainController {

    private final GetRuleChainByIdHandler getRuleChainByIdHandler;
    private final GetRuleChainsHandler getRuleChainsHandler;

    @GetMapping("/rule-chain/{ruleChainId}")
    @PreAuthorize("hasAnyAuthority('TENANT')")
    public ResponseEntity<GetRuleChainByIdResponse> getRuleChainById(@PathVariable("ruleChainId") String ruleChainId) {
        return ResponseEntity.ok(
                getRuleChainByIdHandler.handleRequest(GetRuleChainByIdRequest.builder()
                        .ruleChainId(ruleChainId)
                        .build())
        );
    }

    @GetMapping("/rule-chains")
    @PreAuthorize("hasAnyAuthority('TENANT')")
    public ResponseEntity<GetRuleChainsResponse> getRuleChains(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                                               @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                               @RequestParam(value = "property", defaultValue = "createdAt") String property) {
        return ResponseEntity.ok(
                getRuleChainsHandler.handleRequest(
                        GetRuleChainsRequest.builder()
                                .page(page)
                                .size(size)
                                .order(order)
                                .property(property)
                                .build()
                )
        );
    }
}
