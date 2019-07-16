package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Gj;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface GjMapper extends Mapper<Gj>, MySqlMapper<Gj> {

    @Update("update mf_gj set status=#{status},cksj = now() where lsh=#{lsh} and status=0")
    int updateBillStatus(@Param("status") int status,@Param("lsh") String lsh);

}
