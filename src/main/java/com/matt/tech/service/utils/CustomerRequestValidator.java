package com.matt.tech.service.utils;

import com.matt.tech.service.models.CustomerRequest;
import org.springframework.util.Assert;

/**
 *
 * @author mball
 */
public class CustomerRequestValidator {

    /**
     * A static method that will contain any logic that is required
     * to valid an incoming request before we attempt to send it the 
     * third party service.
     * 
     * @param customerRequest 
     */
    public static void validate(CustomerRequest customerRequest) {

        Assert.notNull(customerRequest, "customer request was null");
        Assert.notNull(customerRequest.getUsername(), "username was null");
        Assert.notNull(customerRequest.getPassword(), "password was null");
    }
}
