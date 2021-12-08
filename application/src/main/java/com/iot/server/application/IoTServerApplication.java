package com.iot.server.application;

import com.iot.server.common.dto.RoleDto;
import com.iot.server.common.enums.AuthorityEnum;
import com.iot.server.common.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.iot.server"})
@EnableJpaRepositories(basePackages = {"com.iot.server"})
@EntityScan(basePackages = {"com.iot.server"})
public class IoTServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoTServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(final RoleService roleService) {
        return args -> {
            roleService.save(
                    RoleDto.builder()
                            .name(AuthorityEnum.CUSTOMER.name())
                            .build());
            roleService.save(
                    RoleDto.builder()
                            .name(AuthorityEnum.TENANT.name())
                            .build());
            roleService.save(
                    RoleDto.builder()
                            .name(AuthorityEnum.ADMIN.name())
                            .build());
        };
    }
}
