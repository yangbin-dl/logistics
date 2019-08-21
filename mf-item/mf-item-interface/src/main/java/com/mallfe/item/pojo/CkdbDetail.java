package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述
 * 仓库调拨明细
 * @author yangbin
 * @since 2019-08-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CkdbDetail extends Sp{
    /**
     * 流水号
     */
    private String lsh;


    /**
     * 数量
     */
    private Integer sl;
}
