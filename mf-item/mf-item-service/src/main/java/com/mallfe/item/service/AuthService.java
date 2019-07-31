package com.mallfe.item.service;

import com.mallfe.common.entity.UserInfo;
import com.mallfe.common.utils.JwtUtils;
import com.mallfe.item.mapper.UserMapper;
import com.mallfe.item.pojo.User;
import com.mallfe.item.properties.JwtServerProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtServerProperties properties;

    /**
     * 用户授权
     * @param username
     * @param password
     * @return
     */
    public String authentication(String username, String password) {

        try{
            //1.调用微服务查询用户信息
            User t =new User();
            t.setUsername(username);
            t.setPassword(password);
            User user = this.userMapper.selectOne(t);
            //2.查询结果为空，则直接返回null
            if (user == null){
                return null;
            }
            //3.查询结果不为空，则生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()),
                    properties.getPrivateKey(), properties.getExpire());
            return token;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
