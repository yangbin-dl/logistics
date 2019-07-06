package com.mallfe.logistics.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述
 * 送货路线类
 * @author Yangbin
 * @since 2019/07/05
 */
@EqualsAndHashCode(callSuper = true)
@Table(name="mf_path")
@Data
public class Path extends BaseBean{

}
