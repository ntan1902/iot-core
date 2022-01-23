package com.iot.server.application;

import delight.graaljssandbox.GraalSandbox;
import delight.graaljssandbox.GraalSandboxes;
import delight.nashornsandbox.NashornSandbox;
import delight.nashornsandbox.NashornSandboxes;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

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
        GraalSandbox sandboxGraaljs = GraalSandboxes.create();
        System.out.println(sandboxGraaljs);
        long startTimeGraalJs = System.currentTimeMillis();
        sandboxGraaljs.eval("let count = 0;for(let i=0;i<100000;i++){count += i}");
        System.out.println("finish " + (System.currentTimeMillis() - startTimeGraalJs));

        NashornSandbox nashornSandbox = NashornSandboxes.create();
        System.out.println(nashornSandbox);
        long startTimeNashornSandbox = System.currentTimeMillis();
        nashornSandbox.eval("var count = 0;for(var i=0;i<100000;i++){count += i}");
        System.out.println("finish " + (System.currentTimeMillis() - startTimeNashornSandbox));

        ScriptEngineManager factory = new ScriptEngineManager();

        ScriptEngine engine = factory.getEngineByName("nashorn");
        System.out.println(engine.toString());
        long startTimeNashorn = System.currentTimeMillis();
        engine.eval("var count = 0;for(var i=0;i<100000;i++){count += i} java.lang.System.out.println(\"Hello from JS\");\n");
        System.out.println("finish " + (System.currentTimeMillis() - startTimeNashorn));

        ScriptEngine engineJs = factory.getEngineByName("js");
        System.out.println(engineJs.toString());
        long startTime = System.currentTimeMillis();
        engineJs.eval("let count = 0;for(let i=0;i<100000;i++){count += i}");
        System.out.println("finish " + (System.currentTimeMillis() - startTime));

    }
}
