package com.mallfe.user.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

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
    private int lx;
}
