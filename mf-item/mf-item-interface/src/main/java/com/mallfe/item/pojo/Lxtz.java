package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-09-04
 */
@Data
public class Lxtz extends Sp{
    @Id
    @KeySql(useGeneratedKeys=true)
    private Integer id;

    /**
     * 单据号
     */
    private String lsh;

    /**
     * 录入人id
     */
    private Long lrid;

    /**
     * 录入人用户名
     */
    private String lrusername;

    /**
     * 录入人真实姓名
     */
    private String truename;

    /**
     * 单据状态
     */
    private Integer status;

    /**
     * 状态描述
     */
    @Transient
    private String statusInfo;

    /**
     * 录入时间
     */
    private String lrsj;

    /**
     * 货号
     */
    private Integer hh;

    /**
     * 数量
     */
    private Integer sl;

    /**
     * 原类型
     */
    private Integer oldLx;

    /**
     * 新类型
     */
    private Integer newLx;

    /**
     * 店铺编码
     */
    private String storeCode;

    /**
     * 店铺名称
     */
    @Transient
    private String storeName;

    /**
     * 地区编码
     */
    private String deptCode;

    /**
     * 地区名称
     */
    @Transient
    private String deptName;

    private String remark;
    @Transient
    private String newLxInfo;
    @Transient
    private String oldLxInfo;
    @Transient
    private String cksj;

}
