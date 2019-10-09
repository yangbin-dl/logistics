package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Tprk;
import com.mallfe.item.pojo.TprkDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
public interface TprkMapper extends Mapper<Tprk> {

    Tprk selectOneBill(@Param("lsh") String lsh);

    int insertTprkMx(@Param("lsh") String lsh);

    int insertFromTp(@Param("lsh") String lsh);

    List<Tprk> selectTprk(@Param("uid")Long uid,@Param("status")Integer status);

    int updateTprkStatus(@Param("lsh") String lsh);

    List<TprkDetail> selectTprkMx(@Param("lsh") String lsh);
}
