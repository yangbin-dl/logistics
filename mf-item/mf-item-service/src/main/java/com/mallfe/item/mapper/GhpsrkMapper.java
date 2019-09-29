package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Ghpsrk;
import com.mallfe.item.pojo.GhpsrkDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
public interface GhpsrkMapper extends Mapper<Ghpsrk> {

    int insertGhpsrkMx(@Param("lsh") String lsh);

    int insertFromGhps(@Param("lsh") String lsh);

    List<Ghpsrk> selectGhpsrk(@Param("uid") Long uid, @Param("status") Integer status);

    int updateGhpsrkStatus(@Param("lsh") String lsh);

    List<GhpsrkDetail> selectGhpsrkMx(@Param("lsh") String lsh);
}
