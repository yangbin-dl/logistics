package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Lxtz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LxtzMapper {
    int insertBill(Lxtz lxtz);

    int updateStatus(@Param("lsh") String lsh,
                     @Param("oldstatus") Integer oldStatus,
                     @Param("newstatus") Integer newStatus);

    void updateBill(Lxtz lxtz);

    int updateStatsuToFinish(@Param("lsh") String lsh);

    List<Lxtz> selectBills(@Param("lsh") String lsh, @Param("status")Integer status);

    Lxtz selectOneBill(@Param("lsh") String lsh);

    int updateStatusToZero(@Param("lsh")String lsh);
}
