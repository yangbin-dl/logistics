package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Xs;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface XsMapper extends Mapper<Xs>, MySqlMapper<Xs> {

    /**
     * 销售单提交
     * @param xs 销售单
     * @return
     */
    int updateStatusToCommited(Xs xs);

    /**
     * 取消销售单
     * @param xs 销售单
     * @return
     */
    int updateStatusToCancel(Xs xs);
}
