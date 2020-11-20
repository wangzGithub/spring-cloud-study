package com.wangz.serviceribbon.controller;

import com.wangz.serviceribbon.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping(value = "hello")
    public String hello(@RequestParam String name) {
        return testService.testService(name);
    }
}
