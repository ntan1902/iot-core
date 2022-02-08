package com.iot.server.application.service;

import com.iot.server.application.message.RuleNodeMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class RuleNodeJsEngineImpl implements RuleNodeJsEngine {
    private final NashornService nashornService;
    private final UUID scriptId;

    public RuleNodeJsEngineImpl(NashornService nashornService, String script, String... args) {
        this.nashornService = nashornService;

        this.scriptId = this.nashornService.eval(script, args);
    }

    private String[] getArgs(RuleNodeMsg msg) {
        try {
            String[] args = new String[3];
            if (msg.getData() != null) {
                args[0] = msg.getData();
            } else {
                args[0] = "";
            }
            args[1] = msg.getType();
            return args;
        } catch (Throwable th) {
            throw new IllegalArgumentException("Cannot bind js args", th);
        }
    }


    @Override
    public CompletableFuture<String> executeToStringAsync(RuleNodeMsg msg) {
        String[] stringArgs = getArgs(msg);

        return CompletableFuture.supplyAsync(() -> this.nashornService.invokeFunction(this.scriptId, stringArgs).toString());
    }
}