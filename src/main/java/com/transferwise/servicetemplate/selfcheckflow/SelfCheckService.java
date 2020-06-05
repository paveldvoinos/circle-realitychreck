package com.transferwise.servicetemplate.selfcheckflow;

import com.transferwise.servicetemplate.client.ServiceTemplateClient;
import com.transferwise.servicetemplate.interfaces.ExampleFlowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelfCheckService {

    private final ServiceTemplateClient serviceTemplateClient;

    public String getExampleFlowUsingClient() {
        ExampleFlowResponse response = serviceTemplateClient.getExampleFlow();
        return "The GET response is: " + response.toString();
    }

    public String postExampleFlowUsingClient() {
        ExampleFlowResponse response = serviceTemplateClient.postExampleFlow();
        return "The POST response is: " + response.toString();
    }
}
