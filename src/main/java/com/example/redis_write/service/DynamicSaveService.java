package com.example.redis_write.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.redis_write.config.MongoConfig;
import com.example.redis_write.config.RedisConfig;
import com.mongodb.client.MongoClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.redis_write.Entity.Student;
import com.example.redis_write.Entity.StudentKey;
import com.example.redis_write.Entity.StudentRedis;
import com.example.redis_write.Entity.TimeStamp;

import javax.annotation.PostConstruct;


@Service
@Lazy(false)
public class DynamicSaveService{

    private StudentService studentService;
     private StudentServiceRedis studentServiceRedis;
     
     private static Date maxUpdateDate= new GregorianCalendar(2001,2,8,0,0).getTime();
     private static Date lastUpdateDate=null;

     private ApplicationContext applicationContext;
	 
	 @Autowired
	 private RedisTemplate<String,StudentRedis> redisTemplate;

	 @Autowired
	 private  ChannelTopic channelTopic;

	 private RedisConfig redisConfig;
	 
    @PostConstruct
    public void writeKeysToRedis(){
        applicationContext=ApplicationContextGet.getAppContext();
        studentService=applicationContext.getBean(StudentService.class);
        studentServiceRedis = applicationContext.getBean(StudentServiceRedis.class);
		redisConfig=applicationContext.getBean(RedisConfig.class);
        lastUpdateDate= studentService.getLastUpdateDate();

		redisTemplate=redisConfig.redisTemplatePubSub();
		channelTopic=redisConfig.channelTopic();
        if(maxUpdateDate.before(lastUpdateDate)) {
        	
        	System.out.println("The last Updated date is :"+maxUpdateDate.toString()+" And current  update date is :"+lastUpdateDate.toString());   
	        List<StudentRedis> studentRedisList = new ArrayList();
	        List<Student>students=studentService.getAllStudentDetatils();
	
		        for (Student student : students) {
		            
		            StudentKey studentKey=new StudentKey();
		            studentKey.setId(student.getId());
		            studentKey.setClassName(student.getClassName());
		            studentKey.setName(student.getName());
		            
		            StudentRedis studentRedis =  new StudentRedis();
		            studentRedis.setStudentKey(studentKey);
		            studentRedis.setId(student.getId());
		            studentRedis.setClassName(student.getClassName());
		            studentRedis.setName(student.getName());
		            studentRedis.setRollNo(student.getRollNo());
		
		            studentRedisList.add(studentRedis);
		        }
				assert studentRedisList!=null && studentRedisList.size()!=0;
				publish(studentRedisList.get(studentRedisList.size()-1));
		        studentServiceRedis.saveAllStudentDataToRadis(studentRedisList);
		        maxUpdateDate=lastUpdateDate;
        }

    }
	public void publish(StudentRedis studentRedis){
		redisTemplate.convertAndSend(channelTopic.getTopic(), studentRedis);
		System.out.println("publishing data to redis ....  ");
	}
}
