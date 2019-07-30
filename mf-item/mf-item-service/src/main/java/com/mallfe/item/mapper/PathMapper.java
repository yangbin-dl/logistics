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

    @Select("select id,path_code as pathCode,path_name as pathName,status from mf_path order by path_code")
    List<Path> list();
}
