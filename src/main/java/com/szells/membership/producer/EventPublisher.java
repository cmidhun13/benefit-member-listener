package com.szells.membership.producer;

import com.szells.membership.domain.payload.BasePayload;
import com.szells.membership.domain.request.MemberSubscriptionHistory;
import com.szells.membership.domain.request.ProofOfEnrollment;
import com.szells.membership.util.MembershipUtil;
import com.szells.util.domain.ServiceParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublisher implements IEventPublisher {

    private final KafkaTemplate<String, MemberSubscriptionHistory> historyKafkaTemplate;
    private final KafkaTemplate<String, ProofOfEnrollment> poeKafkaTemplate;
    private final KafkaSender<String, String> kafkaSender;
    @Value("${member.kafka.history.topicName}")
    private String historyTopic;
    @Value("${member.kafka.poe.topicName}")
    private String poeTopic;

    public void sendMessage(ServiceParameters<MemberSubscriptionHistory> params) {
        MemberSubscriptionHistory payload = params.getPayload().get();
        historyKafkaTemplate.send(historyTopic, payload);
    }

    public void sendMessage(List<ProofOfEnrollment> poe) {
        poe.forEach(payload -> poeKafkaTemplate.send(poeTopic, payload));
    }

    public Mono<Boolean> sendEvent(ServiceParameters<? extends BasePayload> payload, String topicName, String eventType, String event) {
        String correlationId = payload.getHeaders().getCorrelationId();
        AtomicReference<Mono<Boolean>> successResult = new AtomicReference<>(Mono.just(false));
        MembershipUtil.generatePayloadJson(payload.getPayload().get())
                .ifPresent(payloadJson ->
                        successResult.set(publishEvent(payloadJson, topicName,
                                correlationId, eventType, event))); // if Payload to JSON is transformed successfully

        return successResult.get();
    }

    private Mono<Boolean> publishEvent(String payloadJson, String topic, String correlationId,
                                       String eventType, String event) {

        ProducerRecord<String, String> message = new ProducerRecord<>(topic, null,
                UUID.randomUUID().toString(), payloadJson,
                Collections.singletonList(new RecordHeader(eventType, event.getBytes()))
        );
        //kafkaTemplate.send("member.create1",payloadJson);
        return kafkaSender.send(Mono.just(SenderRecord.create(message, correlationId)))
                .collectList()
                .flatMap(result -> Mono.just(true))
                .onErrorReturn(false);
    }

}