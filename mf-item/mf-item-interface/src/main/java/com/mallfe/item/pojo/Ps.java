package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.util.List;

/**
 * 描述
 * 配送单类
 * @author Yangbin
 * @since 2019/07/23
 */
@Data
@Table(name="mf_ps")
@EqualsAndHashCode(callSuper = true)
public class Ps extends WlBill {
    private List<PsDetail> list;
}
