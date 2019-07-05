package com.mallfe.item.service;

import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.item.mapper.SpMapper;
import com.mallfe.item.pojo.Sp;
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
}
