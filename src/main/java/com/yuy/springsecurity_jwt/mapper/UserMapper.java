package com.yuy.springsecurity_jwt.mapper;

import com.yuy.springsecurity_jwt.entity.User;
import com.yuy.springsecurity_jwt.entity.UserAuth;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    // 插入用户
    @Insert("insert into user(username, password, role) value(#{username}, #{password}, #{role})")
    int insertUserInfo(UserAuth userAuth);
}
