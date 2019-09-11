package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Psrk;
import com.mallfe.item.pojo.PsrkDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
public interface PsrkMapper extends Mapper<Psrk> {


    int insertPsrkMx(@Param("lsh") String lsh);

    int insertFromPs(@Param("lsh") String lsh);

    List<Psrk> selectPsrk(@Param("uid") Long uid);

    int updatePsrkStatus(@Param("lsh") String lsh);


    List<PsrkDetail> selectPsrkMx(@Param("lsh")String lsh);
}
