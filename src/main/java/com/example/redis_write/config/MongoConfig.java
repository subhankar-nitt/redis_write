package com.example.redis_write.config;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.redis_write.Entity.Student;
import com.example.redis_write.Entity.TimeStamp;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
public class MongoConfig {

    @Value("${spring.mongodb.userName}")
    private String user;

    @Value("${spring.mongodb.password}")
    private String password;

    // private String connectionString = "mongodb+srv://"+user+":"+password+"@cluster0.uni5bpo.mongodb.net/?retryWrites=true&w=majority";
    
    @Value("${spring.mongodb.datebase}")
    private String dbName;

    @Value("${spring.mongodb.collection}")
    private String tableName;

    @Value("${spring.data.mongodb.database}")
    private String timeDb;
    
    @Value("${spring.data.mongodb.collection}")
    private String lastUpdateCollection;
    
    @Bean
    public MongoClient mongoClient(){
        final String connectionString = "mongodb+srv://"+user+":"+password+"@cluster0.uni5bpo.mongodb.net/?retryWrites=true&w=majority";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MongoClient mongoClient= MongoClients.create(settings);
        return mongoClient;
    }
    @Bean
    public MongoCollection<Student> getCollection(){
        MongoClient mongoClient= mongoClient();
        CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mongoDatabase= mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
        MongoCollection<Student> mongoCollection= mongoDatabase.getCollection(tableName,Student.class);
//        mongoClient.close();
        return mongoCollection;

    }
    
    @Bean
    public MongoCollection<TimeStamp> getTimeStamp(){
    	MongoClient mongoClient= mongoClient();
        CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mongoDatabase= mongoClient.getDatabase(timeDb).withCodecRegistry(pojoCodecRegistry);
        MongoCollection<TimeStamp> mongoCollection= mongoDatabase.getCollection(lastUpdateCollection,TimeStamp.class);
        return mongoCollection;
    }

    
}
