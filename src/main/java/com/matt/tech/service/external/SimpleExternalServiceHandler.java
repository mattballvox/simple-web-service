package com.matt.tech.service.external;

import com.matt.tech.service.models.CustomerRequest;
import com.matt.tech.service.models.CustomerResponse;
import java.io.StringWriter;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author mball
 */
@Component
public class SimpleExternalServiceHandler implements ExternalServiceHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleExternalServiceHandler.class);

    @Value("${third.party.service.url}")
    private String thirdPartyServiceURL;

    private RestTemplate restTemplate;
    
    private Jaxb2Marshaller marshaller;
    
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Autowired
    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    @Override
    public CustomerResponse sendCustomerRequest(CustomerRequest sendCustomerRequest) {

        HttpEntity<String> request = createRequest(sendCustomerRequest);
        LOGGER.debug("Sending request " + request + " to third party service");
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(thirdPartyServiceURL,
                    request, String.class);

            LOGGER.debug("Received " + response + " from third party service");
            return buildResponse(response.getStatusCode(), response.getBody());

        } catch (RestClientException rce) {
            return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, rce.getMostSpecificCause().getMessage());
        }
    }

    private HttpEntity<String> createRequest(CustomerRequest customerRequest) {
        
        StringWriter sw = new StringWriter();
        
        marshaller.marshal(customerRequest, new StreamResult(sw));
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);

        return new HttpEntity<>(sw.toString(), httpHeaders);
    }

    private CustomerResponse buildResponse(HttpStatus httpStatus, String response) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setSuccess(httpStatus.equals(HttpStatus.CREATED));
        customerResponse.setResponse(response);

        LOGGER.debug("Sending back " + customerResponse + " to RequestController");
        return customerResponse;
    }
}
