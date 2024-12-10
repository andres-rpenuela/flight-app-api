package com.tokioschool.flightapp.base;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Data @Slf4j
public class BaseService3 {

    @PostConstruct
    protected void postConstruct(){
        log.info("baseService3");
    }
}
