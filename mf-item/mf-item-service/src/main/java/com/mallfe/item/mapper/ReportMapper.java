package com.mallfe.item.mapper;

import com.mallfe.item.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/09/13
 */
public interface ReportMapper
{
    /**
     * @param rq 日期
     * @param deptCode 区域
     * @param storeCode 店铺
     * @return 库存结构
     */
    List<KucnStructure> selectPlKucnStructure(@Param("rq") String rq,
                                              @Param("deptCode") String deptCode,
                                              @Param("storeCode") String storeCode);

    List<KucnStructure> selectPpKucnStructure(@Param("rq") String rq,
                                              @Param("plbm") String plbm,
                                              @Param("deptCode") String deptCode,
                                              @Param("storeCode") String storeCode);

    List<AllBill> selectXsthList(@Param("type") String type,
                                 @Param("deptcode") String deptCode,
                                 @Param("strq") String strq,
                                 @Param("torq") String torq,
                                 @Param("lsh") String lsh,
                                 @Param("hh") Integer hh,
                                 @Param("plbm") String plbm,
                                 @Param("storecode") String storeCode,
                                 @Param("storagecode") String storageCode);

    AllBill selectXsthDetail(@Param("lsh") String lsh);

    List<KucnReport> selectKucnList(@Param("deptcode") String deptCode,
                                    @Param("storagecode") String storageCode,
                                    @Param("plbm") String plbm,
                                    @Param("hh") Integer hh);

    List<KucnReport> selectRtKucnList(@Param("deptcode") String deptCode,
                                      @Param("storagecode") String storageCode,
                                      @Param("plbm") String plbm,
                                      @Param("hh") Integer hh);

    List<KucnCompareReport> selectKucnLxList(@Param("deptcode") String deptCode,
                                             @Param("storagecode") String storageCode,
                                             @Param("plbm") String plbm,
                                             @Param("hh") Integer hh);

    List<SpInAndOutDeatilReport> selectSpInAndOutDeatil(@Param("deptcode") String deptCode,
                                                        @Param("storecode") String storageCode,
                                                        @Param("hh") Integer hh,
                                                        @Param("lx") Integer lx,
                                                        @Param("strq") String strq,
                                                        @Param("torq") String torq);

    List<AllBill> selectXsList(@Param("deptcode") String deptCode,
                               @Param("strq") String strq,
                               @Param("torq") String torq,
                               @Param("lsh") String lsh,
                               @Param("hh") Integer hh,
                               @Param("plbm") String plbm,
                               @Param("storecode") String storeCode,
                               @Param("storagecode") String storageCode);

    List<AllBill> selectThList(@Param("deptcode") String deptCode,
                               @Param("strq") String strq,
                               @Param("torq") String torq,
                               @Param("lsh") String lsh,
                               @Param("hh") Integer hh,
                               @Param("plbm") String plbm,
                               @Param("storecode") String storeCode,
                               @Param("storagecode") String storageCode);

}
