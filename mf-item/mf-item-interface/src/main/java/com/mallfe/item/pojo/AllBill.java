package com.mallfe.item.pojo;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/25
 */

@Table(name="vw_xs")
@Data
public class AllBill {

    private String billtype;
    private String lsh;

    /**
     * 录入人id
     */
    private Long lrid;

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
     * 类型
     */
    private Integer lx;
    /**
     * 类型描述
     */
    @Transient
    private String lxInfo;

    /**
     * 数量
     */
    private Integer sl;

    private String pinm;

    /**
     * 详细地址
     */
    private String location;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 期望时间
     */
    private String qwsj;

    /**
     * 送达时间
     */
    private String sdsj;

    private String xingh;
    private String tm;

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
     * 部门编码
     */
    private String deptCode;

    /**
     * 部门名称
     */
    @Transient
    private String deptName;

    private String remark;

    private String province;

    private String city;

    private String district;

    private String driverName;

    private String driverPhone;

    private String psdh;

    private Integer psStatus;

    private String psStatusInfo;

    private Integer wlStatus;

    private String wlStatusInfo;
    /**
     * 仓库编码
     */
    private String storageCode;

    /**
     * 仓库名称
     */
    @Transient
    private String storageName;

    private String plbm;

    private String plmc;

    private String picUrl;

    private String ckuserid;
    @Transient
    private String ckusername;
}
