package com.iot.server.telemetry;

import com.iot.server.dao.db.role.RoleDaoImpl;
import com.iot.server.dao.db.role.RoleRepository;
import com.iot.server.dao.db.user.UserCredentialsDaoImpl;
import com.iot.server.dao.db.user.UserCredentialsRepository;
import com.iot.server.dao.db.user.UserDaoImpl;
import com.iot.server.dao.db.user.UserRepository;
import com.iot.server.domain.role.RoleServiceImpl;
import com.iot.server.domain.user.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
        basePackages = {"com.iot.server"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                UserRepository.class,
                                RoleRepository.class,
                                UserCredentialsRepository.class
                        }
                )
        }
)
@ComponentScan(
        basePackages = {"com.iot.server"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                UserDaoImpl.class,
                                UserCredentialsDaoImpl.class,
                                RoleDaoImpl.class,
                                RoleServiceImpl.class,
                                UserServiceImpl.class
                        }
                )
        }
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
