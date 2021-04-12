package com.mallfe.item.mapper;

import com.mallfe.item.pojo.*;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface XsMapper extends Mapper<Xs>, MySqlMapper<Xs> {

    /**
     * 销售单提交
     * @param lsh 单据号
     * @return 影响的行数
     */
    int updateStatusToCommited(@Param("lsh") String lsh,@Param("ckuserid") Long ckuserid);

    /**
     * 取消销售单
     * @param lsh 单据号
     * @return 影响的行数
     */
    int updateStatusToCancel(@Param("lsh") String lsh,@Param("ckuserid") Long ckuserid);

    /**
     * 销售单更新
     * @param xs 销售单
     * @return 影响的行数
     */
    int updateBill(Xs xs);

    /**
     * 更新为已配车
     * @param lsh
     * @param psdh
     * @param driver
     * @param path
     * @return
     */
    int updateStatusToPs(@Param("lsh") String lsh,
                         @Param("psdh") String psdh,
                         @Param("driver") String driver,
                         @Param("path") String path);

    /**
     * 将已配车更新为待配车,配送单用
     * @param lsh
     * @return
     */
    int updateStatusToUnPs(@Param("lsh") String lsh);

    /**
     * 送达确认时将为送达销售状态改为待配车
     * @param lsh
     * @return
     */
    int updateStatusToUnPsByArrival(@Param("lsh") String lsh);

    int updateStatusToArrival(@Param("lsh") String lsh);

    List<Xs> selectXsWithLsh(@Param("lsh") String lsh);

    List<Xs> selectXsWithLshForRk(@Param("lsh") String lsh);

    List<Xs> selectXsList(@Param("key") String key,
                          @Param("status") Integer status,
                          @Param("uid")Long uid,
                          @Param("rq") String rq);

    List<AllBill> selectAllBill(@Param("lrid") String lrid,
                                @Param("phone")String phone,
                                @Param("lsh") String lsh,
                                @Param("hh") Integer hh,
                                @Param("contact") String contact);

    List<AllBill> selectAllBill2(@Param("lrid") String lrid,
                                @Param("phone")String phone,
                                @Param("lsh") String lsh,
                                @Param("hh") Integer hh,
                                @Param("contact") String contact);

    List<AllBill> selectAllBillByStore(@Param("storecode") String storecode,
                                       @Param("phone")String phone,
                                       @Param("lsh") String lsh,
                                       @Param("hh") Integer hh,
                                       @Param("contact") String contact,
                                       @Param("uid") String uid
                                       );

    AllBill selectOneBill(@Param("lsh") String lsh);

    AllBill selectBillDetail(@Param("lsh") String lsh);

    List<Province> selectProvince();

    List<City> selectCity(@Param("province") String province);

    List<District> selectDistrict(@Param("city") String city);

    List<AllBill> selectAllBillForSh(@Param("storecode") String storecode,
                                     @Param("phone")String phone,
                                     @Param("lsh") String lsh,
                                     @Param("hh") Integer hh,
                                     @Param("contact") String contact,
                                     @Param("uid") String uid
    );


    int updateStatusToRevert(Xs xs);

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
}
