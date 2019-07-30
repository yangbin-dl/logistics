package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述
 * 司机类
 * @author Yangbin
 * @since 2019/07/05
 */
@Table(name="mf_driver")
@Data
public class Driver{

    @Id
    @KeySql(useGeneratedKeys=true)
    private Integer id;

    /**
     * 编码
     */
    private String driverCode;

    /**
     * 名称
     */
    private String driverName;

    /**
     * 状态，0-禁用，1-可用
     */
    private Integer status;
    /**
     * 司机电话
     */
    private String phone;
}
