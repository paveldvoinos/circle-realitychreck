package com.transferwise.servicetemplate.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
@Slf4j
@RequiredArgsConstructor
@Profile("!integration")
public class EventProcessorConfiguration {
    private static final String EXAMPLE_CUSTOM_LISTENER = "customListener";
    private static final long POLL_TIMEOUT = 30000L;
    private final ObjectMapper objectMapper;

    @Bean(EXAMPLE_CUSTOM_LISTENER)
    public ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory(ConsumerFactory<?, ?> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();

        concurrentKafkaListenerContainerFactory.setConsumerFactory((ConsumerFactory<String, String>) consumerFactory);
        concurrentKafkaListenerContainerFactory.setMessageConverter(new StringJsonMessageConverter(objectMapper));
        concurrentKafkaListenerContainerFactory.getContainerProperties().setAckOnError(false);
        concurrentKafkaListenerContainerFactory.getContainerProperties().setPollTimeout(POLL_TIMEOUT);

        // TODO: Consider using batch listener
        // concurrentKafkaListenerContainerFactory.setBatchListener(true);

        return concurrentKafkaListenerContainerFactory;
    }

}