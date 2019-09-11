package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 描述
 * 配送退配单通用类
 * @author Yangbin
 * @since 2019/07/23
 */
@Data
public class WlBill {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Integer id;

    /**
     * 单据号
     */
    private String lsh;

    /**
     * 司机编码
     */
    private String driverCode;

    /**
     * 司机姓名
     */
    @Transient
    private String driverName;

    /**
     * 路线编码
     */
    private String pathCode;

    /**
     * 路线名称
     */
    @Transient
    private String pathName;

    /**
     * 录入时间
     */
    private String lrsj;

    /**
     * 录入人id
     */
    private Long lrid;

    /**
     * 录入人账户名
     */
    private String lrusername;

    /**
     * 录入人真实姓名
     */
    private String truename;

    /**
     * 发出时间
     */
    private String fcsj;

    /**
     * 送达时间
     */
    private String sdsj;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态描述
     */
    @Transient
    private String statusInfo;

    /**
     * 地区编码
     */
    private String deptCode ;

    /**
     * 地区名称
     */
    @Transient
    private String deptName;


    /**
     * 仓库编码
     */
    private String storeCode ;

    /**
     * 仓库名称
     */
    private String storeName ;
}
