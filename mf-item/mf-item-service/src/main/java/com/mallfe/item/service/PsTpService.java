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
    private GhpsMapper ghpsMapper;

    @Autowired
    private PsMxMapper psMxMapper;

    @Autowired
    private GhpsMxMapper ghpsMxMapper;

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
    private GhMapper ghMapper;

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
    private GhpsrkMapper ghpsrkMapper;

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
                ps.setDeptCode(bill.getDeptCode());
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
     * @param ps 配送单信息
     */
    public void modifyPs(Ps ps){

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
    }

    /**
     * 插入往返配送单
     * @param ghps 单据信息
     */
    public void insertGhps(Ghps ghps) {
        ghps.setStatus(0);
        ghps.setLrsj(CommonService.getStringDate());

        try {
            for (GhpsDetail mx: ghps.getList()) {
                //获取流水号
                String lsh = commonService.getLsh("GHPS");
                ghps.setLsh(lsh);
                ghps.setId(null);
                AllBill bill = xsMapper.selectOneBill(mx.getDdh());
                ghps.setStoreCode(bill.getStorageCode());
                ghps.setDeptCode(bill.getDeptCode());
                ghpsMapper.insert(ghps);
                //如果更新的行数不为1，则代表单据状态异常，回滚事务
                if(ghMapper.updateStatusToGhps(mx.getDdh(),lsh,ghps.getDriverCode(),ghps.getPathCode())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
                ghpsMxMapper.insertGhpsMx(lsh,mx.getDdh(),0);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    /**
     * 修改往返配送单
     * @param ghps 往返配送单
     */
    public void modifyGhps(Ghps ghps){

        //修改销售单为待配送
        try {
            ghMapper.updateStatusToUnGhps(ghps.getLsh());
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //更新销售单状态
        try {
            for (GhpsDetail mx: ghps.getList()) {
                //如果更新的行数不为1，则代表单据状态异常，回滚事务
                if(ghMapper.updateStatusToGhps(mx.getDdh(),ghps.getLsh(),ghps.getDriverCode(),ghps.getPathCode())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //删除配送明细
        Example example = new Example(GhpsDetail.class);
        example.createCriteria().andEqualTo("lsh",ghps.getLsh());
        try {
            ghpsMxMapper.deleteByExample(example);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }


        //插入配送明细
        try {
            for (GhpsDetail mx: ghps.getList()) {
                ghpsMxMapper.insertGhpsMx(ghps.getLsh(),mx.getDdh(),0);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    /**
     * 新增退配单
     * @param tp 退配单信息
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
                tp.setDeptCode(bill.getDeptCode());
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

    /**
     * 修改退配单
     * @param tp 退配单信息
     */
    public void modifyTp(Tp tp){

        //修改销售单为待配送
        try {
            thMapper.updateStatusToUnTp(tp.getLsh());
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
    }

    /**
     * 配送单作废
     * @param ps 流水号字段
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

    /**
     * 作废退配单
     * @param tp 流水号字段
     */
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

    /**
     * 作废往返配送单
     * @param ghps 流水号字段
     */
    public void deleteGhps(Ghps ghps) {
        //修改销售单为待配送
        try {
            ghMapper.updateStatusToUnGhps(ghps.getLsh());
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        try {
            ghpsMapper.updateStatusToCancel(ghps.getLsh());
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }
    public PageResult<Ps> queryPsByPage(Integer page, Integer rows, Integer status,String key) {
        //分页
        PageHelper.startPage(page, rows);

        //查询
        List<Ps> list = psMapper.selectPs(status,null,key);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Ps> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public PageResult<Tp> queryTpByPage(Integer page, Integer rows, Integer status, String key) {
        //分页
        PageHelper.startPage(page, rows);

        //查询
        List<Tp> list = tpMapper.selectTp(status,null,key);

        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Tp> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);

    }

    public PageResult<Ghps> queryGhpsByPage(Integer page, Integer rows, Integer status, String key) {
        //分页
        PageHelper.startPage(page, rows);

        //查询
        List<Ghps> list = ghpsMapper.selectGhps(status,null, key);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Ghps> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public Ps queryPsByLsh(String lsh) {
        Ps ps = new Ps();
        ps.setLsh(lsh);

        List<Ps> t = psMapper.selectPs(null,lsh,null);
        if(!t.isEmpty()){
            ps = t.get(0);
        }

        ps.setXsList(xsMapper.selectXsWithLsh(lsh));
        return ps;
    }

    public Tp queryTpByLsh(String lsh) {
        Tp tp = new Tp();
        tp.setLsh(lsh);

        List<Tp> t = tpMapper.selectTp(null,lsh,null);
        if(!t.isEmpty()){
            tp = t.get(0);
        }
        tp.setThList(thMapper.selectThWithLsh(lsh));
        return tp;
    }

    public Ghps queryGhpsByLsh(String lsh) {
        Ghps ghps = new Ghps();
        ghps.setLsh(lsh);

        List<Ghps> t = ghpsMapper.selectGhps(null,lsh,null);
        if(!t.isEmpty()){
            ghps = t.get(0);
        }

        ghps.setGhList(ghMapper.selectGhWithLsh(lsh));
        return ghps;
    }

    public void commitPs(Ps ps) {

        try{
            if(psMapper.updateStatusToOut(ps.getLsh()) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            ps = queryPsByLsh(ps.getLsh());
            String  storeCode = ps.getStoreCode();
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

    public void commitGhps(Ghps ghps) {

        try{
            if(ghpsMapper.updateStatusToOut(ghps.getLsh()) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            ghps = queryGhpsByLsh(ghps.getLsh());
            String  storeCode = ghps.getStoreCode();
            for(Gh mx : ghps.getGhList()){
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
                kucnOut.setYwbm("GHPS");
                kucnOut.setSl(mx.getSl());
                kucnOut.setLsh(ghps.getLsh());
                kucnOut.setLx(mx.getLx());
                kucnOut.setDeptCode(ghps.getDeptCode());
                kucnOut.setStoreCode(ghps.getStoreCode());
                //插入出库记录
                kucnOutMapper.insert(kucnOut);
            }
        } catch (Exception e){
            e.printStackTrace();
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
            //thMapper.updateStatusToUnTp(tp.getLsh());

            //修改退配单明细状态为未送达
            //tpMapper.updateTpmxToUnFinish(tp.getLsh());


            //根据传进来的数据，修改退配单明细中状态
            for(TpDetail mx : tp.getList()){
                tpMxMapper.updateStatus(tp.getLsh(),mx.getDdh(),mx.getStatus());
                thMapper.updateStatusToArrival(mx.getDdh());
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

    public void arrivedGhps(Ghps ghps) {
        try{
            if(ghpsMapper.updateStatusToFinish(ghps.getLsh()) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            //修改所有退货单状态为待配车
            //ghMapper.updateStatusToUnGhps(ghps.getLsh());

            //修改退配单明细状态为未送达
            //ghpsMapper.updateGhpsmxStatusToUnFinish(ghps.getLsh());


            //根据传进来的数据，修改退配单明细中状态
            for(GhpsDetail mx : ghps.getList()){
                ghpsMxMapper.updateStatus(ghps.getLsh(),mx.getDdh(),mx.getStatus());
                ghMapper.updateStatusToArrival(mx.getDdh());
            }

            //插入配送入库信息
            if(ghpsrkMapper.insertGhpsrkMx(ghps.getLsh())!=0){
                if(ghpsrkMapper.insertFromGhps(ghps.getLsh())!=1){
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
        List<Psrk> list = psrkMapper.selectPsrk(uid,2);


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

    public Ghpsrk queryGhpsrkByLsh(String lsh) {

        Ghpsrk ghpsrk = new Ghpsrk();
        ghpsrk.setLsh(lsh);
        ghpsrk = ghpsrkMapper.selectOne(ghpsrk);

        ghpsrk.setGhList(ghMapper.selectGhWithLshForRk(lsh));
        return ghpsrk;

    }

    public void inStoreTp(Tprk tprk) {
        Integer newLx = tprk.getNewLx();
        try {
            //更新退配入库单状态
            if(tprkMapper.updateTprkStatus(tprk.getLsh())!=1){
                throw new MallfeException(ExceptionEnum.BILL_STATUS_ERROR);
            }

            if(tprkMapper.updateTprkMxLx(tprk.getLsh(),newLx)!=1){
                throw new MallfeException(ExceptionEnum.BILL_STATUS_ERROR);
            }

            tprk = tprkMapper.selectOneBill(tprk.getLsh());

            List<TprkDetail> list = tprkMapper.selectTprkMx(tprk.getLsh());

            for(TprkDetail mx: list){
                Kucn kc = new Kucn();
                kc.setHh(mx.getHh());
                kc.setLx(newLx);
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

                if(kucnMapper.addRtKucn(mx.getHh(),mx.getSl(),tprk.getStoreCode(),newLx)==0){
                    Kucn rt = new Kucn();
                    rt.setHh(mx.getHh());
                    rt.setLx(newLx);
                    rt.setDeptCode(tprk.getDeptCode());
                    rt.setStoreCode(tprk.getStoreCode());
                    rt.setKucn(mx.getSl());
                    kucnMapper.insertRtKucn(rt);
                }

                kucnMapper.insertRtKucnLog(mx.getHh(),mx.getSl()*(-1),tprk.getStoreCode(),tprk.getDeptCode(),
                        newLx,tprk.getLsh(),"TPRK");

                KucnIn kucnIn = new KucnIn();
                kucnIn.setYwbm("TPRK");
                kucnIn.setHh(mx.getHh());
                kucnIn.setSl(mx.getSl());
                kucnIn.setLsh(mx.getLsh());
                kucnIn.setLx(newLx);
                kucnIn.setStoreCode(tprk.getStoreCode());
                kucnIn.setDeptCode(tprk.getDeptCode());
                //插入入库记录
                kucnInMapper.insert(kucnIn);
            }
        } catch (Exception e) {
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void inStoreGhps(Ghpsrk ghpsrk) {
        Integer newLx = ghpsrk.getNewLx();
        try {
            //更新退配入库单状态
            if(ghpsrkMapper.updateGhpsrkStatus(ghpsrk.getLsh())!=1){
                throw new MallfeException(ExceptionEnum.BILL_STATUS_ERROR);
            }

            if(ghpsrkMapper.updateGhpsrkMxLx(ghpsrk.getLsh(),newLx)!=1){
                throw new MallfeException(ExceptionEnum.BILL_STATUS_ERROR);
            }

            List<GhpsrkDetail> list = ghpsrkMapper.selectGhpsrkMx(ghpsrk.getLsh());

            for(GhpsrkDetail mx: list){
                Kucn kc = new Kucn();
                kc.setHh(mx.getHh());
                kc.setLx(newLx);
                kc.setDeptCode(ghpsrk.getDeptCode());
                kc.setStoreCode(ghpsrk.getStoreCode());
                Kucn result = kucnMapper.selectOne(kc);
                //更新库存
                if(result == null){
                    kc.setKucn(mx.getSl());
                    kucnMapper.insert(kc);
                }
                else{
                    kucnMapper.addKucn(mx.getSl(),result.getId());
                }



                //未送达，不修改即时库存
                if(mx.getStatus()== 2){
                    ghMapper.updateStatusToUnGhps(ghpsrk.getLsh());
                }
                else{
                    if(kucnMapper.addRtKucn(mx.getHh(),mx.getSl(),ghpsrk.getStoreCode(),newLx)==0){
                        Kucn rt = new Kucn();
                        rt.setHh(mx.getHh());
                        rt.setLx(newLx);
                        rt.setDeptCode(ghpsrk.getDeptCode());
                        rt.setStoreCode(ghpsrk.getStoreCode());
                        rt.setKucn(mx.getSl());
                        kucnMapper.insertRtKucn(rt);
                    }

                    kucnMapper.insertRtKucnLog(mx.getHh(),mx.getSl()*(-1),ghpsrk.getStoreCode(),ghpsrk.getDeptCode(),
                            newLx,ghpsrk.getLsh(),"GHPSRK");
                }


                KucnIn kucnIn = new KucnIn();
                kucnIn.setYwbm("GHPSRK");
                kucnIn.setHh(mx.getHh());
                kucnIn.setSl(mx.getSl());
                kucnIn.setLsh(mx.getLsh());
                kucnIn.setLx(newLx);
                kucnIn.setStoreCode(ghpsrk.getStoreCode());
                kucnIn.setDeptCode(ghpsrk.getDeptCode());
                //插入入库记录
                kucnInMapper.insert(kucnIn);
            }
        } catch (Exception e) {
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public PageResult<Tprk> queryTprkByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key,
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
        List<Tprk> list = tprkMapper.selectTprk(uid,2);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Tprk> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public PageResult<Tprk> queryTprkhisByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key,
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
        List<Tprk> list = tprkMapper.selectTprk(uid,3);


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

    public PageResult<Ghpsrk> queryGhpsrkByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key,
                                            Long uid) {
        //分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(Ghpsrk.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("lsh",key+"%");
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Ghpsrk> list = ghpsrkMapper.selectGhpsrk(uid,2);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Ghpsrk> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
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

    public JsonObject appPsArrive(String psdh,String sdpicUrl) {
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
            //更新送达照片
            if(StringUtils.isNotBlank(sdpicUrl)){
                xsMapper.updateSdpicUrl(ps.getXsList().get(0).getLsh(),sdpicUrl);
            }

        }
        else if(bill.getBilltype().equals("TP")){
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
            //更新送达照片
            if(!StringUtils.isNotBlank(sdpicUrl)){
                thMapper.updateSdpicUrl(tp.getThList().get(0).getLsh(),sdpicUrl);
            }

        }
        else {
            Ghps ghps = queryGhpsByLsh(psdh);
            List<GhpsDetail> list = new ArrayList<>();
            GhpsDetail ghpsDetail = new GhpsDetail();
            ghpsDetail.setLsh(psdh);
            ghpsDetail.setDdh(ghps.getGhList().get(0).getLsh());
            ghpsDetail.setStatus(1);
            list.add(ghpsDetail);
            ghps.setList(list);
            //准备数据完毕
            arrivedGhps(ghps);
            //更新送达照片
            if(!StringUtils.isNotBlank(sdpicUrl)){
                ghMapper.updateSdpicUrl(ghps.getGhList().get(0).getLsh(),sdpicUrl);
            }

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
        else if(bill.getBilltype().equals("TP")){
            if(tpMapper.updateStatusToFinish(psdh) != 1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }

            //修改所有退货单状态为待配车
            thMapper.updateStatusToUnTp(psdh);

            //修改退配单明细状态为未送达
            tpMapper.updateTpmxToUnFinish(psdh);

        }
        else {
            if(ghpsMapper.updateStatusToFinish(psdh)!=1){
                throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
            }
            ghpsMapper.updateGhpsmxStatusToUnFinish(psdh);
            if(ghpsrkMapper.insertGhpsrkMx(psdh)!=0){
                if(ghpsrkMapper.insertFromGhps(psdh)!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }
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

    public PageResult<Tp> queryTpfcByPage(Integer page, Integer rows, Integer status, Long uid) {
        //分页
        PageHelper.startPage(page, rows);

        //查询
        List<Tp> list = tpMapper.selectTpWithUid(status,null,uid);

        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Tp> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public PageResult<Ghps> queryGhpsckByPage(Integer page, Integer rows, Integer status, Long uid) {
        //分页
        PageHelper.startPage(page, rows);

        //查询
        List<Ghps> list = ghpsMapper.selectGhpsWithUid(status,null,uid);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Ghps> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public PageResult<Psrk> queryPsrkhisByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key, Long uid) {
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
        List<Psrk> list = psrkMapper.selectPsrk(uid,3);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Psrk> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public PageResult<Ghpsrk> queryGhpsrkhisByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key, Long uid) {
        //分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(Ghpsrk.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("lsh",key+"%");
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Ghpsrk> list = ghpsrkMapper.selectGhpsrk(uid,3);


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Ghpsrk> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public JsonObject updateSdpic(String psdh, String sdpicUrl) {

        List<AllBill> billList = psMapper.selectList(null,null,null,null,psdh,null,null);

        if(CollectionUtils.isEmpty(billList)){
            return new JsonError("操作失败！");
        }

        AllBill bill = billList.get(0);

        if(bill.getBilltype().equals("PS")){
            //更新送达照片
            xsMapper.updateSdpicUrl(bill.getLsh(),sdpicUrl);
        }
        else if(bill.getBilltype().equals("TP")){
            //更新送达照片
            thMapper.updateSdpicUrl(bill.getLsh(),sdpicUrl);
        }
        else {
            //更新送达照片
            ghMapper.updateSdpicUrl(bill.getLsh(),sdpicUrl);
        }

        return new JsonData("提交成功！");
    }
}
