package com.example.redis_write.converter;

import com.example.redis_write.Entity.StudentKey;
import org.springframework.core.convert.converter.Converter;

public class StudentRedisKeyToString implements Converter<StudentKey,String> {
    @Override
    public String convert(StudentKey source) {
        String key = source.getId()+":"+source.getName()+":"+source.getClassName();
        return key;
    }
}
