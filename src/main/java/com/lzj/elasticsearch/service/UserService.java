package com.lzj.elasticsearch.service;

import com.lzj.elasticsearch.bean.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    long count();

    User save(User user);

    void delete(User user);

    Iterable<User> getAll();

    List<User> getByName(String name);

    Page<User> pageQuery(Integer pageNum, Integer pageSize, String q);

    Page<User> pageQueryWithHighLight(Integer pageNum, Integer pageSize, String q);
}
