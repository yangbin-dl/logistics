package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.AllBillMapper;
import com.mallfe.item.mapper.ConsumerMapper;
import com.mallfe.item.mapper.ThMapper;
import com.mallfe.item.mapper.XsMapper;
import com.mallfe.item.pojo.AllBill;
import com.mallfe.item.pojo.Consumer;
import com.mallfe.item.pojo.Th;
import com.mallfe.item.pojo.Xs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 描述
 * 销售退货Service
 * @author yangbin
 * @since 2019-07-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class XsThService {
    @Autowired
    XsMapper xsMapper;

    @Autowired
    ThMapper thMapper;

    @Autowired
    CommonService commonService;

    @Autowired
    AllBillMapper allBillMapper;

    @Autowired
    ConsumerMapper consumerMapper;
    public Xs insertXs(@NotNull Xs xs){
        String lsh = commonService.getLsh("XS");
        xs.setLsh(lsh);
        xs.setStatus(0);
        xs.setLrsj(CommonService.getStringDate());
        try {
            xsMapper.insert(xs);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        return xs;
    }

    public Th insertTh(@NotNull Th th){
        String lsh = commonService.getLsh("TH");
        th.setLsh(lsh);
        th.setStatus(0);
        th.setLrsj(CommonService.getStringDate());
        try {
            thMapper.insert(th);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        return th;
    }

    public Xs modifyXs(@NotNull Xs xs){
        try {
            if (xsMapper.updateBill(xs) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return xs;
    }

    public Th modifyTh(@NotNull Th th){
        try {
            if (thMapper.updateBill(th) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return th;
    }

    public PageResult<Xs> queryXsByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(Xs.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("lsh",key+"%")
                    .orLike("truename","%"+key+"%")
                    .orLike("contact","%"+key+"%");
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Xs> list = xsMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Xs> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public PageResult<Th> queryThByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(Th.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("lsh",key+"%")
                    .orLike("truename","%"+key+"%")
                    .orLike("contact","%"+key+"%");
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Th> list = thMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Th> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public void commitXs(Xs xs){
        try {
            xsMapper.updateStatusToCommited(xs);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void deleteXs(Xs xs){
        try {
            xsMapper.updateStatusToCancel(xs);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void commitTh(Th th){
        try {
            thMapper.updateStatusToCommited(th);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void deleteTh(Th th){
        try {
            thMapper.updateStatusToCancel(th);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public Xs queryXs(String lsh){
        Xs xs = new Xs();
        xs.setLsh(lsh);
        xs = xsMapper.selectOne(xs);
        return xs;
    }

    public Th queryTh(String lsh) {
        Th th = new Th();
        th.setLsh(lsh);
        th =thMapper.selectOne(th);
        return th;
    }

    public PageResult<AllBill> queryAll(Integer page, String lruserid, String phone, String contact) {
        //分页
        PageHelper.startPage(page, 10);
        //条件过滤
        Example example = new Example(Th.class);
        if(StringUtils.isNotBlank(lruserid)){
            example.createCriteria().andEqualTo("lrid",lruserid);
        }

        if(StringUtils.isNotBlank(phone)){
            example.createCriteria().andEqualTo("phone",phone);
        }

        if(StringUtils.isNotBlank(contact)){
            example.createCriteria().andEqualTo("contact",contact);
        }
        //排序

        example.setOrderByClause("lrsj desc");
        //查询
        List<AllBill> list = allBillMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<AllBill> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public Consumer queryByPhone(String phone) {
        Consumer consumer = consumerMapper.queryByPhone(phone);
        return consumer;
    }

    public List<Xs> queryXsForPs(String lsh) {
        List<Xs> list;

        if(StringUtils.isNotBlank(lsh)){
            list = xsMapper.selectXsWithLsh(lsh);
        } else{
            Xs xs = new Xs();
            xs.setStatus(1);
            list = xsMapper.select(xs);
        }

        return list;
    }


    public List<Th> queryThForTp(String lsh) {
        List<Th> list;

        if(StringUtils.isNotBlank(lsh)){
            list = thMapper.selectThWithLsh(lsh);
        } else{
            Th th = new Th();
            th.setStatus(1);
            list = thMapper.select(th);
        }
        return list;
    }
}
