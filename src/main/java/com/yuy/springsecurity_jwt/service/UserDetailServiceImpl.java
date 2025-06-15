package com.yuy.springsecurity_jwt.service;

import com.yuy.springsecurity_jwt.entity.User;
import com.yuy.springsecurity_jwt.entity.UserAuth;
import com.yuy.springsecurity_jwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;

    /**
     * 需新建配置类注册一个指定的加密方式Bean，或在下一步Security配置类中注册指定
     */

    /*
    * 5、获取用户信息 loadUserByUsername（）
    * UserDetailsService接口,加载用户特定数据的核心接口,其中定义了一个根据用户名查询用户信息的方法
    * UserDetails接口,提供核心用户信息.将UserDetailsService中获取的信息封装为UserDetails对象返回.并将其封装至Authentication对象中
    * */
    @Override
    public UserDetails loadUserByUsername(String username) {
        //查询用户
        User user = userMapper.findByUsername(username);
        if(user == null){
            throw new RuntimeException("用户不存在或已被禁用");
        }
        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(user.getUsername());
        userAuth.setPassword(user.getPassword());
        userAuth.setNickName(user.getNickName());

        //添加角色
        List<String> roles=new ArrayList<>();
        if("user@qq.com".equals(username)){
            roles.add("USER");
            userAuth.setRoles(roles);
        }
        if("admin@qq.com".equals(username)){
            roles.add("USER");
            roles.add("ADMIN");
            userAuth.setRoles(roles);
        }
        return userAuth;
    }
}
