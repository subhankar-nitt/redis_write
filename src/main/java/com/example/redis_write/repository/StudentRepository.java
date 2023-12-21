package com.example.redis_write.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.redis_write.Entity.Student;
import com.example.redis_write.Entity.TimeStamp;

@Repository
public interface StudentRepository {

  public List<Student>findAllStudents();  
  public TimeStamp findtheLastUpdateDate();
} 
