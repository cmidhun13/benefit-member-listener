package com.szells.membership.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MembershipConfigTest {
    private MembershipConfig config;

    @Before
    public void setUp() throws Exception {
        config = new MembershipConfig();
    }

    @Test
    public void test() {
        Assert.assertNotNull(config.poeThreadExecutor());
    }
}