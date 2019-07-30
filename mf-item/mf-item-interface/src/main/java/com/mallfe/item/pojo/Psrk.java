package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 描述
 * 配送单类
 * @author Yangbin
 * @since 2019/07/23
 */
@Data
@Table(name="mf_psrk")
@EqualsAndHashCode(callSuper = true)
public class Psrk extends WlBill {
    private List<PsDetail> list;

    @Transient
    private List<Xs> xsList;

    /**
     * 入库时间
     */
    private String rksj;
}
