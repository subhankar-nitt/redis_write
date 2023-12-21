package com.example.redis_write.config;

import java.lang.reflect.Array;

import org.springframework.data.redis.listener.*;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;

import com.example.redis_write.converter.StudentRedisKeyToByte;
import com.example.redis_write.converter.StudentRedisKeyToString;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.web.client.RestTemplate;

import com.example.redis_write.Entity.Student;
import com.example.redis_write.Entity.StudentKey;
import com.example.redis_write.Entity.StudentRedis;
import com.example.redis_write.repository.StudentRepository;
//import com.example.redis_write.repository.StudentRepositoryRedis;
import com.example.redis_write.repository.Impl.StudentRepositoryImpl;

@Configuration
@ComponentScan({"com.example.redis_write.repository","com.example.redis_write.repository"})
@EnableRedisRepositories({"com.example.redis_write.repository"})
@EnableJpaRepositories({"com.example.redis_write.repository"})
public class RedisConfig {
 
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.timeout}")
    private Duration redisTimeout;

    @Value("${spring.data.redis.max-wait}")
    private Duration maxWait;
    @Value("${spring.data.redis.max-total}")
    private int  maxTotal;
    @Value("${spring.data.redis.max-idle}")
    private int maxIdle;
    @Value("${spring.data.redis.min-idle}")
    private int minIdle;
    @Value("${redis.pubsub.topic:channel-events}")
    private String topic;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);



//        JedisClientConfiguration jedisClientConfigurationBuilder= JedisClientConfiguration.builder()
//                .usePooling()
//                .poolConfig(getGenericObjectPoolConfig())
//                .and()
//                .readTimeout(redisTimeout)
//                .useSsl()
//                .build();

        JedisConnectionFactory factory=new JedisConnectionFactory(redisStandaloneConfiguration);
//        factory.setUsePool(true);
//        factory.setUseSsl(true);
//        factory.setTimeout(Integer.parseInt(String.valueOf(redisTimeout.getSeconds())));
        return factory;
    }
    private GenericObjectPoolConfig<?>getGenericObjectPoolConfig(){
        GenericObjectPoolConfig<?> genericObjectPoolConfig=new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxWait(maxWait);
        return  genericObjectPoolConfig;
    }
    @Bean
    public RedisTemplate<StudentKey,Student> redisTemplate(){
        RedisTemplate<StudentKey,Student> redisTemplate= new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setExposeConnection(true);
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
    @Bean
    public StudentRepository studentRepository(){
        return new StudentRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisCustomConversions redisCustomConversions(){
        return  new RedisCustomConversions(Arrays.asList(new StudentRedisKeyToByte(),
                new StudentRedisKeyToString()));
    }
    @Bean
    public ChannelTopic channelTopic() {
    	return new ChannelTopic(topic);
    }
    @Bean
    public RedisTemplate<String,StudentRedis>redisTemplatePubSub(){
    	RedisTemplate<String,StudentRedis> redisTemplate=new RedisTemplate<>();
    	redisTemplate.setConnectionFactory(jedisConnectionFactory());
    	redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    	return redisTemplate;
    }

}
