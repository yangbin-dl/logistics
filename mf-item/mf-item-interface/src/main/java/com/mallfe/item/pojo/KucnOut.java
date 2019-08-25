package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */

@Table(name="mf_kucn_out")
@Data
public class KucnOut{
    @Id
    @KeySql(useGeneratedKeys=true)
    private Integer id;

    /**
     * 货号
     */
    private Integer hh;

    /**
     * 类型
     */
    private Integer lx;

    /**
     * 类型描述
     */
    @Transient
    private String lxInfo;

    private Integer sl;
    private String lsh;
    private Integer xh;
    private String ywbm;
    private String storeCode;
    private String deptCode;
}
