package com.matt.tech.service.external;

import com.matt.tech.service.models.CustomerRequest;
import com.matt.tech.service.models.CustomerResponse;
import java.io.StringWriter;
import java.util.HashMap;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author mball
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SimpleExternalServiceHandlerTest {

    private static final String THIRD_PARTY_URL = "some.url";
    private static final String EXPECTED_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<customerDetails>\n"
            + "    <customerVerified>true</customerVerified>\n"
            + "    <customerName>name</customerName>\n"
            + "    <customerPassword>password</customerPassword>\n"
            + "    <customerUsername>username</customerUsername>\n"
            + "    <customerLevel>50</customerLevel>\n"
            + "</customerDetails>\n";

    private static final int CUSTOMER_LEVEL = 50;
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";

    @Autowired
    private SimpleExternalServiceHandler simpleSendCustomerHandler;

    @Autowired
    private Jaxb2Marshaller marshaller;

    public SimpleExternalServiceHandlerTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    /**
     * Test to ensure that the body of the request sent via the
     * RestTemplate contains correct XML
     */
    @Test
    public void testRestTempleteExecutionParameter() {
        System.out.println("testRestTempleteExecution");

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setVipLevel(CUSTOMER_LEVEL);
        customerRequest.setName(NAME);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setUsername(USERNAME);
        customerRequest.setAccountVerified(true);

        RestTemplate mockRestTemplate = mock(RestTemplate.class);

        when(mockRestTemplate.postForEntity(THIRD_PARTY_URL, createRequest(customerRequest), String.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED));

        simpleSendCustomerHandler.setRestTemplate(mockRestTemplate);

        CustomerResponse result = simpleSendCustomerHandler.sendCustomerRequest(customerRequest);

        ArgumentCaptor<HttpEntity<String>> captor = ArgumentCaptor.forClass(HttpEntity.class);

        verify(mockRestTemplate, times(1)).postForEntity(eq(THIRD_PARTY_URL), captor.capture(), eq(String.class));

        // Assert the argument
        HttpEntity<String> request = captor.getValue();
                
        assertEquals(EXPECTED_XML, request.getBody());

    }

    /**
     * Test of sendCustomerRequest method, of class SimpleSendCustomerHandler.
     * Testing with all the correct parameters and should result in a successful
     * response being sent back to the initial request.
     */
    @Test
    public void testSendCustomerRequest() {
        System.out.println("sendCustomerRequest");

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setVipLevel(CUSTOMER_LEVEL);
        customerRequest.setName(NAME);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setUsername(USERNAME);
        customerRequest.setAccountVerified(true);

        RestTemplate mockRestTemplate = mock(RestTemplate.class);

        when(mockRestTemplate.postForEntity(THIRD_PARTY_URL, createRequest(customerRequest), String.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED));

        simpleSendCustomerHandler.setRestTemplate(mockRestTemplate);

        CustomerResponse expResult = new CustomerResponse();
        expResult.setResponse(HttpStatus.CREATED.getReasonPhrase());
        expResult.setSuccess(true);

        CustomerResponse result = simpleSendCustomerHandler.sendCustomerRequest(customerRequest);
        assertEquals(expResult, result);
    }

    /**
     * Test of sendCustomerRequest method, of class SimpleSendCustomerHandler.
     * Testing with a failure at the third party service side. It should result
     * in an unsuccessful response being sent back to the initial request.
     */
    @Test
    public void testSendCustomerRequestThirdPartyServiceError() {
        System.out.println("testSendCustomerRequestThirdPartyServiceError");

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setVipLevel(CUSTOMER_LEVEL);
        customerRequest.setName(NAME);
        customerRequest.setPassword(PASSWORD);
        customerRequest.setUsername(USERNAME);
        customerRequest.setAccountVerified(true);

        RestTemplate mockRestTemplate = mock(RestTemplate.class);

        when(mockRestTemplate.postForEntity(THIRD_PARTY_URL, createRequest(customerRequest), String.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        HttpStatus.INTERNAL_SERVER_ERROR));

        simpleSendCustomerHandler.setRestTemplate(mockRestTemplate);

        CustomerResponse expResult = new CustomerResponse();
        expResult.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        expResult.setSuccess(false);

        CustomerResponse result = simpleSendCustomerHandler.sendCustomerRequest(customerRequest);
        assertEquals(expResult, result);
    }

    private HttpEntity<String> createRequest(CustomerRequest sendCustomerRequest) {
        StringWriter sw = new StringWriter();

        marshaller.marshal(sendCustomerRequest, new StreamResult(sw));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);

        return new HttpEntity<>(sw.toString(), httpHeaders);
    }

    @Configuration
    static class ContextConfiguration {

        @Bean
        public ExternalServiceHandler sendCustomerHandler() {
            System.setProperty("third.party.service.url", THIRD_PARTY_URL);
            SimpleExternalServiceHandler simpleSendCustomerHandler = new SimpleExternalServiceHandler();
            return simpleSendCustomerHandler;
        }

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Bean
        public Jaxb2Marshaller marshaller() {
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
            marshaller.setMarshallerProperties(new HashMap<String, Object>() {
                {
                    put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                }
            });

            marshaller.setClassesToBeBound(CustomerRequest.class);

            return marshaller;
        }

    }
}
