package com.mallfe.item.mapper;

import com.mallfe.item.pojo.AllBill;
import com.mallfe.item.pojo.Th;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ThMapper extends Mapper<Th>, MySqlMapper<Th> {

    /**
     * 退货单提交
     * @param lsh
     * @param ckuserid
     * @return int
     */
    int updateStatusToCommited(@Param("lsh") String lsh, @Param("ckuserid") Long ckuserid);

    /**
     * 取消退货单
     * @param lsh 退货单
     * @return
     */
    int updateStatusToCancel(@Param("lsh") String lsh, @Param("ckuserid") Long ckuserid);

    /**
     * 退货单更新
     * @param th 退货单
     * @return
     */
    int updateBill(Th th);

    /**
     * 更新为已配车
     * @param lsh
     * @param psdh
     * @param driver
     * @param path
     * @return
     */
    int updateStatusToTp(@Param("lsh") String lsh,
                         @Param("psdh") String psdh,
                         @Param("driver") String driver,
                         @Param("path") String path);

    /**
     * 将退货单状态改为待配车，退配单用
     * @param lsh
     * @return
     */
    int updateStatusToUnTp(@Param("lsh") String lsh);
    
    List<Th> selectThWithLsh(@Param("lsh") String lsh);

    List<Th> selectThList(@Param("key") String key,
                          @Param("status") Integer status,
                          @Param("uid") Long uid,
                          @Param("rq") String rq);

    int updateStatusToArrival(@Param("lsh") String lsh);

    List<Th> selectThWithLshForRk(@Param("lsh") String lsh);

    /**
     * 退货单撤销
     * @param th
     * @return
     */
    int updateStatusToRevert(Th th);

    Integer selectBillNumberCount(@Param("billNumber") String billNumber);

    int updateSdpicUrl(@Param("lsh") String lsh,@Param("sdpicUrl") String sdpicUrl);

    List<AllBill> selectBill(@Param("lrid") String lruserid,
                             @Param("phone") String phone,
                             @Param("lsh") String lsh,
                             @Param("hh") Integer hh,
                             @Param("contact") String contact,
                             @Param("limit") Integer limit,
                             @Param("status") Integer status,
                             @Param("storecode") String storecode,
                             @Param("shuserid") String shuserid,
                             @Param("his") Integer his);
    AllBill selectBillDetail(@Param("lsh") String lsh);
}
