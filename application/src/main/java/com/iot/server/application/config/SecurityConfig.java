package com.iot.server.application.config;

import com.iot.server.application.exception.IoTExceptionHandler;
import com.iot.server.application.security.filter.JwtAuthorizationFilter;
import com.iot.server.application.security.filter.LoginAuthenticationFilter;
import com.iot.server.application.security.provider.JwtAuthorizationProvider;
import com.iot.server.application.security.provider.LoginAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String LOGIN_URL = "/api/auth/login";
    public static final String REGISTER_URL = "/api/auth/register";
    public static final String REFRESH_TOKEN_URL = "/api/auth/refresh-token";
    public static final String JWK_SET_URL = "/api/auth/.well-known/jwks.json";

    private final IoTExceptionHandler ioTExceptionHandler;

    private final JwtAuthorizationProvider jwtAuthorizationProvider;
    private final LoginAuthenticationProvider loginAuthenticationProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager Bean
        return super.authenticationManagerBean();
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setAuthenticationManager(this.authenticationManager());
        filter.setFilterProcessesUrl(LOGIN_URL);
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(
                LOGIN_URL,
                REGISTER_URL,
                REFRESH_TOKEN_URL,
                JWK_SET_URL
        );
        return new JwtAuthorizationFilter(pathsToSkip, this.authenticationManager());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthorizationProvider);
        auth.authenticationProvider(loginAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().accessDeniedHandler(ioTExceptionHandler)
                .and().authorizeRequests()
                .antMatchers(LOGIN_URL).permitAll()
                .antMatchers(REGISTER_URL).permitAll()
                .antMatchers(JWK_SET_URL).permitAll()
                .anyRequest().authenticated().and()
                .addFilter(loginAuthenticationFilter())
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
