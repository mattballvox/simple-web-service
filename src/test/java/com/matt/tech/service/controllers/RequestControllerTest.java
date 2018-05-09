package com.matt.tech.service.controllers;

import com.matt.tech.service.models.CustomerRequest;
import com.matt.tech.service.external.SimpleExternalServiceHandler;
import com.matt.tech.service.models.CustomerResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author mball
 */
public class RequestControllerTest {

    private static final int LEVEL = 50;
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";

    public RequestControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Test of healthCheck method, of class RequestController
     */
    @Test
    public void testHealthCheck() {
        ResponseEntity<String> expResult = new ResponseEntity<>(null, HttpStatus.OK);
        
        RequestController instance = new RequestController();
        
        ResponseEntity<String> actResult = instance.healthCheck();
        
        assertEquals(expResult, actResult);
    }

    /**
     * Test of sendCustomer method, of class RequestController.
     */
    @Test
    public void testSendCustomerValidRequest() {
        System.out.println("testSendCustomerValidRequest");
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAccountVerified(true);
        customerRequest.setName(NAME);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setUsername(USERNAME);
        customerRequest.setVipLevel(LEVEL);

        RequestController instance = new RequestController();

        SimpleExternalServiceHandler mockSimpleExternalServiceHandler = mock(SimpleExternalServiceHandler.class);

        CustomerResponse sendCustomerResponse = createCustomerResponse("Success", true);

        when(mockSimpleExternalServiceHandler.sendCustomerRequest(customerRequest))
                .thenReturn(sendCustomerResponse);

        instance.setSendMessageHandler(mockSimpleExternalServiceHandler);

        ResponseEntity<String> expResult = new ResponseEntity<>("Success", HttpStatus.CREATED);
        ResponseEntity<String> result = instance.sendCustomer(customerRequest);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of sendCustomer method, of class RequestController.
     */
    @Test
    public void testSendCustomerValidRequestAccountVerifiedNotSet() {
        System.out.println("testSendCustomerValidRequestAccountVerifiedNotSet");
        CustomerRequest customerRequest = new CustomerRequest();        
        customerRequest.setName(NAME);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setUsername(USERNAME);
        customerRequest.setVipLevel(LEVEL);

        RequestController instance = new RequestController();

        SimpleExternalServiceHandler mockSimpleExternalServiceHandler = mock(SimpleExternalServiceHandler.class);

        CustomerResponse sendCustomerResponse = createCustomerResponse("Success", true);

        when(mockSimpleExternalServiceHandler.sendCustomerRequest(customerRequest))
                .thenReturn(sendCustomerResponse);

        instance.setSendMessageHandler(mockSimpleExternalServiceHandler);

        ResponseEntity<String> expResult = new ResponseEntity<>("Success", HttpStatus.CREATED);
        ResponseEntity<String> result = instance.sendCustomer(customerRequest);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of sendCustomer method, of class RequestController.
     */
    @Test
    public void testSendCustomerValidRequestVipLevelNotSet() {
        System.out.println("testSendCustomerValidRequestVipLevelNotSet");
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAccountVerified(true);
        customerRequest.setName(NAME);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setUsername(USERNAME);        

        RequestController instance = new RequestController();

        SimpleExternalServiceHandler mockSimpleExternalServiceHandler = mock(SimpleExternalServiceHandler.class);

        CustomerResponse sendCustomerResponse = createCustomerResponse("Success", true);

        when(mockSimpleExternalServiceHandler.sendCustomerRequest(customerRequest))
                .thenReturn(sendCustomerResponse);

        instance.setSendMessageHandler(mockSimpleExternalServiceHandler);

        ResponseEntity<String> expResult = new ResponseEntity<>("Success", HttpStatus.CREATED);
        ResponseEntity<String> result = instance.sendCustomer(customerRequest);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of sendCustomer method, of class RequestController.
     */
    @Test
    public void testSendCustomerValidRequestNameIsNull() {
        System.out.println("testSendCustomerValidRequestNameIsNull");
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAccountVerified(true);
        customerRequest.setName(null);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setUsername(USERNAME);
        customerRequest.setVipLevel(LEVEL);

        RequestController instance = new RequestController();

        SimpleExternalServiceHandler mockSimpleExternalServiceHandler = mock(SimpleExternalServiceHandler.class);

        CustomerResponse sendCustomerResponse = createCustomerResponse("Success", true);

        when(mockSimpleExternalServiceHandler.sendCustomerRequest(customerRequest))
                .thenReturn(sendCustomerResponse);

        instance.setSendMessageHandler(mockSimpleExternalServiceHandler);

        ResponseEntity<String> expResult = new ResponseEntity<>("Success", HttpStatus.CREATED);
        ResponseEntity<String> result = instance.sendCustomer(customerRequest);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of sendCustomer method, of class RequestController.
     */
    @Test
    public void testSendCustomerRequestNull() {
        System.out.println("testSendCustomerRequestNull");

        RequestController instance = new RequestController();

        SimpleExternalServiceHandler mockSimpleExternalServiceHandler = mock(SimpleExternalServiceHandler.class);

        instance.setSendMessageHandler(mockSimpleExternalServiceHandler);

        ResponseEntity<String> expResult = new ResponseEntity<>(
                "customer request was null", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = instance.sendCustomer(null);
        assertEquals(expResult, result);
    }

    /**
     * Test of sendCustomer method, of class RequestController.
     */
    @Test
    public void testSendCustomerInvalidRequestUsernameIsNull() {
        System.out.println("testSendCustomerInvalidRequestUsernameIsNull");
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAccountVerified(true);
        customerRequest.setUsername(null);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setName(NAME);
        customerRequest.setVipLevel(LEVEL);

        RequestController instance = new RequestController();

        SimpleExternalServiceHandler mockSimpleExternalServiceHandler = mock(SimpleExternalServiceHandler.class);

        when(mockSimpleExternalServiceHandler.sendCustomerRequest(customerRequest)).thenReturn(null);

        instance.setSendMessageHandler(mockSimpleExternalServiceHandler);

        ResponseEntity<String> expResult = new ResponseEntity<>(
                "username was null", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = instance.sendCustomer(customerRequest);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of sendCustomer method, of class RequestController.
     */
    @Test
    public void testSendCustomerInvalidRequestPasswordIsNull() {
        System.out.println("testSendCustomerInvalidRequestPasswordIsNull");
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAccountVerified(true);
        customerRequest.setUsername(USERNAME);
        customerRequest.setPassword(null);
        customerRequest.setName(NAME);
        customerRequest.setVipLevel(LEVEL);

        RequestController instance = new RequestController();

        SimpleExternalServiceHandler mockSimpleExternalServiceHandler = mock(SimpleExternalServiceHandler.class);

        when(mockSimpleExternalServiceHandler.sendCustomerRequest(customerRequest)).thenReturn(null);

        instance.setSendMessageHandler(mockSimpleExternalServiceHandler);

        ResponseEntity<String> expResult = new ResponseEntity<>(
                "password was null", HttpStatus.BAD_REQUEST);
        ResponseEntity<String> result = instance.sendCustomer(customerRequest);
        assertEquals(expResult, result);
    }

    private CustomerResponse createCustomerResponse(String response, boolean success) {
        CustomerResponse sendCustomerResponse = new CustomerResponse();
        sendCustomerResponse.setResponse(response);
        sendCustomerResponse.setSuccess(success);

        return sendCustomerResponse;
    }
}
