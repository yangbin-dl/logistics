package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Gj;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface GjMapper extends Mapper<Gj>, MySqlMapper<Gj> {

    int updateBillStatus(@Param("status") int status,@Param("lsh") String lsh);

    int updateStatusToFinish(@Param("lsh") String lsh);

    List<Gj> selectBill(@Param("lsh") String lsh, @Param("status") Integer status);

}
