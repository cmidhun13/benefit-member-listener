package com.szells.membership.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Riya Patel
 */
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
