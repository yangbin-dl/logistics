package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 描述
 * 销售单
 * @author yangbin
 * @since 2019-07-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="mf_xs")
public class Xs extends Bill {
    @Transient
    private String statusMsg;

}
