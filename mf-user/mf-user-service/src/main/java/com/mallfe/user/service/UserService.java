package com.mallfe.user.service;

import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.user.mapper.UserMapper;
import com.mallfe.user.pojo.User;
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
        User user =userMapper.selectOne(t);
        if(user.getId() == null){
            throw new MallfeException(ExceptionEnum.USER_OR_PASSWORD_NOT_CORRECT);
        }

        return user;
    }

    public void insertUser(User user){
        userMapper.insert(user);
    }


}
