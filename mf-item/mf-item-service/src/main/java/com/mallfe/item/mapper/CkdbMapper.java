package com.mallfe.item.mapper;


import com.mallfe.item.pojo.Ckdb;
import com.mallfe.item.pojo.CkdbDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CkdbMapper {
    Integer insertBill(Ckdb ckdb);

    void insertMx(Ckdb ckdb);

    List<Ckdb> selectBill(@Param("lsh") String lsh,
                          @Param("hh") Integer hh,
                          @Param("status") Integer status);

    void deleteMx(@Param("lsh") String lsh);
    List<CkdbDetail> selectMx(@Param("lsh") String lsh);

    int updateStatus(@Param("lsh") String lsh,
                     @Param("status") Integer status,
                     @Param("oldStatus") Integer oldStatus);
}
