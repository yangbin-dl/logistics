package com.mallfe.item.pojo;

import lombok.Data;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/10/30
 */
@Data
public class SpInAndOutDeatilReport{
    /**
     * 业务编码
     */
    private String ywbm;

    /**
     * 业务编码
     */
    private String ywmc;

    /**
     * 流水号
     */
    private String lsh;

    /**
     * 仓库编码
     */
    private String storeCode;

    /**
     * 仓库名称
     */
    private String storeName;

    /**
     * 时间
     */
    private String sj;

    /**
     * 入库数量
     */
    private String rksl;

    /**
     * 出库数量
     */
    private String cksl;

    private Integer lx;

    private String lxInfo;
}
