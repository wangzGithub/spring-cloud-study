package com.wangz.servicefeign.utils;

import com.wangz.servicefeign.service.SchedualService;
import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHystrix implements SchedualService {


    @Override
    public String sayHelloFromClientOne(String name) {
        return "sorry, " + name + ", this client has error, the message send by feign hystrix";
    }
}
