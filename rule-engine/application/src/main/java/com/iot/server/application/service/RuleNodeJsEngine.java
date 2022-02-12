package com.iot.server.application.service;

import com.iot.server.application.message.RuleNodeMsg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface RuleNodeJsEngine {

    CompletableFuture<String> executeToStringAsync(RuleNodeMsg msg);

    void executeUpdate(RuleNodeMsg msg);
}
