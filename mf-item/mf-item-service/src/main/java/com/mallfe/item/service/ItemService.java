package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.json.JsonData;
import com.mallfe.common.json.JsonError;
import com.mallfe.common.json.JsonObject;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.aop.JsonMapper;
import com.mallfe.item.mapper.KucnMapper;
import com.mallfe.item.mapper.PlMapper;
import com.mallfe.item.mapper.SpMapper;
import com.mallfe.item.mapper.UserMapper;
import com.mallfe.item.pojo.Pl;
import com.mallfe.item.pojo.Sp;
import com.mallfe.item.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import povo.PlVo;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-05
 */
@Service
@Slf4j
public class ItemService {

    @Autowired
    SpMapper spMapper;

    @Autowired
    PlMapper plMapper;

    @Autowired
    KucnMapper kucnMapper;

    @Autowired
    UserMapper userMapper;

    /**
     *
     * @param hh 货号
     * @param uid 查询人id
     * @return
     */
    public Sp query(Integer hh,Long uid) {
        Sp sp = spMapper.selectSpInfo(hh);

        if (sp == null) {
            throw new MallfeException(ExceptionEnum.SP_NOT_EXISTS);
        }

        if(uid==null){
            return sp;
        }
        User user = userMapper.selectUserInfoById(uid);

        if(user == null){
            throw new MallfeException(ExceptionEnum.SP_NOT_EXISTS);
        }

        sp.setKucnList(kucnMapper.selectKucn(hh,user.getStoreCode()));

        return sp;
    }

    public List<Sp> queryByPinm(String pinm) {
        Example example = new Example(Sp.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("pinm", pinm.concat("%"));

        List<Sp> list = spMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new MallfeException(ExceptionEnum.SP_NOT_EXISTS);
        }
        return list;
    }

    /**
     * 新增商品
     *
     * @param sp 商品信息
     * @return 商品完整信息
     */
    public Sp insert(Sp sp) {
        if (sp.getHh() == null) {
            throw new MallfeException(ExceptionEnum.HH_CANNOT_BE_NULL);
        }

        if (sp.getPinm() == null) {
            throw new MallfeException(ExceptionEnum.PINM_CANNOT_BE_NULL);
        }

        // 插入后自动获得id
        spMapper.insert(sp);

        return sp;
    }

    /**
     * 根据分页查询商品
     *
     * @param page   显示页
     * @param rows   每页行
     * @param sortBy 排序字段
     * @param desc   倒序
     * @param key    关键字
     * @return 包含商品列表的ResponseEntity
     */
    public PageResult<Sp> querySpByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(Sp.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().orLike("pinm", "%" + key + "%")
                    .orLike("hh", key.toUpperCase() + "%");
        }
        //排序
        if (StringUtils.isNotBlank(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Sp> list = spMapper.querySp(key);
        if (CollectionUtils.isEmpty(list)) {
            throw new MallfeException(ExceptionEnum.SP_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Sp> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    /**
     * 商品信息修改
     *
     * @param sp
     * @return
     */
    public void modify(Sp sp) {
        //按照id修改商品信息
        spMapper.updateByPrimaryKey(sp);
    }

    public JsonObject findHh(Integer hh,Long userid) {
        Sp sp = spMapper.selectSpInfo(hh);

        if (sp == null) {
            return new JsonError("商品未找到");
        }

        List<Pl> plList = userMapper.selectUserPl(userid);

        plList = plList.stream().filter(x -> x.getPlbm().equals(sp.getPlbm())).collect(Collectors.toList());

        if(plList.isEmpty()){
            return new JsonError("用户不能使用该商品");
        }

        User user = userMapper.selectUserInfoById(userid);

        sp.setKucnList(kucnMapper.selectKucn(hh,user.getStoreCode()));

        return new JsonData(sp);

    }

    public List<Pl> queryPlByPage(Integer level) {
        //分页


        //查询
        List<Pl> list = plMapper.queryPl(level);


        if (CollectionUtils.isEmpty(list)) {
            throw new MallfeException(ExceptionEnum.PL_NOT_EXISTS);
        }

        return list;
    }

    /**
     * 递归
     */
    private PlVo recur(PlVo vo, PlVo vp) {

        if (vo.getLevel() == 2 && vo.getId().substring(0, 4).equals(vp.getId().substring(0, 4))) {
            vo.getChildren().add(vp);
            return vo;
        }
        vo.getChildren().forEach(a->recur(a,vp));
        return vo;
    }

    public PlVo queryTreeData() {
        //查询
        List<Pl> list = plMapper.queryPl(0);


        PlVo plVo = new PlVo();
        plVo.setId("0");

        List<PlVo> pls = new ArrayList<PlVo>();
        list.stream().filter(o -> o.getLevel() == 1).forEach(i -> pls.add(new PlVo(i)));
        plVo.setChildren(pls);


        List<PlVo> pls1 = new ArrayList<PlVo>();
        list.stream().filter(o -> o.getLevel() == 2).forEach(i -> pls1.add(new PlVo(i)));
        plVo.getChildren().forEach(a -> {
            pls1.forEach(b -> {
                if (a.getId().substring(0, 2).equals(b.getId().substring(0, 2))) {
                    a.getChildren().add(b);
                }
            });
        });

        List<PlVo> pls2 = new ArrayList<PlVo>();
        list.stream().filter(o -> o.getLevel() == 3).forEach(i -> pls2.add(new PlVo(i)));
        pls2.forEach(a -> {
            recur(plVo, a);
        });


        log.info(JsonMapper.NON_NULL.toJson(plVo));


        if (CollectionUtils.isEmpty(list)) {
            throw new MallfeException(ExceptionEnum.PL_NOT_EXISTS);
        }

        return plVo;
    }
}
