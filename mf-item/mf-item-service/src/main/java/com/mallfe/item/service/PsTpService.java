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
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PsTpService {

    @Autowired
    private PsMapper psMapper;

    @Autowired
    private PsMxMapper psMxMapper;

    @Autowired
    private TpMapper tpMapper;

    @Autowired
    private TpMxMapper tpMxMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private XsMapper xsMapper;

    @Autowired
    private ThMapper thMapper;

    @Autowired
    private KucnMapper kucnMapper;

    @Autowired
    private KucnOutMapper kucnOutMapper;

    @Autowired
    private KucnInMapper kucnInMapper;

    @Autowired
    private PsrkMapper psrkMapper;

    @Autowired
    private TprkMapper tprkMapper;

    @Autowired
    private UserService userService;

    /**
     * 新增配送单
     * @param ps
     * @return
     */
    public void insertPs(Ps ps){


        ps.setStatus(0);
        ps.setLrsj(CommonService.getStringDate());

        try {
            for (PsDetail mx: ps.getList()) {
                //获取流水号
                String lsh = commonService.getLsh("PS");
                ps.setLsh(lsh);
                ps.setId(null);
                AllBill bill = xsMapper.selectOneBill(mx.getDdh());
                ps.setStoreCode(bill.getStorageCode());
                psMapper.insert(ps);
                //如果更新的行数不为1，则代表单据状态异常，回滚事务
                if(xsMapper.updateStatusToPs(mx.getDdh(),lsh,ps.getDriverCode(),ps.getPathCode())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
                psMxMapper.insertPsMx(lsh,mx.getDdh(),0);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    /**
     * 修改配送单
     * @param ps
     * @return
     */
    public Ps modifyPs(Ps ps){

        //修改销售单为待配送
        try {
            xsMapper.updateStatusToUnPs(ps.getLsh());
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //更新销售单状态
        try {
            for (PsDetail mx: ps.getList()) {
                //如果更新的行数不为1，则代表单据状态异常，回滚事务
                if(xsMapper.updateStatusToPs(mx.getDdh(),ps.getLsh(),ps.getDriverCode(),ps.getPathCode())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //删除配送明细
        Example example = new Example(PsDetail.class);
        example.createCriteria().andEqualTo("lsh",ps.getLsh());
        try {
            psMxMapper.deleteByExample(example);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }


        //插入配送明细
        try {
            for (PsDetail mx: ps.getList()) {
                psMxMapper.insertPsMx(ps.getLsh(),mx.getDdh(),0);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return ps;
    }


    /**
     * 新增退配单
     * @param tp
     * @return
     */
    public void insertTp(Tp tp){

        tp.setStatus(0);
        tp.setLrsj(CommonService.getStringDate());

        //更新销售单状态
        try {
            for (TpDetail mx: tp.getList()) {
                //获取流水号
                String lsh = commonService.getLsh("TP");
                tp.setLsh(lsh);
                tp.setId(null);
                //根据单据的销售仓库，生成配送单所属店铺
                AllBill bill = xsMapper.selectOneBill(mx.getDdh());
                tp.setStoreCode(bill.getStorageCode());
                tpMapper.insert(tp);
                //如果更新的行数不为1，则代表单据状态异常，回滚事务
                if(thMapper.updateStatusToTp(mx.getDdh(),lsh,tp.getDriverCode(),tp.getPathCode())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
                tpMxMapper.insertTpMx(lsh,mx.getDdh(),0);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public Tp modifyTp(Tp tp){

        //修改销售单为待配送
        try {
            for (TpDetail mx: tp.getList()) {
                thMapper.updateStatusToUnTp(tp.getLsh());
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //更新销售单状态
        try {
            for (TpDetail mx: tp.getList()) {
                //如果更新的行数不为1，则代表单据状态异常，回滚事务
                if(thMapper.updateStatusToTp(mx.getDdh(),tp.getLsh(),tp.getDriverCode(),tp.getPathCode())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //删除配送明细
        Example example = new Example(TpDetail.class);
        example.createCriteria().andEqualTo("lsh",tp.getLsh());
        try {
            tpMxMapper.deleteByExample(example);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }


        //插入配送明细
        try {
            for (TpDetail mx: tp.getList()) {
                tpMxMapper.insertTpMx(tp.getLsh(),mx.getDdh(),0);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return tp;
    }

    /**
     * 配送单作废
     * @param ps
     */
    public void deletePs(Ps ps) {
        //修改销售单为待配送
        try {
            xsMapper.updateStatusToUnPs(ps.getLsh());
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        try {
            psMapper.updateStatusToCancel(ps.getLsh());
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

    }

    public void deleteTp(Tp tp) {
        //修改销售单为待配送
        try {
            thMapper.updateStatusToUnTp(tp.getLsh());
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        try {
            tpMapper.updateStatusToCancel(tp.getLsh());
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public PageResult<Ps> queryPsByPage(Integer page, Integer rows, Integer status) {
        //分页
        PageHelper.startPage(page, rows);

        //查询
        List<Ps> list = psMapper.selectPs(status,null);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Ps> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public PageResult<Tp> queryTpByPage(Integer page, Integer rows, Integer status) {
        //分页
        PageHelper.startPage(page, rows);

        //查询
        List<Tp> list = tpMapper.selectTp(status,null);

        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Tp> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);

    }

    public Ps queryPsByLsh(String lsh) {
        Ps ps = new Ps();
        ps.setLsh(lsh);

        List<Ps> t = psMapper.selectPs(null,lsh);
        if(!t.isEmpty()){
            ps = t.get(0);
        }

        ps.setXsList(xsMapper.selectXsWithLsh(lsh));
        return ps;
    }

    public Tp queryTpByLsh(String lsh) {
        Tp tp = new Tp();
        tp.setLsh(lsh);

        List<Tp> t = tpMapper.selectTp(null,lsh);
        if(!t.isEmpty()){
            tp = t.get(0);
        }
        tp.setThList(thMapper.selectThWithLsh(lsh));
        return tp;
    }

    public void commitPs(Ps ps) {
        String  storeCode = ps.getStoreCode();
        try{
            if(psMapper.updateStatusToOut(ps.getLsh()) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            ps = queryPsByLsh(ps.getLsh());
            for(Xs mx : ps.getXsList()){
                Kucn kc = new Kucn();
                kc.setHh(mx.getHh());
                kc.setLx(mx.getLx());
                kc.setStoreCode(storeCode);
                Kucn result = kucnMapper.selectOne(kc);
                //更新库存
                if(result == null){
                    throw new MallfeException(ExceptionEnum.UNDER_STOCK);
                }
                else{
                    if(kucnMapper.reduceKucn(mx.getHh(),mx.getSl(),storeCode,mx.getLx())!=1){
                        throw new MallfeException(ExceptionEnum.UNDER_STOCK);
                    }
                }

                KucnOut kucnOut = new KucnOut();
                kucnOut.setHh(mx.getHh());
                kucnOut.setYwbm("PS");
                kucnOut.setSl(mx.getSl());
                kucnOut.setLsh(ps.getLsh());
                kucnOut.setLx(mx.getLx());
                kucnOut.setDeptCode(ps.getDeptCode());
                kucnOut.setStoreCode(ps.getStoreCode());
                //插入出库记录
                kucnOutMapper.insert(kucnOut);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void commitTp(Tp tp) {
        try{
            if(tpMapper.updateStatusToCommit(tp.getLsh()) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void arrivedPs(Ps ps) {
        try {
            //更新配送单状态
            if(psMapper.updateStatusToFinish(ps.getLsh())!=1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            //更新明细状态,先全部更新为2，再根据送达更新为1
            psMapper.updatePsmxStatusToUnFinish(ps.getLsh());

            for(PsDetail mx : ps.getList()){
                psMxMapper.updateStatus(ps.getLsh(),mx.getDdh(),mx.getStatus());
                xsMapper.updateStatusToArrival(mx.getDdh());
            }


            //插入配送入库信息
            if(psrkMapper.insertPsrkMx(ps.getLsh())!=0){
                if(psrkMapper.insertFromPs(ps.getLsh())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }

        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void arrivedTp(Tp tp) {
        try{
            if(tpMapper.updateStatusToFinish(tp.getLsh()) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            //修改所有退货单状态为待配车
            thMapper.updateStatusToUnTp(tp.getLsh());

            //修改退配单明细状态为未送达
            tpMapper.updateTpmxToUnFinish(tp.getLsh());


            //根据传进来的数据，修改退配单明细中状态
            for(TpDetail mx : tp.getList()){
                tpMxMapper.updateStatus(tp.getLsh(),mx.getDdh(),mx.getStatus());
                thMapper.updateStatusToArrival(mx.getDdh(),tp.getLsh(),tp.getDriverCode(),tp.getPathCode());
            }

            //插入配送入库信息
            if(tprkMapper.insertTprkMx(tp.getLsh())!=0){
                if(tprkMapper.insertFromTp(tp.getLsh())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }


        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void inStorePs(Psrk psrk) {

        try {
            //更新配送入库单状态
            if(psrkMapper.updatePsrkStatus(psrk.getLsh())!=1){
                throw new MallfeException(ExceptionEnum.BILL_STATUS_ERROR);
            }

            List<PsrkDetail> list = psrkMapper.selectPsrkMx(psrk.getLsh());

            for(PsrkDetail mx: list){
                Kucn kc = new Kucn();
                kc.setHh(mx.getHh());
                kc.setLx(mx.getLx());
                kc.setDeptCode(psrk.getDeptCode());
                kc.setStoreCode(psrk.getStoreCode());
                Kucn result = kucnMapper.selectOne(kc);
                //更新库存
                if(result == null){
                    kc.setKucn(mx.getSl());
                    kucnMapper.insert(kc);
                }
                else{
                    kucnMapper.addKucn(mx.getSl(),result.getId());
                }

                KucnIn kucnIn = new KucnIn();
                kucnIn.setYwbm("PSRK");
                kucnIn.setHh(mx.getHh());
                kucnIn.setSl(mx.getSl());
                kucnIn.setLsh(mx.getLsh());
                kucnIn.setLx(mx.getLx());
                kucnIn.setDeptCode(psrk.getDeptCode());
                kucnIn.setStoreCode(psrk.getStoreCode());
                //插入入库记录
                kucnInMapper.insert(kucnIn);

                //更新销售单状态
                xsMapper.updateStatusToUnPsByArrival(mx.getDdh());
            }


        } catch (Exception e) {
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

    }

    public PageResult<Psrk> queryPsrkByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key,
                                            Long uid) {
        //分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(Psrk.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("lsh",key+"%");
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Psrk> list = psrkMapper.selectPsrk(uid);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Psrk> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public Psrk queryPsrkByLsh(String lsh) {

        Psrk psrk = new Psrk();
        psrk.setLsh(lsh);
        psrk = psrkMapper.selectOne(psrk);

        psrk.setXsList(xsMapper.selectXsWithLshForRk(lsh));
        return psrk;

    }

    public void inStoreTp(Tprk tprk) {
        try {
            //更新退配入库单状态
            if(tprkMapper.updateTprkStatus(tprk.getLsh())!=1){
                throw new MallfeException(ExceptionEnum.BILL_STATUS_ERROR);
            }

            List<TprkDetail> list = tprkMapper.selectTprkMx(tprk.getLsh());

            for(TprkDetail mx: list){
                Kucn kc = new Kucn();
                kc.setHh(mx.getHh());
                kc.setLx(mx.getLx());
                kc.setDeptCode(tprk.getDeptCode());
                kc.setStoreCode(tprk.getStoreCode());
                Kucn result = kucnMapper.selectOne(kc);
                //更新库存
                if(result == null){
                    kc.setKucn(mx.getSl());
                    kucnMapper.insert(kc);
                }
                else{
                    kucnMapper.addKucn(mx.getSl(),result.getId());
                }

                if(kucnMapper.addRtKucn(mx.getHh(),mx.getSl(),tprk.getStoreCode(),mx.getLx())==0){
                    Kucn rt = new Kucn();
                    rt.setHh(mx.getHh());
                    rt.setLx(mx.getLx());
                    rt.setDeptCode(tprk.getDeptCode());
                    rt.setStoreCode(tprk.getStoreCode());
                    rt.setKucn(mx.getSl());
                    kucnMapper.insertRtKucn(rt);
                }

                KucnIn kucnIn = new KucnIn();
                kucnIn.setYwbm("TPRK");
                kucnIn.setHh(mx.getHh());
                kucnIn.setSl(mx.getSl());
                kucnIn.setLsh(mx.getLsh());
                kucnIn.setLx(mx.getLx());
                kucnIn.setStoreCode(tprk.getStoreCode());
                kucnIn.setDeptCode(tprk.getDeptCode());
                //插入入库记录
                kucnInMapper.insert(kucnIn);
            }
        } catch (Exception e) {
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public PageResult<Tprk> queryTprkByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(Psrk.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("lsh",key+"%");
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Tprk> list = tprkMapper.selectTprk();


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Tprk> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public Tprk queryTprkByLsh(String lsh) {
        Tprk tprk = new Tprk();
        tprk.setLsh(lsh);
        tprk = tprkMapper.selectOne(tprk);

        tprk.setThList(thMapper.selectThWithLshForRk(lsh));
        return tprk;
    }

    public JsonObject applist(Integer page, String userid, String phone, Integer hh, String lsh, String psdh) {

        //分页
        PageHelper.startPage(page, 10);
        //条件过滤

        //查询用户角色
        List<AllBill> list;
        User u = userService.selectById(Long.parseLong(userid));
        String driveCode = u.getUsername();

        list = psMapper.selectList(driveCode,phone,hh,lsh,psdh,0,1);

        //查询

        if(CollectionUtils.isEmpty(list)){
            return new JsonError("未查询到单据！");
        }

        //解析分页结果
        PageInfo<AllBill> info = new PageInfo<>(list);

        return new JsonData(new PageResult<>(info.getTotal(), list));
    }

    public JsonObject appPsArrive(String psdh) {
        List<AllBill> billList = psMapper.selectList(null,null,null,null,psdh,0,1);

        if(billList == null){
            return new JsonError("操作失败！");
        }

        AllBill bill = billList.get(0);

        if(bill.getBilltype().equals("PS")){
            Ps ps = queryPsByLsh(psdh);
            //准备数据
            List<PsDetail> list = new ArrayList<>();
            PsDetail psDetail = new PsDetail();
            psDetail.setLsh(psdh);
            psDetail.setDdh(ps.getXsList().get(0).getLsh());
            psDetail.setStatus(1);
            list.add(psDetail);
            ps.setList(list);
            //准备数据完毕
            arrivedPs(ps);
        }
        else {
            Tp tp = queryTpByLsh(psdh);
            //准备数据
            List<TpDetail> list = new ArrayList<>();
            TpDetail tpDetail = new TpDetail();
            tpDetail.setLsh(psdh);
            tpDetail.setDdh(tp.getThList().get(0).getLsh());
            tpDetail.setStatus(1);
            list.add(tpDetail);
            tp.setList(list);
            //准备数据完毕
            arrivedTp(tp);
        }

        return new JsonData("提交成功！");
    }

    public JsonObject appPsNotArrive(String psdh) {
        List<AllBill> billList = psMapper.selectList(null,null,null,null,psdh,0,1);

        if(billList == null){
            return new JsonError("操作失败！");
        }

        AllBill bill = billList.get(0);

        if(bill.getBilltype().equals("PS")){
            if(psMapper.updateStatusToFinish(psdh)!=1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }
            psMapper.updatePsmxStatusToUnFinish(psdh);
            if(psrkMapper.insertPsrkMx(psdh)!=0){
                if(psrkMapper.insertFromPs(psdh)!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }
        }
        else {
            if(tpMapper.updateStatusToFinish(psdh) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            //修改所有退货单状态为待配车
            thMapper.updateStatusToUnTp(psdh);

            //修改退配单明细状态为未送达
            tpMapper.updateTpmxToUnFinish(psdh);

        }

        return new JsonData("提交成功！");

    }

    public JsonObject appmx(String psdh) {
        List<AllBill> list = psMapper.selectList(null,null,null,null,psdh,null,null);
        if(CollectionUtils.isEmpty(list)){
            return new JsonError("未查询到单据！");
        }

        return new JsonData(list.get(0));

    }

    public JsonObject applisthis(Integer page, String userid, String phone, Integer hh, String lsh, String psdh) {
        //分页
        PageHelper.startPage(page, 10);
        //条件过滤

        //查询用户角色
        List<AllBill> list;
        User u = userService.selectById(Long.parseLong(userid));
        String driveCode = u.getUsername();

        list = psMapper.selectList(driveCode,phone,hh,lsh,psdh,null,2);

        //查询

        if(CollectionUtils.isEmpty(list)){
            return new JsonError("未查询到单据！");
        }

        //解析分页结果
        PageInfo<AllBill> info = new PageInfo<>(list);

        return new JsonData(new PageResult<>(info.getTotal(), list));
    }

    public PageResult<Ps> queryPsckByPage(Integer page, Integer rows, Integer status, Long uid) {
        //分页
        PageHelper.startPage(page, rows);

        //查询
        List<Ps> list = psMapper.selectPsWithUid(status,null,uid);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Ps> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }
}
