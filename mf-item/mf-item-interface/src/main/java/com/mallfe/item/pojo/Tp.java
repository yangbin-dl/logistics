package com.mallfe.item.pojo;

import lombok.Data;

import javax.persistence.Table;
import java.util.List;

/**
 * 描述
 * 退配单类
 * @author Yangbin
 * @since 2019/07/23
 */
@Data
@Table(name="tp")
public class Tp extends WlBill {

    private List<TpDetail> list;
}
