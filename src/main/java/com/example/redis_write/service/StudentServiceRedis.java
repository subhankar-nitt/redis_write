 package com.example.redis_write.service;

 import java.util.List;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import com.example.redis_write.Entity.StudentRedis;
 import com.example.redis_write.repository.StudentRepositoryRedis;

 @Service
 public class StudentServiceRedis {

     @Autowired
     private StudentRepositoryRedis studentRepository;

     public void saveAllStudentDataToRadis(List<StudentRedis> list){
         studentRepository.saveAll(list);
     }
 }
