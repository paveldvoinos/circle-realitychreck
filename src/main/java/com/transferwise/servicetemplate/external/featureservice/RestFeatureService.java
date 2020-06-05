package com.transferwise.servicetemplate.external.featureservice;

import com.transferwise.feature.presentation.api.model.AssignmentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConditionalOnProperty(name = "feature-service.client", havingValue = "REST", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class RestFeatureService implements FeatureService {


    private static final String GET_ASSIGNMENT_ENDPOINT = "/api/v1/features/{featureName}/assignment?userId={userId}";

    @Qualifier("featureServiceRestTemplate")
    private final RestTemplate featureServiceRestTemplate;

    @Override
    public boolean isEnabled(String featureName, Long userId) {
        try {
            AssignmentDTO assignmentDTO = featureServiceRestTemplate.getForObject(GET_ASSIGNMENT_ENDPOINT, AssignmentDTO.class, featureName, userId);
            if (assignmentDTO == null) {
                return false;
            }
            return assignmentDTO.getEnabled();
        } catch (Exception e) {
            log.warn("An error occurred while getting data from feature service for feature {} and userId {}", featureName, userId, e);
            return false;
        }
    }

}
