package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/08/06
 */
public interface DepartmentMapper {
    List<Department> selectDepartmentList(@Param("storeCode")String storeCode);
}
