package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

/**
 * 描述
 * 销售退货单据通用类
 * @author yangbin
 * @since 2019-07-10
 */
@Data
class Bill {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;

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
    private String lruserame;

    /**
     * 录入人真实姓名
     */
    private String truename;

    /**
     * 单据状态
     */
    private Integer status;

    /**
     * 录入时间
     */
    private Date lrsj;

    /**
     * 货号
     */
    private Integer hh;

    /**
     * 类型
     */
    private Integer lx;

    /**
     * 数量
     */
    private Long sl;

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
    private Date qwsj;

    /**
     * 送达时间
     */
    private Date sdsj;

    /**
     * 司机
     */
    private String driverCode;

    /**
     * 送货路线
     */
    private String pathCode;
}
