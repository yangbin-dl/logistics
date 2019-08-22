package com.mallfe.item.mapper;


import com.mallfe.item.pojo.Ckdb;
import com.mallfe.item.pojo.CkdbDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CkdbMapper {
    Integer insertBill(Ckdb ckdb);

    void insertMx(Ckdb ckdb);

    List<Ckdb> selectBill(@Param("lsh") String lsh, @Param("hh") Integer hh);

    void deleteMx(@Param("lsh") String lsh);
    List<CkdbDetail> selectMx(@Param("lsh") String lsh);
}
