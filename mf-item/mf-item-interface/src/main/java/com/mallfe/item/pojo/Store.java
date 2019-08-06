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
public class Store {

    private Long id;

    /**
     * 店铺编码
     */
    private String storeCode;

    /**
     * 店铺名称
     */
    @Transient
    private String storeName;


}
