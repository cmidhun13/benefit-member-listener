package com.szells.membership.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
public class MemberListenerConfig {

    @Value(value = "${member.kafka.bootstrap-server}")
    private String bootstrapAddress;

    @Value(value = "${member.kafka.consumer-groupId}")
    private String consumerGroupId;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return config;
    }

    @Bean
    public ConsumerFactory<String, String> updateMemberConsumerFactory() {
        Map<String, Object> config = consumerConfigs();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "updateMembers");
        return new DefaultKafkaConsumerFactory<>(config,
                new StringDeserializer(),
                new JsonDeserializer<>(String.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> updateMemberListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(updateMemberConsumerFactory());
        return listenerFactory;
    }

    @Bean
    public ConsumerFactory<String, String> deleteConsumerFactory() {
        Map<String, Object> config = consumerConfigs();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "deleteConsumers");
        return new DefaultKafkaConsumerFactory<>(config,
                new StringDeserializer(),
                new JsonDeserializer<>(String.class));
    }

    @Bean
    public ConsumerFactory<String, String> activateMemberFactory() {
        Map<String, Object> config = consumerConfigs();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "activateMembers");
        return new DefaultKafkaConsumerFactory<>(config,
                new StringDeserializer(),
                new JsonDeserializer<>(String.class));
    }

    @Bean
    public ConsumerFactory<String, String> bulkUploadMemberFactory() {
        Map<String, Object> config = consumerConfigs();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "bulkUploadMember");
        return new DefaultKafkaConsumerFactory<>(config,
                new StringDeserializer(),
                new JsonDeserializer<>(String.class));
    }

    @Bean
    public ConsumerFactory<String, String> suspendMemberFactory() {
        Map<String, Object> config = consumerConfigs();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "suspendMembers");
        return new DefaultKafkaConsumerFactory<>(config,
                new StringDeserializer(),
                new JsonDeserializer<>(String.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> activateMemberListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(activateMemberFactory());
        return listenerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> suspendMemberListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(suspendMemberFactory());
        return listenerFactory;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> bulkUploadMemberListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(bulkUploadMemberFactory());
        return listenerFactory;
    }

    @Bean
    public ConsumerFactory<String, String> createMemberFactory() {
        Map<String, Object> config = consumerConfigs();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "createMembers");
        return new DefaultKafkaConsumerFactory<>(config,
                new StringDeserializer(),
                new JsonDeserializer<>(String.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> createMemberListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(createMemberFactory());
        return listenerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> deleteListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerFactory.setConsumerFactory(deleteConsumerFactory());
        return listenerFactory;
    }


}
