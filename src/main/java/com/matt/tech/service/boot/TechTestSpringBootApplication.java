package com.matt.tech.service.boot;

import com.matt.tech.service.models.CustomerRequest;
import java.util.Arrays;
import java.util.HashMap;
import javax.xml.bind.Marshaller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

/**
 *
 * Main class that will kick off the application.
 *
 * @author mball
 */
@SpringBootApplication(scanBasePackages = "com.matt.tech.service")
public class TechTestSpringBootApplication{

    public static void main(String[] args) throws Exception {

        SpringApplication.run(TechTestSpringBootApplication.class, args);
    }
    
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setMarshallerProperties(new HashMap<String, Object>() {{
          put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        }});
        
        marshaller.setClassesToBeBound(CustomerRequest.class);
        
        return marshaller;
    }
    
    @Bean
    public RestTemplate restTemplate() {
        Jaxb2RootElementHttpMessageConverter xmlConverter = new Jaxb2RootElementHttpMessageConverter();
        MappingJackson2HttpMessageConverter  jsonConverter = new MappingJackson2HttpMessageConverter();
        
        RestTemplate restTemplate = new RestTemplate();
        
        restTemplate.setMessageConverters(Arrays.asList(xmlConverter, jsonConverter));
        
        return restTemplate;
    }
}
