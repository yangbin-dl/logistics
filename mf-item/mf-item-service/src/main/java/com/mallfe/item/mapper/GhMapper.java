package com.mallfe.item.mapper;

import com.mallfe.item.pojo.AllBill;
import com.mallfe.item.pojo.Gh;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

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
    int updateStatusToCommited(@Param("lsh") String lsh);

    /**
     * 取消换货单
     * @param lsh 单据号
     * @return 影响的行数
     */
    int updateStatusToCancel(@Param("lsh") String lsh);

    AllBill selectOneBill(@Param("lsh") String lsh);
}
