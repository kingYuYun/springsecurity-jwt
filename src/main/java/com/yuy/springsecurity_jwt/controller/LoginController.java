package com.yuy.springsecurity_jwt.controller;

import cn.hutool.system.UserInfo;
import com.yuy.springsecurity_jwt.entity.User;
import com.yuy.springsecurity_jwt.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/security")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    public String login(@RequestBody User user) {

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authentication);

        if (authenticate.isAuthenticated()) { //认证通过
            Object principal = authenticate.getPrincipal();
            Map<String, Object> claims = new HashMap<>();
            claims.put("user", principal);
            String token = JwtUtil.createJWT("itcast", 360000, claims);
            return token;
        } else {
            return "";
        }
    }
}
