package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name="mf_fc")
public class Fc extends Order {

    private List<FcDetail> list;
}
