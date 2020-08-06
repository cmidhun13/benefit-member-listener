//For now we are comment against Standing data later we uncomment
package com.szells.membership.adaptor;

import com.szells.membership.constants.Constants;
import com.szells.util.domain.RuleEngineResponse;
import com.szells.util.domain.ServiceParameters;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.szells.membership.constants.Constants.*;

@Service
public class RuleEngineAdaptor implements IRuleEngineAdaptor {

    private static final Map<String, String> membershipStatusAttributes =
            Collections.singletonMap(ATTRIBUTE_NAME, MEMBERSHIP_STATUS);

    @Value("visibilityscope.api.ruleEngineUrl")
    private String ruleEngineUrl;

    @Value("member.ruleEngine.attributeUrl")
    private String ruleEngineAttributeUrl;

    private static String getStatus(List<Status> results, String statusCode, String defaultStatus) {
        AtomicReference<String> statusResponse = new AtomicReference<>(defaultStatus);
        results.stream()
                .filter(status -> status.getId() == Integer.parseInt(statusCode))
                .findFirst()
                .ifPresent(status -> statusResponse.set(status.getValue()));
        return statusResponse.get();
    }

    @Override
    public Mono<Boolean> checkIfLightFamiliesActivated(ServiceParameters<String> parameters) {
        Map<String, String> clientAttributes = new HashMap<>();
        clientAttributes.put(ATTRIBUTE_NAME, LIGHT_FAMILIES_ACTIVATED);
        clientAttributes.put(Constants.ATTRIBUTE_VALUE, parameters.getHeaders().getVisibilityScopeKey());
        List<Map<String, String>> ruleEngRequest = Collections.singletonList(clientAttributes);

        return WebClient.create(ruleEngineUrl)
                .post()
                .bodyValue(ruleEngRequest)
                .accept(MediaType.APPLICATION_JSON)
                .header(BRMS_TOKEN_KEY, parameters.getHeaders().getBrmsTokenKey())
                .retrieve()
                .bodyToMono(RuleEngineResponse.class)
                .map(RuleEngineResponse::getResult)
                .map(results -> results.stream()
                        .anyMatch(result -> Constants.ACTIVATION_LIGHT_FAMILIES.equalsIgnoreCase(result.getAttributeName())
                                && Constants.ON.equalsIgnoreCase(result.getAttributeValue()))
                );
    }

    @Override
    public Mono<String> findMembershipStatusValForClient(ServiceParameters<String> parameters) {

        return WebClient.create(ruleEngineAttributeUrl)
                .post()
                .bodyValue(membershipStatusAttributes)
                .accept(MediaType.APPLICATION_JSON)
                .header(BRMS_TOKEN_KEY, parameters.getHeaders().getBrmsTokenKey())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Status>>() {
                })
                .map(results -> getStatus(results, parameters.getParameters().getParam(MEMBERSHIP_STATUS), parameters.getParameters().getParam(DEFAULT_STATUS)))
                .onErrorReturn(parameters.getParameters().getParam(DEFAULT_STATUS));
    }

    @Override
    public Flux<String> getSolicitationForClient(ServiceParameters<String> parameters) {
        Map<String, String> clientAttributes = new HashMap<>();
        clientAttributes.put(ATTRIBUTE_NAME, SOLICITATION_ID);
        clientAttributes.put(Constants.ATTRIBUTE_VALUE, parameters.getParameters().getParam(SOLICITATION_ID));
        List<Map<String, String>> ruleEngRequest = Collections.singletonList(clientAttributes);
        return WebClient.create(ruleEngineAttributeUrl)
                .post()
                .bodyValue(ruleEngRequest)
                .accept(MediaType.APPLICATION_JSON)
                .header(BRMS_TOKEN_KEY, parameters.getHeaders().getBrmsTokenKey())
                .retrieve()
                .bodyToMono(RuleEngineResponse.class)
                .map(RuleEngineResponse::getResult)
                .flatMapIterable(results -> results.stream()
                        .map(RuleEngineResponse.Result::getAttributeValue)
                        .collect(Collectors.toList()));
    }

    @Getter
    @Setter
    static class Status {
        private int id;
        private String value;
    }
}


