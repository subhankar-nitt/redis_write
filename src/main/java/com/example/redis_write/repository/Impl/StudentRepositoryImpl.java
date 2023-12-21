package com.example.redis_write.repository.Impl;



import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.redis_write.Entity.Student;
import com.example.redis_write.Entity.TimeStamp;
import com.example.redis_write.repository.StudentRepository;
import com.mongodb.client.MongoCollection;

public class StudentRepositoryImpl implements  StudentRepository {

    @Autowired
    MongoCollection<Student>mongoCollection;
    
    @Autowired
    MongoCollection<TimeStamp> lastUpdateCollection;

    @Override
    public List<Student> findAllStudents() {
        List<Student> students= mongoCollection.find(new Document(),Student.class).into(new ArrayList<>());
        return students;
        
    }

	@Override
	public TimeStamp findtheLastUpdateDate() {
		List<TimeStamp> timeStamp=lastUpdateCollection.find(new Document(),TimeStamp.class).into(new ArrayList<>());
		
		return timeStamp!=null && timeStamp.size()>0?timeStamp.get(0):null;
	}
    
}