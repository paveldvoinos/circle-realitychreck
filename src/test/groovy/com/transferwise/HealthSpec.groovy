package com.transferwise

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthSpec extends Specification {

    @Autowired
    private TestRestTemplate testRestTemplate

    def "is it healthy? (Empty base path redirected correctly)"() {
        given:
        def result = testRestTemplate.getForEntity("/actuator/health", String.class)
        expect:
        result.statusCodeValue == 200
        result.body.contains("\"status\":\"UP\"")
    }

    def "is it healthy?"() {
        given:
        def result = testRestTemplate.getForEntity("/actuator/health", String.class)
        expect:
        result.statusCodeValue == 200
        result.body.contains("\"status\":\"UP\"")
    }

    def "is it alive?"() {
        given:
        def result = testRestTemplate.getForEntity("/actuator/liveness", String.class)
        expect:
        result.statusCodeValue == 200
        result.body == "true"
    }

    def "should expose metrics on the prometheus endpoint"() {
        given:
        def result = testRestTemplate.getForEntity("/actuator/prometheus", String.class)
        expect:
        result.statusCodeValue == 200
        result.body.contains("jvm_buffer_memory_used_bytes")
    }
}
