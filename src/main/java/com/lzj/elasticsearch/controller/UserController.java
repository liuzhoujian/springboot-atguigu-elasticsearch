package com.lzj.elasticsearch.controller;

import com.lzj.elasticsearch.bean.User;
import com.lzj.elasticsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/count")
    public long count() {
        return userService.count();
    }

    @GetMapping("/getAll")
    public Iterable<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/save")
    public User save(@RequestBody User user) {
        userService.save(user);
        return user;
    }

    @PostMapping("/delete")
    public User delete(@RequestBody User user) {
        userService.delete(user);
        return user;
    }

    @GetMapping("/getByName/{userName}")
    public List<User> getByName(@PathVariable("userName") String userName) {
        return userService.getByName(userName);
    }

    @GetMapping("/pageQueryByName/{pageNum}/{pageSize}/{q}")
    public List<User> pageQuery(@PathVariable("pageNum") Integer pageNum,
                                @PathVariable("pageSize") Integer pageSize,
                                @PathVariable("q") String q) {
        Page<User> users = userService.pageQuery(pageNum, pageSize, q);
        List<User> content = users.getContent();
        return content;
    }

    @GetMapping("/pageQueryWithHightLight/{pageNum}/{pageSize}/{q}")
    public List<User> pageQueryWithHightLight(@PathVariable("pageNum") Integer pageNum,
                                @PathVariable("pageSize") Integer pageSize,
                                @PathVariable("q") String q) {
        Page<User> users = userService.pageQueryWithHighLight(pageNum, pageSize, q);
        if(users != null && users.getContent() != null) {
            List<User> content = users.getContent();
            return content;
        }
        return null;
    }
}
