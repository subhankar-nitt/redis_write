package com.example.redis_write.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextGet implements ApplicationContextAware {


    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext=applicationContext;
    }
    public  static  ApplicationContext getAppContext(){
        return appContext;
    }

}
