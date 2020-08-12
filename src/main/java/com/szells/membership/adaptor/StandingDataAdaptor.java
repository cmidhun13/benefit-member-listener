package com.szells.membership.adaptor;

import com.szells.membership.model.BenefitPackageResponse;
import com.szells.util.domain.ServiceParameters;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class StandingDataAdaptor implements IStandingDataAdaptor {

    @Value("${member.standingData.pkgBySolIdUrl}")
    private String standingDataUrl;

    @Override
    public Flux<BenefitPackageResponse> findPackagesBySolicitationId(ServiceParameters<String> parameters) {
        return WebClient.builder()
                .baseUrl(standingDataUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, parameters.getHeaders().getAuthorization())
                .defaultUriVariables(parameters.getParameters().getParams())
                .build()
                .get()
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.findPath("standingDataResponse"))
                .map(jsonNode -> jsonNode.findPath("benefitPackages"))
                .flatMapIterable(jsonNode -> new ObjectMapper().convertValue(jsonNode, new TypeReference<List<BenefitPackageResponse>>() {
                }));
    }
}

