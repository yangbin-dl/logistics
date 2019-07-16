package com.mallfe.item.mapper;

import com.mallfe.item.pojo.LshBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/15
 */
public interface LshMapper extends Mapper<LshBean> {

    @Update("update mf_lsh set lsh = lsh+1 where ywbm=#{ywbm}")
    int updateLsh(@Param("ywbm") String ywbm);

    @Update("update mf_lsh set lsh = 2 , rq=#{rq} where ywbm=#{ywbm} ")
    int clearLsh(@Param("ywbm") String ywbm, @Param("rq") String rq);

}
