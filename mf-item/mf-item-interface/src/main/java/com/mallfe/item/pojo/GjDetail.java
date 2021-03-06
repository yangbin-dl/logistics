package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="mf_gj_mx")
public class GjDetail extends OrderDetail {
}
