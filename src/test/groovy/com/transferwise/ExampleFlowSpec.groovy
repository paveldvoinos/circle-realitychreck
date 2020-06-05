package com.transferwise

import com.transferwise.servicetemplate.interfaces.ExampleFlowResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExampleFlowSpec extends Specification {

    @Autowired
    private TestRestTemplate testRestTemplate

    def "do we have example flow"() {
        given:
        def result = testRestTemplate.getForEntity("/v1/example-flow", ExampleFlowResponse.class)
        expect:
        result.statusCodeValue == 200
        result.body.responseId == 222
    }

}
