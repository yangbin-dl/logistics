package com.mallfe.item.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 描述
 * 配送单类
 * @author Yangbin
 * @since 2019/07/23
 */
@Data
@Table(name="mf_ghps")
@EqualsAndHashCode(callSuper = true)
public class Ghps extends WlBill {
    private List<GhpsDetail> list;

    @Transient
    private List<Gh> ghList;
}
