package com.mallfe.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 * 描述
 * 商品类
 *
 * @author Yangbin
 * @since 2019/07/05
 */
@Table(name = "mf_sp")
@Data
public class Sp {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Pattern(regexp = "\\d{6}", message = "必须输入6位数字", groups = Groups.Default.class)
    private Integer hh;
    private String pinm;
    private String xingh;
    private String tm;
    private Long zhongl;
    private Long chang;
    private Long kuan;
    private Long gao;

}
