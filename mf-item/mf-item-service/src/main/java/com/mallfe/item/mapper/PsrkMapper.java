package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Psrk;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
public interface PsrkMapper extends Mapper<Psrk> {


    int insertPsrkMx(@Param("lsh") String lsh);

    int insertFromPs(@Param("lsh") String lsh);
}
