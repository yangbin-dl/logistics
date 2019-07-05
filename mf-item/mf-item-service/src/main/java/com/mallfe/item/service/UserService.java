package com.mallfe.item.service;

import com.mallfe.item.mapper.UserMapper;
import com.mallfe.item.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User verifyUser(String username, String password){
        User t =new User();
        t.setUsername(username);
        t.setPassword(password);

        return userMapper.selectOne(t);
    }

    public void insertUser(User user){
        userMapper.insert(user);
    }


}
