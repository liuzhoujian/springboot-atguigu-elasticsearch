package com.lzj.elasticsearch;

import com.lzj.elasticsearch.bean.User;
import com.lzj.elasticsearch.repository.UserRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElasticsearchApplicationTests {

    @Autowired
    private JestClient jestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 使用elasticsearchTemplate添加索引
     * Indexing multiple Document(bulk index) using Elasticsearch Template
     */
    @Test
    public void addIndex03() {
        List<IndexQuery> indexQueries = new ArrayList<>();

        //first document
        User user = new User();
        user.setId(6);
        user.setName("yyyy");
        user.setAddress("北京市");
        user.setSex(0);
        IndexQuery query = new IndexQueryBuilder().withId(user.getId().toString()).withObject(user).build();
        indexQueries.add(query);

        //second document
        User user1 = new User();
        user1.setId(7);
        user1.setName("wwww");
        user1.setAddress("北京市");
        user1.setSex(0);
        IndexQuery query1 = new IndexQueryBuilder().withId(user1.getId().toString()).withObject(user1).build();
        indexQueries.add(query1);

        elasticsearchTemplate.bulkIndex(indexQueries);
    }

    /**
     * 使用elasticsearchTemplate添加索引
     * indexing a single document using Elasticsearch Template
     */
    @Test
    public void addIndex02() {
        User user = new User();
        user.setId(5);
        user.setName("xxxxxxxxx");
        user.setAddress("北京市");
        user.setSex(0);

        IndexQuery query = new IndexQueryBuilder().withId(user.getId().toString()).withObject(user).build();
       /* IndexQuery query = new IndexQuery();
        query.setIndexName("my");
        query.setType("user");
        query.setObject(user);*/
        elasticsearchTemplate.index(query);
    }

    /**
     * 使用elasticsearchTemplate查询用户
     */
    @Test
    public void queryByName(){
        List<User> users = elasticsearchTemplate.queryForList(
                new CriteriaQuery(Criteria.where("name").contains("三")), User.class);

        for (User u: users) {
            System.out.println(u);
        }
    }


    /**
     * 使用userRepository添加索引
     */
    @Test
    public void addIndex() {
        User user = new User();
        user.setId(10);
        user.setName("唐三");
        user.setAddress("北京市");
        user.setSex(1);
        //在user类中使用注解表明所处的index和type
        userRepository.save(user);
    }

    /**
     * 使用userRepository中自定义方法查询user
     */
    @Test
    public void searchUser() {
        List<User> userList = userRepository.findUserByNameLike("三");
        for (User user : userList) {
            System.out.println("模糊查询的结果:" + user);
        }

        User user = userRepository.findUserById(3);
        System.out.println("按照id查询的结果：" + user);

    }

    /**
     *userRepository 分页查询
     */
    @Test
    public void testQueryPage() {


        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                // .withQuery(QueryBuilders.matchQuery("name", "李三"))  //分词
                //.withQuery(QueryBuilders.matchPhraseQuery("name", "刘周健")) //精确匹配
                .withQuery(QueryBuilders.termQuery("name", "zhangsan")) //精确匹配
                .withPageable(PageRequest.of(0, 2))
                .build();

        Page<User> page = userRepository.search(searchQuery);
        List<User> users = page.getContent();
        for (User user : users) {
            System.out.println(user);
        }
    }


    /**
     * Jest方式：
     * 使用向索引中添加文档
     */
    @Test
    public void contextLoads() {
        //1、给ES中存储一个文档
        User user = new User();
        user.setId(1);
        user.setName("liuzhouja");
        user.setAddress("西安市");
        user.setSex(1);

        //构建一个索引功能，指定索引和类型
        Index index = new Index.Builder(user).index("my").type("user").build();

        //执行
        try {
            jestClient.execute(index);
            System.out.println("执行成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Jest方式：
     * 使用查询表达式查询文档
     */
    @Test
    public void search() {
        String queryStr = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"name\" : \"aaaa\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        Search search = new Search.Builder(queryStr).addIndex("my").addType("user").build();
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

