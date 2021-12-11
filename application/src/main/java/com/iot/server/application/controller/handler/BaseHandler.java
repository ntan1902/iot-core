package com.iot.server.application.controller.handler;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public abstract class BaseHandler<T, U> {
    protected abstract U processRequest(T t);

    public U handleRequest(T t) {
        log.info("request={}", t);
        val result = processRequest(t);
        log.info("response={}", result);
        return result;
    }
}


