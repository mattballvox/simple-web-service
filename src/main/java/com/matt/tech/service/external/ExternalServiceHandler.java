
package com.matt.tech.service.external;

import com.matt.tech.service.models.CustomerRequest;
import com.matt.tech.service.models.CustomerResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * This interface defines the general contract for any class that implements
 * logic to send customer details to a particular destination.
 *
 * 
 * @author mball
 *
 */
@EnableAsync
public interface ExternalServiceHandler {

    /**
     * Send the request to 3rd party service. 
     *
     * @param customerRequest
     * @return
     */
    @Async
    CustomerResponse sendCustomerRequest(CustomerRequest customerRequest);
    
    
}
