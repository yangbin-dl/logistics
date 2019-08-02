package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.json.JsonData;
import com.mallfe.common.json.JsonError;
import com.mallfe.common.json.JsonObject;
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

        //查询
        List<Xs> list = xsMapper.selectXsList(key);


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

        List<Th> list = thMapper.selectTh(key);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Th> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public JsonObject commitXs(Xs xs){
        try {
            if(xsMapper.updateStatusToCommited(xs)!=1){
                return new JsonError("单据状态异常，提交失败！");
            }
        } catch (Exception e){
            return new JsonError("系统异常，提交失败！");
        }
        return new JsonData("提交成功！");
    }

    public JsonObject deleteXs(Xs xs){
        try {
            if(xsMapper.updateStatusToCancel(xs)!=1){
                return new JsonError("单据状态异常，作废失败！");
            }
        } catch (Exception e){
            return new JsonError("系统异常，作废失败！");
        }
        return new JsonData("作废成功！");
    }

    public JsonObject commitTh(Th th){
        try {
            if(thMapper.updateStatusToCommited(th)!=1){
                return new JsonError("单据状态异常，提交失败！");
            }
        } catch (Exception e){
            return new JsonError("系统异常，提交失败！");
        }
        return new JsonData("提交成功！");
    }

    public JsonObject deleteTh(Th th){
        try {
            if(thMapper.updateStatusToCancel(th)!=1) {
                return new JsonError("单据状态异常，作废失败！");
            }
        } catch (Exception e){
            return new JsonError("系统异常，作废失败！");
        }
        return new JsonData("作废成功！");
    }

    public AllBill queryBill(String lsh){

        AllBill bill = xsMapper.selectOneBill(lsh);

        return bill;
    }

    public JsonObject queryAll(Integer page, String lruserid, String phone, Integer hh, String lsh) {
        //分页
        PageHelper.startPage(page, 10);
        //条件过滤

        //查询
        List<AllBill> list = xsMapper.selectAllBill(lruserid,phone,lsh,hh);
        if(CollectionUtils.isEmpty(list)){
            return new JsonError("未查询到单据！");
        }

        //解析分页结果
        PageInfo<AllBill> info = new PageInfo<>(list);

        return new JsonData(new PageResult<>(info.getTotal(), list));
    }

    public JsonObject queryByPhone(String phone) {
        Consumer consumer = consumerMapper.queryByPhone(phone);
        return new JsonData(consumer);
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

    public JsonObject appInsertXs(Xs xs) {

        String lsh = commonService.getLsh("XS");
        xs.setLsh(lsh);
        xs.setStatus(0);
        xs.setLrsj(CommonService.getStringDate());
        try {
            xsMapper.insert(xs);
        } catch (Exception e){
            return new JsonError("单据保存失败");
        }

        return new JsonData(xs);

    }

    public JsonObject appInsertTh(Th th) {
        String lsh = commonService.getLsh("TH");
        th.setLsh(lsh);
        th.setStatus(0);
        th.setLrsj(CommonService.getStringDate());
        try {
            thMapper.insert(th);
        } catch (Exception e){
            return new JsonError("单据保存失败");
        }

        return new JsonData(th);
    }

    public JsonObject appCommitXs(Xs xs) {
        try {
            xsMapper.updateStatusToCommited(xs);
        } catch (Exception e){
            return new JsonError("单据保存失败");
        }

        return new JsonData("提交成功");
    }

    public JsonObject appCommitTh(Th th){
        try {
            thMapper.updateStatusToCommited(th);
        } catch (Exception e){
            return new JsonError("单据保存失败");
        }
        return new JsonData("提交成功");
    }

    public JsonObject appQueryXs(String lsh) {
        Xs xs = new Xs();
        xs.setLsh(lsh);
        xs = xsMapper.selectOne(xs);
        return new JsonData(xs);
    }

    public JsonObject appQueryTh(String lsh) {
        Th th = new Th();
        th.setLsh(lsh);
        th =thMapper.selectOne(th);
        return new JsonData(th);
    }
}
