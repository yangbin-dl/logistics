package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Pl;
import com.mallfe.item.pojo.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
public interface UserMapper extends Mapper<User> {
    List<User> selectUserByPage(@Param("key") String key);

    int insertUser(User user);

    int insertUserPl(User user);

    User selectUserInfo(@Param("username") String username, @Param("password") String password);

    List<Pl> selectUserPl(@Param("uid") Long uid);

    User selectUserInfoById(@Param("uid") Long uid);

    void updateUserInfo(User user);

    void deleteUserPl(User user);

    void updatePwd(@Param("id")Long id, @Param("password")String password);

    void deleteUser(@Param("id")Long id);
}


