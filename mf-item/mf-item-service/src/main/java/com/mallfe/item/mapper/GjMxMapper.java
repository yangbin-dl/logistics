package com.mallfe.item.mapper;

import com.mallfe.item.pojo.GjDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/15
 */
public interface GjMxMapper extends Mapper<GjDetail>, MySqlMapper<GjDetail> {

    @Insert("insert into mf_gj_mx(lsh,hh,sl) values(#{lsh},#{hh},#{sl})")
    int insertMx(@Param("lsh") String lsh,
                         @Param("hh") Integer hh,
                         @Param("sl") Integer sl);


    @Select("select a.id,lsh,a.hh,sl,b.pinm,b.xingh,b.tm from " +
            "mf_gj_mx a left join mf_sp b on a.hh=b.hh where a.lsh=#{lsh}")
    List<GjDetail> getMx(@Param("lsh") String lsh);

    @Delete("delete from mf_gj_mx where lsh=#{lsh}")
    int deleteMx(@Param("lsh") String lsh);
}
