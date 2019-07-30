package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述
 * 送货路线类
 * @author Yangbin
 * @since 2019/07/05
 */
@Table(name="mf_path")
@Data
public class Path {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Integer id;

    /**
     * 编码
     */
    private String pathCode;

    /**
     * 名称
     */
    private String pathName;

    /**
     * 状态，0-禁用，1-可用
     */
    private Integer status;
}
