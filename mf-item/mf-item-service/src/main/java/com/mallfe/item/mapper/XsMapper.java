package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Xs;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface XsMapper extends Mapper<Xs>, MySqlMapper<Xs> {

    /**
     * 销售单提交
     * @param xs 销售单
     * @return 影响的行数
     */
    int updateStatusToCommited(Xs xs);

    /**
     * 取消销售单
     * @param xs 销售单
     * @return 影响的行数
     */
    int updateStatusToCancel(Xs xs);

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
     * 将已配车更新为待配车
     * @param lsh
     * @param psdh
     * @return
     */
    int updateStatusToUnPs(@Param("lsh") String lsh,
                           @Param("psdh") String psdh);
}
