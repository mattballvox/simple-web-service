package com.matt.tech.service.controllers;

import com.matt.tech.service.models.CustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.matt.tech.service.models.CustomerResponse;
import com.matt.tech.service.utils.CustomerRequestValidator;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import com.matt.tech.service.external.ExternalServiceHandler;

/**
 * Exposes 2 REST endpoints. One to accept incoming requests to send customer
 * data to a 3rd party service and another to act as a health check for the
 * service.
 *
 * @author mball
 */
@RestController
public class RequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    private ExternalServiceHandler sendCustomerHandler;

    @Autowired
    public void setSendMessageHandler(ExternalServiceHandler sendMessageHandler) {
        this.sendCustomerHandler = sendMessageHandler;
    }

    /**
     * A simple RESTful endpoint that will accept a PUT containing the JSON
     * of the customer you want to update to a third party to store.
     * 
     * @param customerRequest
     * @return 
     */
    @RequestMapping(value = "/updateCustomer", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendCustomer(@RequestBody CustomerRequest customerRequest) {

        LOGGER.debug("Received " + customerRequest);
        
        try {
            CustomerRequestValidator.validate(customerRequest);

            CustomerResponse sendCustomerResponse = 
                    sendCustomerHandler.sendCustomerRequest(customerRequest);

            return new ResponseEntity<>(sendCustomerResponse.getResponse(),
                    sendCustomerResponse.getSuccess() ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException iae) {
            
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * A simple health check endpoint that will accept a GET request with no parameters
     * and return 200 status code.
     * 
     * @return 
     */
    @RequestMapping(value = "/healthCheck", method = RequestMethod.GET)
    public ResponseEntity<String> healthCheck() {

        LOGGER.info("HealthCheck endpoint hit at " + ZonedDateTime.now(ZoneOffset.UTC));

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
