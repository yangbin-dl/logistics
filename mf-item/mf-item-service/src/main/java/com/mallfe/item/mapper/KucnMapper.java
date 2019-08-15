package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Kucn;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
public interface KucnMapper extends Mapper<Kucn> {

    void addKucn(@Param("sl") Integer sl, @Param("id") Integer id);

    int reduceKucn(@Param("sl") Integer sl, @Param("id") Integer id);

    List<Kucn> selectKucn(@Param("hh") Integer hh,@Param("storecode") String storeCode);
}
