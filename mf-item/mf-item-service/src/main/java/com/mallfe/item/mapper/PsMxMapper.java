package com.mallfe.item.mapper;

import com.mallfe.item.pojo.PsDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
public interface PsMxMapper extends Mapper<PsDetail>, MySqlMapper<PsDetail> {
    @Insert("insert into mf_ps_mx(lsh,ddh,status) values(#{lsh},#{ddh},#{status})")
    int insertPsMx(@Param("lsh") String lsh,
                   @Param("ddh") String ddh,
                   @Param("status") Integer status);

    @Update("update mf_ps_mx set status=#{status} where lsh=#{lsh} and ddh=#{ddh}")
    int updateStatus(@Param("lsh")String lsh,
                     @Param("ddh")String ddh,
                     @Param("status")Integer status);
}
