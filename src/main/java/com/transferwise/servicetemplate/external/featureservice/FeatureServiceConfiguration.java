package com.transferwise.servicetemplate.external.featureservice;

import com.transferwise.rest.RestTemplateBuilder;
import com.transferwise.rest.httpclient.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

@Configuration
@ConditionalOnProperty(name = "feature-service.client", havingValue = "REST", matchIfMissing = true)
public class FeatureServiceConfiguration {

    @Value("${feature-service.url}")
    private String baseUrl;

    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(3);
    private static final Duration READ_TIMEOUT = Duration.ofSeconds(5);

    @Bean
    @Qualifier("featureServiceRestTemplate")
    public RestTemplate featureServiceRestTemplate() {
        RestTemplate restTemplate = RestTemplateBuilder.aBuilder()
            .withBaseUrl(baseUrl)
            .withHttpClient(
                HttpClientBuilder.aBuilder()
                    .readTimeoutMs((int) READ_TIMEOUT.toMillis())
                    .connectionTimeoutMs((int) CONNECT_TIMEOUT.toMillis())
                    .build()
            )
            .build();

        // force JSON
        restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));

        return restTemplate;
    }
}
