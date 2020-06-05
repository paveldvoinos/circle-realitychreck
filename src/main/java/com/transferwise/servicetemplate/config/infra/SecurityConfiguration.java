package com.transferwise.servicetemplate.config.infra;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableResourceServer
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Profile({"production-kubernetes", "staging-kubernetes"})
public class SecurityConfiguration implements ResourceServerConfigurer {
    @NonNull
    private final ResourceServerTokenServices tokenServices;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .sessionManagement().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .rememberMe().disable()
            .logout().disable()

            .authorizeRequests()
            .requestMatchers(EndpointRequest.to("health", "liveness"))
            .permitAll()

            .and()
            .authorizeRequests()
            .regexMatchers("/v[0-9]+/api-docs.*", ".*swagger.*", ".*self-check.*")
            .permitAll()
            .anyRequest().authenticated()

            .and()
            .csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(tokenServices);
    }

    @Profile({"development", "development-with-ce", "integration"})
    @EnableWebSecurity
    @Configuration
    public static class Disable extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity http) {
            http.ignoring().anyRequest();
        }
    }
}
