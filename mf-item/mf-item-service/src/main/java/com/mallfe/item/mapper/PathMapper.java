package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Path;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
public interface PathMapper extends Mapper<Path> {

    @Select("select id,code,name,status from mf_path order by code")
    List<Path> list();
}
