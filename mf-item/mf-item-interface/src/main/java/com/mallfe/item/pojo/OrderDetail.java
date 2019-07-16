package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-10
 */
@Data
class OrderDetail {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Integer id;

    /**
     * 流水号
     */
    private String lsh;

    /**
     * 货号
     */
    private Integer hh;

    /**
     * 数量
     */
    private Long sl;

}
