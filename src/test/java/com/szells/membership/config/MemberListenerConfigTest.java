package com.szells.membership.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

@RunWith(SpringRunner.class)
public class MemberListenerConfigTest {
    private MemberListenerConfig memberListenerConfig;

    @Before
    public void setup() {
        this.memberListenerConfig = new MemberListenerConfig();
        ReflectionTestUtils.setField(memberListenerConfig, "bootstrapAddress", "localhost:9092");
        ReflectionTestUtils.setField(memberListenerConfig, "consumerGroupId", "testGroup");
    }

    @Test
    public void testDeleteListenerConfig() {
        Assert.assertNotNull("Listener config init failed!", memberListenerConfig);
        Map<String, Object> configs = memberListenerConfig.consumerConfigs();
        Assert.assertNotNull("Consumer configs init failed!", configs);
        Assert.assertEquals("Consumer configs failed!", 3, configs.size());
        Assert.assertEquals("Consumer configs set bootstrap address failed!",
                "localhost:9092", configs.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));

        ConsumerFactory<String, String> consumerFactory = memberListenerConfig.deleteConsumerFactory();
        Assert.assertNotNull("consumerFactory init failed!", consumerFactory);
        ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory =
                memberListenerConfig.deleteListenerFactory();

        Assert.assertNotNull("listener factory init failed!", listenerFactory);


    }

    @Test
    public void testUpdateListenerConfig() {
        Map<String, Object> configs = memberListenerConfig.consumerConfigs();
        Assert.assertNotNull("Consumer configs init failed!", configs);
        Assert.assertEquals("Consumer configs set bootstrap address failed!",
                "localhost:9092", configs.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));

        ConsumerFactory<String, String> consumerFactory = memberListenerConfig.updateMemberConsumerFactory();
        Assert.assertNotNull("consumerFactory init failed!", consumerFactory);
        ConcurrentKafkaListenerContainerFactory<String, String> listenerFactory =
                memberListenerConfig.updateMemberListenerFactory();

        Assert.assertNotNull("listener factory init failed!", listenerFactory);


    }
}
