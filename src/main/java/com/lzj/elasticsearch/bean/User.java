package com.lzj.elasticsearch.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * userRepository操作的bean
 */
@Data
@Document(indexName = "lzj", type = "user")
public class User {
    @Id
    private Integer id;
    private String name;

    @Field(index=false)
    private String address;
    private Integer sex;
}

/**
 *
 * Jest操作的bean
     @Data
     public class User {
     @JestId
     private Integer id;
     private String name;
     private String address;
     private Integer sex;
     }

 */