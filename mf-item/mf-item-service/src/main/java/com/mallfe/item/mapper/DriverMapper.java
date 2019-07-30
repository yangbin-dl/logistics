package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Driver;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
public interface DriverMapper extends Mapper<Driver> {

    @Select("select id,driver_code as driverCode,driver_name as driverName,status,phone from mf_driver order by " +
            "driver_code")
    List<Driver> list();

}
