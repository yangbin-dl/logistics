package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Fc;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/15
 */
public interface FcMapper extends Mapper<Fc>, MySqlMapper<Fc> {

    int updateBillStatus(@Param("status") int status, @Param("lsh") String lsh);

    int updateStatusToFinish(@Param("lsh") String lsh);

    List<Fc> selectBill(@Param("lsh") String lsh, @Param("status") Integer status);

    int updateStatusToZero(@Param("lsh") String lsh);
}
