package com.mallfe.item.mapper;

import com.mallfe.item.pojo.AllBill;
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

    List<Tp> selectTp(@Param("status")Integer status, @Param("lsh") String lsh, @Param("key") String key);

    void updateTpmxToUnFinish(@Param("lsh") String lsh);

    List<Tp> selectTpWithUid(@Param("status")Integer status, @Param("lsh") String lsh, @Param("uid")Long uid);

    /**
     * 用原始单据查询配送单
     * 2022-04-01新增
     * 用于优化app查询速度
     */
    List<AllBill> selectBill(@Param("driverCode") String driveCode,
                             @Param("phone") String phone,
                             @Param("hh") Integer hh,
                             @Param("lsh") String lsh,
                             @Param("psdh") String psdh,
                             @Param("wlstatus") Integer wlstatus,
                             @Param("psstatus") Integer psstatus);
}
