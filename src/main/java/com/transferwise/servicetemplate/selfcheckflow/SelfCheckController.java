package com.transferwise.servicetemplate.selfcheckflow;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SelfCheckController {

    private final SelfCheckService selfCheckService;

    @GetMapping(path = "/v1/self-check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getExampleFlow() {
        try {
            String getResult = selfCheckService.getExampleFlowUsingClient();
            String postResult = selfCheckService.postExampleFlowUsingClient();
            return ResponseEntity.ok(getResult + "||" + postResult);
        } catch (RestClientException e) {
            log.error("fail to respond with example response", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
