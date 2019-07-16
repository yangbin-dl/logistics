package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Kucn;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
public interface KucnMapper extends Mapper<Kucn> {
    @Update("update mf_kucn set kucn=kucn+#{sl} where id=#{id}")
    int addKucn(@Param("sl") Integer sl, @Param("id") Integer id);

    @Update("update mf_kucn set kucn=kucn-#{sl} where id=#{id}")
    int reduceKucn(@Param("sl") Integer sl, @Param("id") Integer id);
}
