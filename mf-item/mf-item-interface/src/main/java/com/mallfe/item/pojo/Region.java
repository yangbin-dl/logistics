package com.mallfe.item.pojo;

import lombok.Data;

import javax.persistence.Transient;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/08/06
 */
@Data
public class Region {

    private Long id;
    /**
     * 地区编码
     */
    private String deptCode;

    /**
     * 地区名称
     */
    @Transient
    private String deptName;

}
