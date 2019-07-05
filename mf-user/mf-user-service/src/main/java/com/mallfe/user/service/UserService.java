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

    /**
     * 用户验证
     * @param username 用户名
     * @param password 密码
     * @return 完整用户信息，包括密码
     */
    public User verify(String username, String password){
        User t =new User();
        t.setUsername(username);
        t.setPassword(password);
        User user =userMapper.selectOne(t);
        if(user == null){
            throw new MallfeException(ExceptionEnum.USER_OR_PASSWORD_NOT_CORRECT);
        }

        return user;
    }

    /**
     * 更新用户信息
     * 注意：会根据id更新所有not null 字段。
     * 适用于更新密码、用户名等。
     * @param user 带有id的用户实体类
     */
    public void update(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }


    /**
     * 新增用户
     * @param user 用户信息
     * @return 带有id的用户信息
     */
    public User insert(User user){

        //1.检查用户名是否重复
        Long userId = findUserByUsername(user.getUsername());
        if (userId != null) {
            throw new MallfeException(ExceptionEnum.USERNAME_DUPLICATE);
        }

        //2.插入用户，插入后会自动获取id
        userMapper.insert(user);
        return user;
    }


    /**
     * 根据用户明查询用户信息
     * @param username 用户名
     * @return 用户id
     */
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
