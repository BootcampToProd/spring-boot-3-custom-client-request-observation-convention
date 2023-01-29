package com.bootcamptoprod.customclientrequestobservationconvention;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringBootRestTemplateMetricsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestTemplateMetricsApplication.class, args);
    }


    /**
     * Rest template.
     * In order for client metrics to be available via actuator endpoint, it is mandatory to create rest template bean using rest template builder.
     *
     * @param builder the builder
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
