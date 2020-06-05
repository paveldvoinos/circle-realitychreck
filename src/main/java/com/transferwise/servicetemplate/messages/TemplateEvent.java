package com.transferwise.servicetemplate.messages;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
// JSON: {"boolField": true, "eventKey": "myKey", "stringField": "myString"}
public class TemplateEvent {

    public static final String TW_SERVICE_TEMPLATE_EXAMPLE_TOPIC = "TwServiceTemplate.ExampleTopic";


    private boolean boolField;
    private String stringField;
    private String eventKey;
}