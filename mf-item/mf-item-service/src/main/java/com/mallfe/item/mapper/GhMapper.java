package com.mallfe.item.mapper;

import com.mallfe.item.pojo.AllBill;
import com.mallfe.item.pojo.Gh;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/09/25
 */
public interface GhMapper extends Mapper<Gh>, MySqlMapper<Gh> {
    /**
     * 换货单提交
     * @param lsh 单据号
     * @return 影响的行数
     */
    int updateStatusToCommited(@Param("lsh") String lsh, @Param("ckuserid") Long ckuserid);

    /**
     * 取消换货单
     * @param lsh 单据号
     * @return 影响的行数
     */
    int updateStatusToCancel(@Param("lsh") String lsh, @Param("ckuserid") Long ckuserid);

    AllBill selectOneBill(@Param("lsh") String lsh);

    List<Gh> selectGhWithLsh(@Param("lsh") String lsh);

    List<Gh> selectGhList(@Param("key") String key,
                          @Param("status") Integer status,
                          @Param("uid")Long uid,
                          @Param("rq") String rq);

    /**
     * 更新为已配车
     * @param lsh
     * @param psdh
     * @param driver
     * @param path
     * @return
     */
    int updateStatusToGhps(@Param("lsh") String lsh,
                         @Param("psdh") String psdh,
                         @Param("driver") String driver,
                         @Param("path") String path);

    /**
     * 将已配车更新为待配车,更换配送单用
     * @param lsh
     * @return
     */
    int updateStatusToUnGhps(@Param("lsh") String lsh);

    /**
     * 往返单撤销
     * @param gh
     * @return
     */
    int updateStatusToRevert(Gh gh);

    List<Gh> selectGhWithLshForRk(@Param("lsh") String lsh);

    int updateStatusToArrival(@Param("lsh") String lsh);

    Integer selectBillNumberCount(@Param("billNumber") String billNumber);

    int updateSdpicUrl(@Param("lsh") String lsh,@Param("sdpicUrl") String sdpicUrl);
}
