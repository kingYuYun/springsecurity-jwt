package com.yuy.springsecurity_jwt.service;

import com.yuy.springsecurity_jwt.entity.User;
import com.yuy.springsecurity_jwt.entity.UserAuth;
import com.yuy.springsecurity_jwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserInfoByUsername(String username)
    {
        return userMapper.findByUsername(username);
    }

    public int insertUser(UserAuth userAuth){
        // 加密密码
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
        return userMapper.insertUserInfo(userAuth);
    }

}
