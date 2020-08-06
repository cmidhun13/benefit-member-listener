package com.szells.membership.config;

import com.szells.membership.domain.payload.MembershipPayload;
import com.szells.membership.domain.request.MemberSubscriptionHistory;
import com.szells.membership.domain.request.ProofOfEnrollment;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MemberProducerConfig {
    @Value("${member.kafka.history.bootstrap-server}")
    private String historyKafkaServer;

    private Map<String, Object> props() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                historyKafkaServer);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, MemberSubscriptionHistory> historyProducerFactory() {
        return new DefaultKafkaProducerFactory<>(props());
    }

    @Bean
    public KafkaTemplate<String, MemberSubscriptionHistory> historyKafkaTemplate() {
        return new KafkaTemplate<>(historyProducerFactory());
    }

    @Bean
    public ProducerFactory<String, ProofOfEnrollment> poeProducerFactory() {
        return new DefaultKafkaProducerFactory<>(props());
    }

    @Bean
    public KafkaTemplate<String, ProofOfEnrollment> poeKafkaTemplate() {
        return new KafkaTemplate<>(poeProducerFactory());
    }

    @Bean
    public ProducerFactory<String, MembershipPayload> membershipProducerFactory() {
        return new DefaultKafkaProducerFactory<>(props());
    }

    @Bean
    public KafkaTemplate<String, MembershipPayload> membershipTemplate() {
        return new KafkaTemplate<>(membershipProducerFactory());
    }

    @Bean
    public ProducerFactory<String, String> memberProducerFactory() {
        return new DefaultKafkaProducerFactory<>(props());
    }

    @Bean
    public KafkaTemplate<String, String> memberTemplate() {
        return new KafkaTemplate<>(memberProducerFactory());
    }

    @Bean
    public KafkaSender<String, String> kafkaSender() {
        return KafkaSender.create(SenderOptions.<String, String>create(props()).maxInFlight(1024));
    }

}
