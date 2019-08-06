package com.mallfe.item.mapper;

import com.mallfe.item.pojo.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
public interface UserMapper extends Mapper<User> {
    List<User> selectUserByPage(String key);
}
