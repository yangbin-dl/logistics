package com.mallfe.item.pojo;

import lombok.Data;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/10/09
 */
@Data
public class KucnCompareReport extends Sp{
    /**
     * 系统库存
     */
    private Integer Kucn;

    /**
     * 即时库存
     */
    private Integer rtKucn;

    /**
     * 待配车数量
     */
    private Integer dpcSl;

    /**
     * 已配车数量
     */
    private Integer ypcSl;

    private String storageCode;

    private String storageName;

    private String deptCode;

    private String deptName;

    private Integer lx;

    private String lxInfo;
}
