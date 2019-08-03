package com.mallfe.item.pojo;

import lombok.Data;

import javax.persistence.Transient;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
@Data
public class WlBillDetail {

    /**
     * 单据号
     */
    private String lsh;

    /**
     * 销售、退货单号
     */
    private String ddh;

    /**
     * 送达状态，0-配送中，1-送达，2-未送达
     */
    private Integer status;
    /**
     * 状态描述
     */
    @Transient
    private String statusInfo;

}
