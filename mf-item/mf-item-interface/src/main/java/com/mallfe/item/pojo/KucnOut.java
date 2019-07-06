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
@Table(name="mf_kucn_out")
@Data
public class KucnOut extends Kucn{
    private Long sl;
    private String lsh;
    private Integer xh;
    private String ywbm;
}
