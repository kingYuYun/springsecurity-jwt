package com.yuy.springsecurity_jwt.controller;

import com.yuy.springsecurity_jwt.entity.User;
import com.yuy.springsecurity_jwt.entity.UserAuth;
import com.yuy.springsecurity_jwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/get-user")
    public User getUser(@RequestParam String username) {
        return userInfoService.getUserInfoByUsername(username);
    }

    @RequestMapping("/hello/user")
    public String helloUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "hello-user  "+name;
    }


    @RequestMapping("/hello/admin")
    public String helloAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "hello-admin  "+name;
    }


    @PostMapping("/add-user")
    public int addUser(@RequestBody UserAuth userAuth){
        return userInfoService.insertUser(userAuth);
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("/hello");
        //认证成功，得到认证成功之后用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return "hello  " + userName;
    }
}
