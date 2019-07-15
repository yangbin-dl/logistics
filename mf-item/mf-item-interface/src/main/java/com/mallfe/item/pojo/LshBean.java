package com.mallfe.item.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/15
 */
@Data
@Table(name="mf_lsh")
public class LshBean {
    private String ywbm;

    private Long lsh;

    private String rq;

}
