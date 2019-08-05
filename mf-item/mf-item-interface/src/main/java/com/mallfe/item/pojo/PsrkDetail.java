package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="mf_psrk_mx")
public class PsrkDetail extends WlBillDetail {

    /**
     * 货号
     */
    @Transient
    private Integer hh;

    /**
     * 类型
     */
    @Transient
    private Integer lx;
    /**
     * 数量
     */
    @Transient
    private Integer sl;
}
