package com.wangz.serviceribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestService {


    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "errorReturn")
    public String testService(String name) {
        return restTemplate.getForObject("http://EUREKA-CLIENT/hello?name=" + name, String.class);
    }

    public String errorReturn(String name) {
        return "sorry, " + name + " ,there is a error message!";
    }
}
