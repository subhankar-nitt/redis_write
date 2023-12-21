package com.example.redis_write.redis_write;

import com.example.redis_write.repository.StudentRepositoryRedis;
import com.example.redis_write.service.DynamicSaveService;
import com.example.redis_write.service.DynamicStartup;
import com.example.redis_write.service.StudentServiceRedis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.redis_write.config.MongoConfig;
import com.example.redis_write.config.RedisConfig;
import com.example.redis_write.repository.StudentRepository;
// import com.example.redis_write.repository.StudentRepositoryRedis;
import com.example.redis_write.service.StudentService;
// import com.example.redis_write.service.StudentServiceRedis;

@EnableMongoRepositories(basePackageClasses = {MongoConfig.class})
//@EnableJpaRepositories(basePackageClasses = {StudentRepositoryRedis.class,StudentRepository.class})
@ComponentScan(basePackageClasses={MongoConfig.class,RedisConfig.class,StudentRepository.class,StudentService.class, DynamicSaveService.class, DynamicStartup.class,StudentRepositoryRedis.class, StudentServiceRedis.class})
 
public class RedisWriteApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisWriteApplication.class, args);
	}

}
