package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 描述
 * 用户类
 * @author Yangbin
 * @since 2019/07/05
 */
@Table(name="mf_user")
@Data
public class User {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;

    private String username;

    private String password;

    private String truename;

    /**
     * 用户类型：0-管理员，1-库管员，2-开单员，3-审核员
     */
    private Integer lx;

    /**
     * 类型描述
     */
    @Transient
    private String lxInfo;
}
