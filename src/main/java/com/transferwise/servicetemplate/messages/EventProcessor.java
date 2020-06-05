package com.transferwise.servicetemplate.messages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.transferwise.servicetemplate.messages.TemplateEvent.TW_SERVICE_TEMPLATE_EXAMPLE_TOPIC;

@Component
@Slf4j
@Profile("!integration")
public class EventProcessor {
    private static final String EXAMPLE_CUSTOM_LISTENER = "customListener";

    @KafkaListener(topics = TW_SERVICE_TEMPLATE_EXAMPLE_TOPIC, containerFactory = EXAMPLE_CUSTOM_LISTENER)
    public void process(TemplateEvent event) { // TODO: `List<TemplateEvent> events` if use batch listener

        // TODO: process the event here
        log.info(event.toString());
    }

}