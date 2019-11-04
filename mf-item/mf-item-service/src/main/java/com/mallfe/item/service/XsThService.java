package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.json.JsonData;
import com.mallfe.common.json.JsonError;
import com.mallfe.common.json.JsonObject;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.*;
import com.mallfe.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
    GhMapper ghMapper;

    @Autowired
    CommonService commonService;

    @Autowired
    AllBillMapper allBillMapper;

    @Autowired
    ConsumerMapper consumerMapper;

    @Autowired
    UserService userService;

    @Autowired
    KucnMapper kucnMapper;


    public Xs insertXs(@NotNull Xs xs){
        String lsh = commonService.getLsh("XS");
        xs.setLsh(lsh);
        xs.setStatus(0);
        xs.setLrsj(CommonService.getStringDate());

        User user = userService.selectById(xs.getLrid());

        xs.setDeptCode(user.getDeptCode());
        xs.setStoreCode(user.getStoreCode());
        xs.setStorageCode(user.getStorageCode());

        try {
            xsMapper.insert(xs);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        return xs;
    }

    public Th insertTh(@NotNull Th th){
        String lsh = commonService.getLsh("TH");
        th.setLsh(lsh);
        th.setStatus(0);

        User user = userService.selectById(th.getLrid());

        th.setDeptCode(user.getDeptCode());
        th.setStoreCode(user.getStoreCode());
        th.setStorageCode(user.getStorageCode());
        th.setLrsj(CommonService.getStringDate());
        try {
            thMapper.insert(th);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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

    public PageResult<Xs> queryXsByPage(Integer page, Integer rows, String key, Long uid, Integer status) {
        //分页
        PageHelper.startPage(page, rows);

        //查询
        List<Xs> list = xsMapper.selectXsList(key,status,uid,null);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Xs> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public PageResult<Th> queryThByPage(Integer page, Integer rows, String key, Long uid, Integer status) {
        //分页
        PageHelper.startPage(page, rows);

        List<Th> list = thMapper.selectThList(key,status,uid,null);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Th> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public JsonObject commitXs(Xs xs){

        AllBill t = xsMapper.selectOneBill(xs.getLsh());

        try {
            if(kucnMapper.reduceRtKucn(t.getHh(),t.getSl(),t.getStorageCode(),t.getLx())!= 1){
                return new JsonError("库存不足，提交失败！");
            }

            kucnMapper.insertRtKucnLog(t.getHh(),t.getSl(),t.getStorageCode(),t.getDeptCode(),
                    t.getLx(),t.getLsh(),"XS");

            if(xsMapper.updateStatusToCommited(xs.getLsh(),xs.getCkuserid())!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new JsonError("单据状态异常，提交失败！");
            }
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new JsonError("系统异常，提交失败！");
        }
        return new JsonData("提交成功！");
    }

    public JsonObject deleteXs(Xs xs){
        try {
            if(xsMapper.updateStatusToCancel(xs.getLsh(),xs.getCkuserid())!=1){
                return new JsonError("单据状态异常，作废失败！");
            }
        } catch (Exception e){
            return new JsonError("系统异常，作废失败！");
        }
        return new JsonData("作废成功！");
    }

    public JsonObject commitTh(Th th){
        try {
            if(thMapper.updateStatusToCommited(th.getLsh(),th.getCkuserid())!=1){
                return new JsonError("单据状态异常，提交失败！");
            }
        } catch (Exception e){
            return new JsonError("系统异常，提交失败！");
        }
        return new JsonData("提交成功！");
    }

    public JsonObject deleteTh(Th th){
        try {
            if(thMapper.updateStatusToCancel(th.getLsh(),th.getCkuserid())!=1) {
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

    public JsonObject queryAll(Integer page, String lruserid, String phone, Integer hh, String lsh, String contact) {

        //查询用户角色
        List<AllBill> list;
        User u = userService.selectById(Long.parseLong(lruserid));

        //分页
        //临时调整
        PageHelper.startPage(page, 10);
        //条件过滤

        if(u.getLx()==1){


            //20191030升级为实现同品类商品的查询
            list = xsMapper.selectAllBill2(lruserid,phone,lsh,hh,contact);
        } else {
            list = xsMapper.selectAllBillByStore(u.getStoreCode(),phone,lsh,hh,contact,lruserid);
            //临时使用
            //list = xsMapper.selectAllBillForSh(u.getStoreCode(),phone,lsh,hh,contact,lruserid);
        }

        //查询

        if(CollectionUtils.isEmpty(list)){
            return new JsonError("未查询到单据！");
        }

        //解析分页结果
        PageInfo<AllBill> info = new PageInfo<>(list);

        return new JsonData(new PageResult<>(info.getTotal(), list));
    }

    public JsonObject querySh(Integer page, String lruserid, String phone, Integer hh, String lsh, String contact) {

        //查询用户角色

        User u = userService.selectById(Long.parseLong(lruserid));

        if(u.getLx()!=6 && u.getLx()!=7){
            return new JsonError("未查询到单据！");
        }

        //分页
        PageHelper.startPage(page, 10);
        //条件过滤

        List<AllBill> list = xsMapper.selectAllBillForSh(u.getStoreCode(),phone,lsh,hh,contact,lruserid);

        //查询

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

    public List<Xs> queryXsForPs(Long uid,String lsh,String rq) {
        List<Xs> list;

        if(StringUtils.isNotBlank(lsh)){
            list = xsMapper.selectXsWithLsh(lsh);
        } else{
            list = xsMapper.selectXsList(null,1,uid,rq);
        }

        return list;
    }


    public List<Th> queryThForTp(Long uid,String lsh,String rq) {
        List<Th> list;

        if(StringUtils.isNotBlank(lsh)){
            list = thMapper.selectThWithLsh(lsh);
        } else{
            Th th = new Th();
            th.setStatus(1);
            list = thMapper.selectThList(null,1,uid,rq);
        }
        return list;
    }

    public JsonObject appInsertXs(Xs xs) {

        if(xs.getProvince() == null || xs.getCity() == null || xs.getDistrict() == null){
            return new JsonError("单据保存失败");
        }

        if(StringUtils.isNotBlank(xs.getBillNumber())){
            if(xsMapper.selectBillNumberCount(xs.getBillNumber()) !=0 ){
                return new JsonError("单据号重复");
            }
        }

        if(StringUtils.isBlank(xs.getPicUrl())){
            xs.setPicUrl(null);
        }

        String lsh = commonService.getLsh("XS");
        xs.setLsh(lsh);
        xs.setStatus(0);

        User user = userService.selectById(xs.getLrid());

        xs.setDeptCode(user.getDeptCode());
        xs.setStoreCode(user.getStoreCode());
        xs.setStorageCode(user.getStorageCode());

        xs.setLrsj(CommonService.getStringDate());
        try {
            xsMapper.insert(xs);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new JsonError("单据保存失败");
        }

        return new JsonData(xs);

    }

    public JsonObject appInsertTh(Th th) {

        if(th.getProvince() == null || th.getCity() == null || th.getDistrict() == null){
            return new JsonError("单据保存失败");
        }

            if(StringUtils.isNotBlank(th.getBillNumber())){

                if(thMapper.selectBillNumberCount(th.getBillNumber()) !=0 ){
                return new JsonError("单据号重复");
            }
        }

        if(StringUtils.isBlank(th.getPicUrl())){
            th.setPicUrl(null);
        }

        String lsh = commonService.getLsh("TH");
        th.setLsh(lsh);
        th.setStatus(0);

        User user = userService.selectById(th.getLrid());

        th.setDeptCode(user.getDeptCode());
        th.setStoreCode(user.getStoreCode());
        th.setStorageCode(user.getStorageCode());
        th.setLrsj(CommonService.getStringDate());
        try {
            thMapper.insert(th);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new JsonError("单据保存失败");
        }

        return new JsonData(th);
    }

    public JsonObject appCommitXs(Xs xs) {
        AllBill t = xsMapper.selectOneBill(xs.getLsh());

        try {
            if(kucnMapper.reduceRtKucn(t.getHh(),t.getSl(),t.getStorageCode(),t.getLx())!= 1){
                return new JsonError("库存不足，提交失败！");
            }

            kucnMapper.insertRtKucnLog(t.getHh(),t.getSl(),t.getStorageCode(),t.getDeptCode(),
                    t.getLx(),t.getLsh(),"XS");

            if(xsMapper.updateStatusToCommited(xs.getLsh(),xs.getCkuserid())!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new JsonError("单据状态异常，提交失败！");
            }
        } catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new JsonError("系统异常，提交失败！");
        }
        return new JsonData("提交成功！");

    }

    public JsonObject appCommitTh(Th th){
        try {
            thMapper.updateStatusToCommited(th.getLsh(),th.getCkuserid());
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new JsonError("单据保存失败");
        }
        return new JsonData("提交成功");
    }

    public JsonObject appQueryXs(String lsh) {
        AllBill bill = xsMapper.selectOneBill(lsh);
        return new JsonData(bill);
    }

    public JsonObject appQueryTh(String lsh) {
        AllBill bill = xsMapper.selectOneBill(lsh);
        return new JsonData(bill);
    }

    public JsonObject getProvince() {
        List<Province> provinceList = xsMapper.selectProvince();
        return new JsonData(provinceList);
    }

    public JsonObject getCity(String province) {
        List<City> cityList = xsMapper.selectCity(province);
        return new JsonData(cityList);
    }

    public JsonObject getDistrict(String city) {
        List<District> districtList = xsMapper.selectDistrict(city);
        return new JsonData(districtList);
    }

    public JsonObject appInsertGh(Gh gh) {
        if(gh.getProvince() == null || gh.getCity() == null || gh.getDistrict() == null){
            return new JsonError("单据保存失败");
        }

        if(StringUtils.isNotBlank(gh.getBillNumber())){
            if(ghMapper.selectBillNumberCount(gh.getBillNumber()) !=0 ){
                return new JsonError("单据号重复");
            }
        }

        if(StringUtils.isBlank(gh.getPicUrl())){
            gh.setPicUrl(null);
        }

        String lsh = commonService.getLsh("GH");
        gh.setLsh(lsh);
        gh.setStatus(0);

        User user = userService.selectById(gh.getLrid());

        gh.setDeptCode(user.getDeptCode());
        gh.setStoreCode(user.getStoreCode());
        gh.setStorageCode(user.getStorageCode());

        gh.setLrsj(CommonService.getStringDate());
        try {
            ghMapper.insert(gh);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new JsonError("单据保存失败");
        }

        return new JsonData(gh);
    }

    public JsonObject appCommitGh(Gh gh) {
        AllBill t = ghMapper.selectOneBill(gh.getLsh());

        try {
            if(kucnMapper.reduceRtKucn(t.getHh(),t.getSl(),t.getStorageCode(),t.getLx())!= 1){
                return new JsonError("库存不足，提交失败！");
            }

            kucnMapper.insertRtKucnLog(t.getHh(),t.getSl(),t.getStorageCode(),t.getDeptCode(),
                    t.getLx(),t.getLsh(),"GH");

            if(ghMapper.updateStatusToCommited(gh.getLsh(),gh.getCkuserid())!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new JsonError("单据状态异常，提交失败！");
            }
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new JsonError("系统异常，提交失败！");
        }
        return new JsonData("提交成功！");
    }

    public JsonObject appQueryGh(String lsh) {
        AllBill bill = ghMapper.selectOneBill(lsh);
        return new JsonData(bill);
    }

    public JsonObject deleteGh(Gh gh) {
        try {
            if(ghMapper.updateStatusToCancel(gh.getLsh(),gh.getCkuserid())!=1) {
                return new JsonError("单据状态异常，作废失败！");
            }
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new JsonError("系统异常，作废失败！");
        }
        return new JsonData("作废成功！");
    }

    public List<Gh> queryGhForGh(Long uid, String lsh, String rq) {
        List<Gh> list;

        if(StringUtils.isNotBlank(lsh)){
            list = ghMapper.selectGhWithLsh(lsh);
        } else{
            list = ghMapper.selectGhList(null,1,uid,rq);
        }

        return list;
    }

    public PageResult<Gh> queryGhByPage(Integer page, Integer rows, String key, Long uid, Integer status) {
        //分页
        PageHelper.startPage(page, rows);

        List<Gh> list = ghMapper.selectGhList(key,status,uid,null);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Gh> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public void revertXs(Xs xs) {
        try {
            if(xsMapper.updateStatusToRevert(xs) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }



            AllBill bill = xsMapper.selectOneBill(xs.getLsh());

            if(kucnMapper.addRtKucn(bill.getHh(),bill.getSl(), bill.getStorageCode(), bill.getLx()) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            kucnMapper.insertRtKucnLog(bill.getHh(),bill.getSl()*(-1),bill.getStorageCode(),bill.getDeptCode(),
                    bill.getLx(),bill.getLsh(),"XSCX");
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void revertTh(Th th) {
        try {
            if(thMapper.updateStatusToRevert(th) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void revertGh(Gh gh) {
        try {
            if(ghMapper.updateStatusToRevert(gh) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            AllBill bill = xsMapper.selectOneBill(gh.getLsh());

            if(kucnMapper.addRtKucn(bill.getHh(),bill.getSl(), bill.getStorageCode(), bill.getLx()) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            kucnMapper.insertRtKucnLog(bill.getHh(),bill.getSl()*(-1),bill.getStorageCode(),bill.getDeptCode(),
                    bill.getLx(),bill.getLsh(),"GHCX");
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }
}
