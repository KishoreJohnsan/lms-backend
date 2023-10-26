package com.lms.authservice.config;

import com.lms.authservice.client.CourseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@ComponentScan(basePackages = {"com.lms.authservice"})
@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;
    @Bean
    public WebClient courseWebClient(){
        return WebClient.builder()
                .baseUrl("lb://course")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public CourseClient courseClient(){
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(courseWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(CourseClient.class);
    }


}
