package com.mallfe.item.mapper;

import com.mallfe.item.pojo.Store;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/08/06
 */
public interface StoreMapper {
    List<Store> selectStoreList(@Param("deptcode") String deptCode);
}
