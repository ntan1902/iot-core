package com.iot.server.application;

import delight.nashornsandbox.NashornSandbox;
import delight.nashornsandbox.NashornSandboxes;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.script.ScriptException;
import java.util.concurrent.Executors;

@SpringBootApplication(scanBasePackages = {"com.iot.server"})
@SpringBootConfiguration
@EnableJpaRepositories(basePackages = {"com.iot.server"})
@EntityScan(basePackages = {"com.iot.server"})
@ComponentScan({"com.iot.server"})
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        NashornSandbox nashornSandbox = NashornSandboxes.create();

        nashornSandbox.setMaxCPUTime(100);
        nashornSandbox.allowNoBraces(false);
        nashornSandbox.setMaxPreparedStatements(30); // because preparing scripts for execution is expensive
        nashornSandbox.setExecutor(Executors.newSingleThreadExecutor());

        try {
            nashornSandbox.eval("" +
                    "function invokeFunction_d270da90_00d9_4a1c_ba07_19ac2ba95f6a(msgStr, msgType) {" +
                    "   var msg = JSON.parse(msgStr);" +
                    "   return JSON.stringify(ruleNodeFunctionName(msg, msgType)) " +
                    "   function ruleNodeFunctionName(msg, msgType) {" +
                    "       return JSON.stringify(msg);\n" +
                    "   }\n" +
                    "}");
            System.out.println(nashornSandbox.getSandboxedInvocable().invokeFunction("invokeFunction_d270da90_00d9_4a1c_ba07_19ac2ba95f6a", "{\"s\": \"hello\"}", "{}"));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
