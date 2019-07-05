package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述
 * 库存类
 * @author Yangbin
 * @since 2019/07/05
 */
@Table(name="mf_kucn")
@Data
public class Kucn {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;
    private int hh;
    private int lx;
    private Long kucn;
}
