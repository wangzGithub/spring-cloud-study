package com.wangz.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String appName;


    @RequestMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return "Hello, the name you press is: " + name + ", this message from :" + appName + ", port is: " + port;
    }
}
