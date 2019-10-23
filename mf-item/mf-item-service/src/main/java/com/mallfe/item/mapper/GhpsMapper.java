package com.mallfe.item.mapper;

import com.mallfe.item.pojo.AllBill;
import com.mallfe.item.pojo.Ghps;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
public interface GhpsMapper extends Mapper<Ghps> {

    /**
     * 配送单作废
     * @param lsh
     * @return
     */
    int updateStatusToCancel(@Param("lsh") String lsh);


    /**
     * 配送单发出
     * @param lsh
     * @return
     */
    int updateStatusToOut(@Param("lsh") String lsh);

    /**
     * 配送单送达
     * @param lsh
     * @return
     */
    int updateStatusToFinish(@Param("lsh") String lsh);

    void updateGhpsmxStatusToUnFinish(@Param("lsh") String lsh);


    List<Ghps> selectGhps(@Param("status") Integer status,
                          @Param("lsh") String lsh,
                          @Param("key") String key);

    List<AllBill> selectList(@Param("driverCode") String driveCode,
                             @Param("phone") String phone,
                             @Param("hh") Integer hh,
                             @Param("lsh") String lsh,
                             @Param("psdh") String psdh,
                             @Param("wlstatus") Integer wlstatus,
                             @Param("psstatus") Integer psstatus);

    List<Ghps> selectGhpsWithUid(@Param("status") Integer status, @Param("lsh") String lsh, @Param("uid") Long uid);
}
