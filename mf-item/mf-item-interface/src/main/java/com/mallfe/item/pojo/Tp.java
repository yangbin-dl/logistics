package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 描述
 * 退配单类
 * @author Yangbin
 * @since 2019/07/23
 */
@Data
@Table(name="mf_tp")
@EqualsAndHashCode(callSuper = true)
public class Tp extends WlBill {

    private List<TpDetail> list;

    @Transient
    private List<Th> thList;
}
