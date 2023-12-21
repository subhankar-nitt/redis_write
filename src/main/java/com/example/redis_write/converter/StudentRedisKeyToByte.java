package com.example.redis_write.converter;

import com.example.redis_write.Entity.StudentKey;

import org.bson.json.StrictJsonWriter;
import org.springframework.core.convert.converter.Converter;

public class StudentRedisKeyToByte implements Converter<StudentKey,byte[]> {

    @Override
    public byte[] convert(StudentKey source) {
        String key = source.getId()+":"+source.getName()+":"+source.getClassName();
        return  key.getBytes();
    }
}
