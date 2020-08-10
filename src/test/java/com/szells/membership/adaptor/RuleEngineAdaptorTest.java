package com.szells.membership.adaptor;

import com.szells.membership.constants.Constants;
import com.szells.util.domain.RequestHeader;
import com.szells.util.domain.RequestParameters;
import com.szells.util.domain.RuleEngineResponse;
import com.szells.util.domain.ServiceParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.szells.membership.constants.Constants.*;

@RunWith(SpringRunner.class)
public class RuleEngineAdaptorTest {

    private static MockWebServer mockWebServer;
    private IRuleEngineAdaptor ruleEngineAdaptor;

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    private static ServiceParameters<String> buildLightFamilyInput() {
        return ServiceParameters.<String>builder()
                .headers(RequestHeader.builder()
                        .visibilityScopeKey("Mc124332452435")
                        .correlationId("2jd82hdj23-2fj23489dfj23")
                        .brmsTokenKey("314-2342358457")
                        .build())
                .parameters(RequestParameters.builder()
                        .param(MEMBERSHIP_STATUS, DEFAULT_NEW_STATUS)
                        .param(DEFAULT_STATUS, NEW)
                        .build())
                .build();
    }

    private static String buildOnResponse() {
        RuleEngineResponse response = new RuleEngineResponse();
        response.setRuleName("Name");
        response.setRuleID(12);
        RuleEngineResponse.Result result = new RuleEngineResponse.Result();
        result.setAttributeName(ACTIVATION_LIGHT_FAMILIES);
        result.setAttributeValue(Constants.ON);
        ArrayList<RuleEngineResponse.Result> results = new ArrayList<>();
        results.add(result);
        response.setResult(results);
        try {
            return new ObjectMapper().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String buildOffResponse() {
        RuleEngineResponse response = new RuleEngineResponse();
        response.setRuleName("Name");
        response.setRuleID(12);
        RuleEngineResponse.Result result = new RuleEngineResponse.Result();
        result.setAttributeValue(ACTIVATION_LIGHT_FAMILIES);
        result.setAttributeValue("OFF");
        ArrayList<RuleEngineResponse.Result> results = new ArrayList<>();
        results.add(result);
        response.setResult(results);
        try {
            return new ObjectMapper().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String buildStatusResponse() {
        RuleEngineAdaptor.Status status = new RuleEngineAdaptor.Status();
        status.setId(104);
        status.setValue("CANCELLED_STATUS");
        List<RuleEngineAdaptor.Status> statuses = new ArrayList<>();
        statuses.add(status);
        status = new RuleEngineAdaptor.Status();
        status.setId(101);
        status.setValue("NEW_STATUS");
        statuses.add(status);
        try {
            return new ObjectMapper().writeValueAsString(statuses);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Before
    public void setUp() {
        ruleEngineAdaptor = new RuleEngineAdaptor();
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String baseUrl = String.format("http://localhost:%s",
                mockWebServer.getPort());

        ReflectionTestUtils.setField(ruleEngineAdaptor, "ruleEngineUrl", baseUrl + "validate");
        ReflectionTestUtils.setField(ruleEngineAdaptor, "ruleEngineAttributeUrl", baseUrl + "attributevalues");

    }

    @Test
    public void testCheckIfLightFamiliesActivated() {
        mockWebServer.enqueue(new MockResponse()
                .setBody((Objects.requireNonNull(buildOnResponse())))
                .addHeader("Content-Type", "application/json")
        );
        Boolean isLightActivated = ruleEngineAdaptor.checkIfLightFamiliesActivated(buildLightFamilyInput()).block();
        Assert.assertNotNull(isLightActivated);
        Assert.assertTrue(isLightActivated);
    }

    @Test
    public void testCheckIfOffLightFamiliesActivated() {
        mockWebServer.enqueue(new MockResponse()
                .setBody((Objects.requireNonNull(buildOffResponse())))
                .addHeader("Content-Type", "application/json")
        );
        Boolean isLightActivated = ruleEngineAdaptor.checkIfLightFamiliesActivated(buildLightFamilyInput()).block();
        Assert.assertNotNull(isLightActivated);
        Assert.assertFalse(isLightActivated);
    }

    @Test
    public void testFindMembershipStatusValForClient() {
        mockWebServer.enqueue(new MockResponse()
                .setBody((Objects.requireNonNull(buildStatusResponse())))
                .addHeader("Content-Type", "application/json")
        );
        String status = ruleEngineAdaptor.findMembershipStatusValForClient(buildLightFamilyInput()).block();
        Assert.assertNotNull(status);
        Assert.assertEquals("NEW_STATUS", status);
    }

    @Test
    public void testFindMembershipStatusValForClientFallback() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .addHeader("Content-Type", "application/json")
        );
        String status = ruleEngineAdaptor.findMembershipStatusValForClient(buildLightFamilyInput()).block();
        Assert.assertNotNull(status);
        Assert.assertEquals("NEW", status);
    }

    @Test
    public void testgetSolicitationForClient() {
        mockWebServer.enqueue(new MockResponse()
                .setBody(buildSolicitationResp())
                .addHeader("Content-Type", "application/json")
        );
        List<String> solIds = ruleEngineAdaptor.getSolicitationForClient(buildLightFamilyInput()).collectList().block();
        Assert.assertNotNull(solIds);

    }

    private String buildSolicitationResp() {
        return "{\n" +
                "    \"result\": [\n" +
                "        {\n" +
                "            \"attributeValue\": \"101\",\n" +
                "            \"attributeName\": \"Benefit_Key\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attributeValue\": \"CyberSec-PWM-Sentrybay-v1\",\n" +
                "            \"attributeName\": \"Benefit_Subscription\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attributeValue\": \"104\",\n" +
                "            \"attributeName\": \"Benefit_Key\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attributeValue\": \"CyberSec-SBD-SentryBay-v1\",\n" +
                "            \"attributeName\": \"Benefit_Subscription\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attributeValue\": \"105\",\n" +
                "            \"attributeName\": \"Benefit_Key\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"attributeValue\": \"CyberSec-SKB-SentryBay-v1\",\n" +
                "            \"attributeName\": \"Benefit_Subscription\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"ruleName\": \"Solicitation_Id_Rule1\",\n" +
                "    \"ruleID\": 3890\n" +
                "}";
    }


}