package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-08-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class City extends Province{
    private String city;
}
