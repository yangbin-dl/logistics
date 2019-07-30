package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="mf_psrk_mx")
public class PsrkDetail extends WlBillDetail {
}
