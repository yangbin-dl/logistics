package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * 描述
 * 更换单
 * @author Yangbin
 * @since 2019/09/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="mf_gh")
public class Gh extends Bill {

}
