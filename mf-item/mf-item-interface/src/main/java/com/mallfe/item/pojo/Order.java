package com.mallfe.item.pojo;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 描述
 * 购进返厂通用类
 * @author yangbin
 * @since 2019-07-10
 */
class Order {
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
     * 录入时间
     */
    private Date lrsj;
    /**
     * 入账时间
     */
    private Date cksj;

    /**
     * 类型
     */
    private Integer lx;

    /**
     * 单据明细
     */
    @Transient
    private List<OrderDetail> list;
}
