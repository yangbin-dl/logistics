package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Th;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ThMapper extends Mapper<Th>, MySqlMapper<Th> {

    /**
     * 退货单提交
     * @param th 退货单
     * @return
     */
    int updateStatusToCommited(Th th);

    /**
     * 取消退货单
     * @param th 退货单
     * @return
     */
    int updateStatusToCancel(Th th);

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

    int updateStatusToUnTp(@Param("lsh") String lsh,
                           @Param("psdh") String psdh);
}
