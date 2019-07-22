package com.mallfe.item.pojo;

import lombok.Data;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-10
 */
@Data
class OrderDetail extends Sp{
    /**
     * 流水号
     */
    private String lsh;


    /**
     * 数量
     */
    private Integer sl;

}