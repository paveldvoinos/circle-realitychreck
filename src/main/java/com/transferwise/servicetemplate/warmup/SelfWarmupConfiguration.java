package com.transferwise.servicetemplate.warmup;

import com.transferwise.common.bouncerjwtclient.BouncerJwt;
import com.transferwise.rest.RestTemplateBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SelfWarmupConfiguration {

    @Bean
    @BouncerJwt
    @Qualifier("selfWarmupRestTemplate")
    public RestTemplate selfWarmupRestTemplate() {
        return RestTemplateBuilder.aBuilder()
            .withBaseUrl("http://localhost:12345")
            .build();
    }
}
