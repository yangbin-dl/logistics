package com.mallfe.item.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-27
 */
@Table(name="vw_xs")
@Data
public class Consumer {
    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

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
}
