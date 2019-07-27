package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Consumer;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-27
 */
public interface ConsumerMapper extends Mapper<Consumer> {
    @Select("select distinct location,phone,contact from vw_xs where phone like '#{phone}%'")
    List<Consumer> queryByPhone(String phone);
}
