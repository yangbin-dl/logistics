package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Sp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
public interface SpMapper extends Mapper<Sp> {
    List<Sp> querySp(@Param("key") String key);
}
