package com.example.redis_write.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@RedisHash("STUDENT_TABLE_REDIS_HASH")
public class StudentRedis {
    
    @Id
    private StudentKey  studentKey;
    private String id;
    private String name;
    private String className;
    private String rollNo;

    public StudentKey getStudentKey() {
        return studentKey;
    }

    public void setStudentKey(StudentKey studentKey) {
        this.studentKey = studentKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
}
