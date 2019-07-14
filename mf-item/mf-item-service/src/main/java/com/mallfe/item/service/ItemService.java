package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.SpMapper;
import com.mallfe.item.pojo.Sp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-05
 */
@Service
public class ItemService {

    @Autowired
    SpMapper spMapper;

    /**
     * 查询商品
     * @param sp 包含查询条件的商品实体类
     * @return 商品完整信息
     */
    public Sp query(Sp sp){
        Sp t = spMapper.selectOne(sp);

        if(t == null){
            throw new MallfeException(ExceptionEnum.SP_NOT_EXISTS);
        }

        return t;
    }

    public List<Sp> queryByPinm(String pinm){
        Example example = new Example(Sp.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("pinm",pinm.concat("%"));

        List<Sp> list = spMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.SP_NOT_EXISTS);
        }
        return list;
    }

    /**
     * 新增商品
     * @param sp 商品信息
     * @return 商品完整信息
     */
    public Sp insert(Sp sp){
        if(sp.getHh() == null){
            throw new MallfeException(ExceptionEnum.HH_CANNOT_BE_NULL);
        }

        if(sp.getPinm()== null){
            throw new MallfeException(ExceptionEnum.PINM_CANNOT_BE_NULL);
        }

        // 插入后自动获得id
        spMapper.insert(sp);

        return sp;
    }

    /**
     * 根据分页查询商品
     * @param page 显示页
     * @param rows 每页行
     * @param sortBy 排序字段
     * @param desc 倒序
     * @param key 关键字
     * @return 包含商品列表的ResponseEntity
     */
    public PageResult<Sp> querySpByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(Sp.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("pinm","%"+key+"%")
                    .orEqualTo("hh",key.toUpperCase());
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Sp> list = spMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.SP_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Sp> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }
}
