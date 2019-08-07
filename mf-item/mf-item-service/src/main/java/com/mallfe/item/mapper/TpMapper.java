package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Tp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
public interface TpMapper extends Mapper<Tp> {

    /**
     * 退配单作废
     * @param lsh
     * @return
     */
    int updateStatusToCancel(@Param("lsh") String lsh);

    int updateStatusToCommit(@Param("lsh") String lsh);

    int updateStatusToFinish(@Param("lsh") String lsh);

    List<Tp> selectTp(@Param("status")Integer status, @Param("lsh") String lsh);

    void updateTpmxToUnFinish(@Param("lsh") String lsh);
}
