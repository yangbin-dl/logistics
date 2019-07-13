package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Th;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ThMapper extends Mapper<Th>, MySqlMapper<Th> {

    /**
     * 退货单提交
     * @param th 退货单
     * @return
     */
    int updateStatusToCommited(Th th);
}
