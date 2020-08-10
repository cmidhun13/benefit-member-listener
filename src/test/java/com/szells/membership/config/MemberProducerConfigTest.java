package com.szells.membership.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberProducerConfigTest {

    MemberProducerConfig config;

    @Before
    public void setUp() {
        config = new MemberProducerConfig();
        ReflectionTestUtils.setField(config, "historyKafkaServer", "sadjkas");
    }

    @Test
    public void test() {
        Assert.assertNotNull(config.historyKafkaTemplate());
        Assert.assertNotNull(config.historyProducerFactory());
        Assert.assertNotNull(config.membershipProducerFactory());
        Assert.assertNotNull(config.membershipTemplate());
        Assert.assertNotNull(config.poeKafkaTemplate());
        Assert.assertNotNull(config.poeProducerFactory());
        Assert.assertNotNull(config.kafkaSender());
        Assert.assertNotNull(config.memberProducerFactory());
        Assert.assertNotNull(config.memberTemplate());
    }
}