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
    private Integer Kucn;

    private Integer rtKucn;

    private Integer dpcSl;

    private Integer ypcSl;

    private String storageCode;

    private String storageName;

    private String deptCode;

    private String deptName;

    private Integer lx;

    private String lxInfo;
}
