package com.matt.tech.service.utils;

import com.matt.tech.service.models.CustomerRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mball
 */
public class CustomerRequestValidatorTest {

    private static final int VIPLEVEL = 50;
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final boolean ACCOUNT_VERIFIED = true;
    private static final boolean ACCOUNT_NOT_VERIFIED = false;

    public CustomerRequestValidatorTest() {
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
     * Test of validate method, of class CustomerRequestValidator.
     */
    @Test
    public void testValidateValidRequest() {
        System.out.println("testValidateValidRequest");
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAccountVerified(ACCOUNT_VERIFIED);
        customerRequest.setName(NAME);
        customerRequest.setUsername(USERNAME);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setVipLevel(VIPLEVEL);
        CustomerRequestValidator.validate(customerRequest);
    }
    
    /**
     * Test of validate method, of class CustomerRequestValidator.
     */
    @Test
    public void testValidateValidRequestNoName() {
        System.out.println("testValidateValidRequestNoName");
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAccountVerified(ACCOUNT_VERIFIED);
        customerRequest.setName(null);
        customerRequest.setUsername(USERNAME);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setVipLevel(VIPLEVEL);
        CustomerRequestValidator.validate(customerRequest);
    }
    
    /**
     * Test of validate method, of class CustomerRequestValidator.
     */
    @Test
    public void testValidateValidRequestNoVipLevel() {
        System.out.println("testValidateValidRequestNoVipLevel");
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAccountVerified(ACCOUNT_VERIFIED);
        customerRequest.setName(NAME);
        customerRequest.setUsername(USERNAME);
        customerRequest.setPassword(PASSWORD);
        CustomerRequestValidator.validate(customerRequest);
    }
    
    /**
     * Test of validate method, of class CustomerRequestValidator.
     */
    @Test
    public void testValidateValidRequestNoAccountVerified() {
        System.out.println("testValidateValidRequestNoAccountVerified");
        CustomerRequest customerRequest = new CustomerRequest();        
        customerRequest.setName(NAME);
        customerRequest.setUsername(USERNAME);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setVipLevel(VIPLEVEL);
        CustomerRequestValidator.validate(customerRequest);
    }
    
    /**
     * Test of validate method, of class CustomerRequestValidator.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateInvalidRequestNoPassword() {
        System.out.println("testValidateInvalidRequestNoPassword");
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAccountVerified(ACCOUNT_VERIFIED);
        customerRequest.setName(NAME);
        customerRequest.setUsername(USERNAME);
        customerRequest.setPassword(null);
        customerRequest.setVipLevel(VIPLEVEL);
        CustomerRequestValidator.validate(customerRequest);
    }
    
    /**
     * Test of validate method, of class CustomerRequestValidator.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateInvalidRequestNoUsername() {
        System.out.println("testValidateInvalidRequestNoUsername");
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAccountVerified(ACCOUNT_VERIFIED);
        customerRequest.setName(NAME);
        customerRequest.setUsername(null);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setVipLevel(VIPLEVEL);
        CustomerRequestValidator.validate(customerRequest);
    }
    
    /**
     * Test of validate method, of class CustomerRequestValidator.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateInvalidRequestNoRequest() {
        System.out.println("testValidateInvalidRequestNoRequest");
        CustomerRequestValidator.validate(null);
    }

}
