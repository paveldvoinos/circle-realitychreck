package com.transferwise.servicetemplate.warmup;

import com.transferwise.common.gracefulshutdown.GracefulShutdownStrategy;
import com.transferwise.common.selfwarmup.ISelfWarmer;
import com.transferwise.common.selfwarmup.SelfWarmer;
import com.transferwise.servicetemplate.interfaces.ExampleFlowResponse;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static java.time.Duration.ofSeconds;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceTemplateSelfWarmer implements GracefulShutdownStrategy {

    @Qualifier("selfWarmupRestTemplate")
    private final RestTemplate restTemplate;

    private final MeterRegistry meterRegistry;

    @Override
    public void applicationStarted() {
        try {
            performWarmUp();
        } catch (Throwable t) {
            log.error("Application self warmup failed", t);
        }
    }

    private void performWarmUp() {

        long warmupStartTime = System.currentTimeMillis();

        List<ISelfWarmer.DoWarmupRequest.WarmupStep> warmupSteps = new ArrayList<>();

        addStep(warmupSteps, "Self warmup: GET example flow endpoint", ofSeconds(10), ofSeconds(10), () -> {
            ExampleFlowResponse exampleFlowResponse = restTemplate.getForObject("/v1/example-flow", ExampleFlowResponse.class);
            log.info("Warm up GET example flow response: {}", exampleFlowResponse);
            return true;
        });

        addStep(warmupSteps, "Self warmup: POST example flow endpoint", ofSeconds(10), ofSeconds(10), () -> {
            ExampleFlowResponse exampleFlowResponse = restTemplate.postForObject("/v1/example-flow", null ,ExampleFlowResponse.class);
            log.info("Warm up POST example flow response: {}", exampleFlowResponse);
            return true;
        });

        ISelfWarmer selfWarmer = new SelfWarmer();
        ISelfWarmer.DoWarmupResponse doWarmupResponse = selfWarmer.doWarmupSequence(new ISelfWarmer.DoWarmupRequest()
            .setExecutorService(Executors.newFixedThreadPool(1))
            .setMaxConcurrency(1)
            .setMeterRegistry(meterRegistry)
            .setTimeLimit(ofSeconds(30))
            .setWarmupSteps(warmupSteps)
        );

        long fullySuccessfulCount = doWarmupResponse
            .getWarmupStepResults()
            .stream()
            .filter(r -> r.getResultCode() == ISelfWarmer.DoWarmupResponse.WarmupStepResult.ResultCode.SUCCESS)
            .count();

        log.info("Service Template was warmed up in {} ms. {} steps out of {} were fully successful",
            System.currentTimeMillis() - warmupStartTime,
            fullySuccessfulCount,
            warmupSteps.size());
    }

    private void addStep(List<ISelfWarmer.DoWarmupRequest.WarmupStep> warmupSteps, String key,
                         Duration expectedDuration, Duration timeLimit, Supplier<Boolean> logic) {
        warmupSteps.add(new ISelfWarmer.DoWarmupRequest.WarmupStep().setTimeLimit(timeLimit)
            .setExpectedDuration(expectedDuration)
            .setKey(key)
            .setLogic(logic));
    }

    @Override
    public void prepareForShutdown() {
        //add implementation here
    }

    @Override
    public boolean canShutdown() {
        return true;
    }

    @Override
    public void applicationTerminating() {
        //add implementation here
    }
}
