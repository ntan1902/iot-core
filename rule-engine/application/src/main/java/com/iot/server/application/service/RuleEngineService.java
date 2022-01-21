package com.iot.server.application.service;

import com.iot.server.application.message.RuleNodeMsg;

import java.lang.reflect.InvocationTargetException;


public interface RuleEngineService {
    void process(RuleNodeMsg ruleNodeMsg) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
