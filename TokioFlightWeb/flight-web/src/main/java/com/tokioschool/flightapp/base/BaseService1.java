package com.tokioschool.flightapp.base;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@DependsOn({"baseService3"})
public class BaseService1 {

    // ID por autowired
    //@Autowired
    //private BaseService2 baseService2;

    // ID por setter
    /*
    private BaseService2 baseService2;

    public BaseService1(BaseService2 baseService2) {
        this.baseService2 = baseService2;
    }
     */
    // ID por constructor
    private final BaseService2 baseService2;

    /**
    // @Autowired // Optional
    public BaseService1(BaseService2 baseService2) {
        this.baseService2 = baseService2;
    }
     */

    @PostConstruct
    protected void postConstruct() {
        // cuando se crea el bean
        log.info("baseService, {}", baseService2.say());
    }

    public String say(){
        return "Hello from service 1";
    }
}
