package com.mallfe.item.pojo;

import lombok.Data;

import javax.persistence.Table;

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
    private String lx;

    /**
     * 数量
     */
    private Long sl;

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


}