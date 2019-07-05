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
        if(user == null){
            throw new MallfeException(ExceptionEnum.USER_OR_PASSWORD_NOT_CORRECT);
        }

        return user;
    }

    public User insertUser(User user){

        //1.检查用户名是否重复
        Long userId = findUserByUsername(user.getUsername());
        if (userId != null) {
            throw new MallfeException(ExceptionEnum.USERNAME_DUPLICATE);
        }

        //2.插入用户
        userMapper.insert(user);

        //3.再次查询，获取用户名对应的id
        user.setId(findUserByUsername(user.getUsername()));
        return user;
    }

    private Long findUserByUsername(String username){
        User t  = new User();
        t.setUsername(username);

        User user =userMapper.selectOne(t);
        if(user == null){
            return null;
        }
        return user.getId();
    }


}
