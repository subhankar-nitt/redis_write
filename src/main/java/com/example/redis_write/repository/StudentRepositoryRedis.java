 package com.example.redis_write.repository;


 import   org.springframework.data.repository.CrudRepository;
 import org.springframework.stereotype.Component;
 import org.springframework.stereotype.Repository;

 import com.example.redis_write.Entity.StudentKey;
 import com.example.redis_write.Entity.StudentRedis;

 @Repository
 public interface StudentRepositoryRedis extends CrudRepository<StudentRedis,StudentKey>{
    
 }
