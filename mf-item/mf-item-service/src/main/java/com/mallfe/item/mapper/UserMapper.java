package com.mallfe.item.mapper;

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

    User insertUser(@Param("user")User user);
}
