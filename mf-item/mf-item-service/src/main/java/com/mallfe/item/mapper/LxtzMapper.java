package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Lxtz;
import org.apache.ibatis.annotations.Param;

public interface LxtzMapper {
    int insertBill(Lxtz lxtz);

    int updateStatus(@Param("lsh") String lsh,@Param("status") Integer status);
}
