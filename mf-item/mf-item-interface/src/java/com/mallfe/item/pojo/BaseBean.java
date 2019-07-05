package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
@Data
public class BaseBean {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;
    private String code;
    private String name;
    private int stauts;
}
