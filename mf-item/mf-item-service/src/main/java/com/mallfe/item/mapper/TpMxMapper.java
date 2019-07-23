package com.mallfe.item.mapper;

import com.mallfe.item.pojo.TpDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
public interface TpMxMapper extends Mapper<TpDetail>, MySqlMapper<TpDetail> {
    @Insert("insert into mf_tp_mx(lsh,ddh,status) values(#{lsh},#{ddh},#{status})")
    int insertTpMx(@Param("lsh") String lsh,
                   @Param("ddh") String ddh,
                   @Param("status") Integer status);
}
