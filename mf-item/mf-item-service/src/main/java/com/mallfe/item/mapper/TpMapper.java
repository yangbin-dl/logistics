package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Tp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
public interface TpMapper extends Mapper<Tp> {

    /**
     * 退配单作废
     * @param lsh
     * @return
     */
    int updateStatusToCancel(@Param("lsh") String lsh);
}
