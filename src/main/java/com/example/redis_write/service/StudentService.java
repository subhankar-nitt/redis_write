package com.example.redis_write.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.redis_write.Entity.Student;
import com.example.redis_write.repository.StudentRepository;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    public java.util.List<Student> getAllStudentDetatils(){
        return studentRepository.findAllStudents();
    }
    
    public Date getLastUpdateDate() {
    	return studentRepository.findtheLastUpdateDate().getDate();
    }
}
