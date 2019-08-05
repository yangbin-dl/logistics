package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Ps;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
public interface PsMapper extends Mapper<Ps> {

    /**
     * 配送单作废
     * @param lsh
     * @return
     */
    int updateStatusToCancel(@Param("lsh")String lsh);


    /**
     * 配送单发出
     * @param lsh
     * @return
     */
    int updateStatusToOut(@Param("lsh")String lsh);

    /**
     * 配送单送达
     * @param lsh
     * @return
     */
    int updateStatusToFinish(@Param("lsh")String lsh);

    void updatePsmxStatusToUnFinish(@Param("lsh")String lsh);


    List<Ps> selectPs(@Param("status")Integer status, @Param("lsh") String lsh);
}
