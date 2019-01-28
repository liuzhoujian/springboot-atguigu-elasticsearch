package com.lzj.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 操作elasticsearch有两种方式：
 * (1)jest:默认不生效，需要导入包io.searchbox.jest
 *    配置application.properties,测试添加文档和查询文档
 * (2)spring-data-es:导入spring-data-elasticsearch包
 *    配置application.properties:cluster-name  cluster-nodes
 *    启动要是报错：可能是版本不匹配
 *    两种用法：
 *    （1）编写接口继承elasticsearchRepository
 *     (2) elasticsearchTemplate
 * (3)spring-data-es CRUD + 分页 + 高亮的练习
 *
 */
@SpringBootApplication
public class SpringbootElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootElasticsearchApplication.class, args);
    }

}

