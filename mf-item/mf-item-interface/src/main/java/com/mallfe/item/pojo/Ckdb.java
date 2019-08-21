package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * 描述
 * 仓库调拨单据
 * @author yangbin
 * @since 2019-08-21
 */
@Data
public class Ckdb {
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
    private String username;
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
     * 入账时间
     */

    private String cksj;

    /**
     * 类型
     */
    private Integer lx;

    /**
     * 类型描述
     */
    @Transient
    private String lxInfo;

    /**
     * 调入店铺编码
     */
    private String inStoreCode;

    /**
     * 调入店铺名称
     */
    @Transient
    private String inStoreName;

    /**
     * 调出店铺编码
     */
    private String outStoreCode;

    /**
     * 调出店铺名称
     */
    @Transient
    private String outStoreName;

    /**
     * 地区编码
     */
    private String deptCode = "0001";

    /**
     * 地区名称
     */
    @Transient
    private String deptName;

    @Transient
    private List<CkdbDetail> list;
}
