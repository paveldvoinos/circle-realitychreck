package com.transferwise.servicetemplate.exampleflow;

import com.transferwise.servicetemplate.external.featureservice.FeatureService;
import com.transferwise.servicetemplate.interfaces.ExampleFlowResponse;
import com.transferwise.servicetemplate.messages.EventSender;
import com.transferwise.servicetemplate.messages.TemplateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.transferwise.servicetemplate.messages.TemplateEvent.TW_SERVICE_TEMPLATE_EXAMPLE_TOPIC;

@Service
@RequiredArgsConstructor
public class ExampleFlowService {

    private final FeatureService featureService;
    private final EventSender eventSender;

    public ExampleFlowResponse getExampleResponse() {

        String featureEnabledString = getFeatureEnabledString();

        return ExampleFlowResponse.builder()
            .responseId(222L)
            .responseContent(featureEnabledString)
            .build();
    }

    public ExampleFlowResponse postExampleResponse() {

        sendPostExampleResponseEvent();

        return ExampleFlowResponse.builder()
            .responseId(12345L)
            .responseContent("Posted a TemplateEvent message to Kafka.")
            .build();
    }

    private void sendPostExampleResponseEvent() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        eventSender.sendEvent(
            TW_SERVICE_TEMPLATE_EXAMPLE_TOPIC,
            TemplateEvent.builder()
                .eventKey(ts.toString())
                .boolField(false)
                .stringField("postExampleResponse")
                .build());
    }

    private String getFeatureEnabledString() {
        boolean isFeatureEnabled = featureService.isEnabled("plaid2", 5985L);
        return isFeatureEnabled ? "Plaid2 is enabled" : "Plaid2 is NOT enabled";
    }


}
