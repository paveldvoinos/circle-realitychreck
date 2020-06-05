package com.transferwise.servicetemplate.external.featureservice;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "feature-service.client", havingValue = "MOCK")
public class MockFeatureService implements FeatureService {

    @Override
    public boolean isEnabled(String featureName, Long userId) {
        return true;
    }
}
