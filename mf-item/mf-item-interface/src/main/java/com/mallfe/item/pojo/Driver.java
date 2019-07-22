package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * 描述
 * 司机类
 * @author Yangbin
 * @since 2019/07/05
 */
@EqualsAndHashCode(callSuper = true)
@Table(name="mf_driver")
@Data
public class Driver extends BaseBean{

    /**
     * 司机电话
     */
    private String phone;
}