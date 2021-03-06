package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 描述
 * 通用类
 * @author Yangbin
 * @since 2019/07/05
 */
@Data
public class BaseBean {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态，0-禁用，1-可用
     */
    private Integer status;
    /**
     * 状态描述
     */
    @Transient
    private String statusInfo;
}
