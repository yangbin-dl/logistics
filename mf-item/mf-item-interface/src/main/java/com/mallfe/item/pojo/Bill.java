package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;


/**
 * 描述
 * 销售退货单据通用类
 * @author yangbin
 * @since 2019-07-10
 */
@Data
public class Bill {
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
     * 类型
     */
    private Integer lx;

    /**
     * 类型
     */
    @Transient
    private String lxInfo;

    /**
     * 数量
     */
    private Integer sl;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

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

    /**
     * 司机
     */
    private String driverCode;

    /**
     * 送货路线
     */
    private String pathCode;

    @Transient
    private String pinm;
    @Transient
    private String xingh;
    @Transient
    private String tm;
    @Transient
    private Long zhongl;
    @Transient
    private Long chang;
    @Transient
    private Long kuan;
    @Transient
    private Long gao;

    @Transient
    private Integer kucn;
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

    /**
     * 仓库编码
     */
    private String storageCode;

    /**
     * 仓库名称
     */
    @Transient
    private String storageName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 手工备注：派工网点
     * @since 2021-03-24
     */
    private String remarksWd;

    /**
     * 手工备注：品类
     * @since 2021-03-24
     */
    private String remarksPl;

    private String picUrl;

    private Long ckuserid;

    @Transient
    private String ckusername;

    private String billNumber;

    private String sdpicUrl;
//     @Transient
//     private String picServer = "http://120.201.127.207:2389/";

    @Transient
    private String picServer = "http://47.94.95.93:2389/";
    
    @Transient
    private String revertInfo;


}
