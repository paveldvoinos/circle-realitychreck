package com.transferwise.servicetemplate.exampleflow;

import com.transferwise.servicetemplate.interfaces.ExampleFlowResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExampleFlowController {

    private final ExampleFlowService exampleFlowService;

    @GetMapping(path = "/v1/example-flow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExampleFlowResponse> getExampleFlow() {
        try {
            ExampleFlowResponse result = exampleFlowService.getExampleResponse();
            return ResponseEntity.ok(result);
        } catch (RestClientException e) {
            log.error("fail to respond with example response", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/v1/example-flow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExampleFlowResponse> postExampleFlow() {
        ExampleFlowResponse result = exampleFlowService.postExampleResponse();
        return ResponseEntity.ok(result);
    }

}
