package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
@EqualsAndHashCode(callSuper = true)
@Table(name="mf_kucn_in")
@Data
public class KucnIn extends Kucn{
    private Long sl;
    private String lsh;
    private int xh;
    private String ywbm;
}
