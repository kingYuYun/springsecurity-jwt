package com.yuy.springsecurity_jwt.config;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuy.springsecurity_jwt.entity.UserAuth;
import com.yuy.springsecurity_jwt.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

@Component
public class TokenAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier authentication, RequestAuthorizationContext requestAuthorizationContext){
        //获取request
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        //获取用户当前的请求地址
        String requestURI = request.getRequestURI();
        //获取token
        String token = request.getHeader("token");
        if(null == token || "".equals(token)){
            return new AuthorizationDecision(false);
        }
        //解析token
        Claims claims = JwtUtil.parseJWT("itcast", token);
        if (ObjectUtil.isEmpty(claims)) {
            //token失效
            return new AuthorizationDecision(false);
        }
        //获取userAuth
        UserAuth userAuth = JSONObject.parseObject(JSON.toJSONString(claims.get("user")),UserAuth.class);
        //存入上下文
        UsernamePasswordAuthenticationToken auth
                =new UsernamePasswordAuthenticationToken( userAuth, userAuth.getPassword(), userAuth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        //判断地址与对象中的角色是否匹配
        if(userAuth.getRoles().contains("ADMIN")){
            if("/hello/admin".equals(requestURI)){
                return new AuthorizationDecision(true);
            }
        }
        if(userAuth.getRoles().contains("USER")){
            if("/hello/user".equals(requestURI)){
                return new AuthorizationDecision(true);
            }
        }
        return new AuthorizationDecision(false);
    }
}
