package com.transferwise.servicetemplate.external.featureservice;

public interface FeatureService {

    boolean isEnabled(String featureName, Long userId);
}
