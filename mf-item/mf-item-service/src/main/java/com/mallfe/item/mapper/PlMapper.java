package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Pl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlMapper {
    List<Pl> queryPl(@Param("level") Integer level);
}
