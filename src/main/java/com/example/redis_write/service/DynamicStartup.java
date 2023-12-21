package com.example.redis_write.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class DynamicStartup {

    @Scheduled(fixedRate = 30000l)
    public void writeToRedis(){
        DynamicSaveService dynamicSaveService=new DynamicSaveService();
        System.out.println("Inside service ....");
        dynamicSaveService.writeKeysToRedis();
    }
    
}
