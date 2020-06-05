package com.transferwise.servicetemplate.messages;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class EventSender {

    private final KafkaTemplate<String, TemplateEvent> kafkaTemplate;

    public void sendEvent(String topic, TemplateEvent event) {
        /* !!! IMPORTANT !!! always set the key when you send the event.
         * if you don't care about ordering or event key in general,
         * use <code>UUID.randomUUID().toString()</code> for generating random keys.
         * if you don't set the key, Kafka will send all events to single partition for 10 min.
         * In practice, this means that without specifying the key, you lose the parallelism on consumer side.
         * more details here: https://cwiki.apache.org/confluence/display/KAFKA/FAQ#FAQ-Whyisdatanotevenlydistributedamongpartitionswhenapartitioningkeyisnotspecified?
         */
        log.info("Sending event {}", event);
        kafkaTemplate.send(topic, event.getEventKey(), event);
    }

}
