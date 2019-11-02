package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.CkdbMapper;
import com.mallfe.item.mapper.KucnInMapper;
import com.mallfe.item.mapper.KucnMapper;
import com.mallfe.item.mapper.KucnOutMapper;
import com.mallfe.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-08-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CkdbService {
    @Autowired
    CkdbMapper ckdbMapper;

    @Autowired
    CommonService commonService;

    @Autowired
    KucnMapper kucnMapper;

    @Autowired
    private KucnInMapper kucnInMapper;

    @Autowired
    private KucnOutMapper kucnOutMapper;

    public Ckdb insert(Ckdb ckdb) {

        //获取流水号
        String lsh = commonService.getLsh("CKDB");
        ckdb.setLsh(lsh);
        try{
            ckdbMapper.insertBill(ckdb);

            ckdbMapper.insertMx(ckdb);
        }
        catch (Exception e ){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }

        return ckdb;
    }

    public Ckdb update(Ckdb ckdb) {

        Ckdb c = ckdbMapper.selectBill(ckdb.getLsh(),null,null).get(0);

        if(c == null){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        if(c.getStatus()!=0){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }
        try{
            ckdbMapper.deleteMx(ckdb.getLsh());
            ckdbMapper.insertMx(ckdb);
        }
        catch (Exception e){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }

        return ckdb;
    }

    public PageResult<Ckdb> page(Integer page, Integer rows, String lsh, Integer hh,Integer status) {
        //分页
        PageHelper.startPage(page, rows);

        List<Ckdb> list = ckdbMapper.selectBill(lsh,hh,status);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Ckdb> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);

    }

    public void commitBill(String lsh) {
        Ckdb ckdb = ckdbMapper.selectBill(lsh,null,0).get(0);

        if(ckdb == null){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }

        ckdbMapper.updateStatus(lsh,1,0);
    }


    public void finishBilll(Ckdb ckdb){
        ckdb = ckdbMapper.selectBill(ckdb.getLsh(),null,2).get(0);

        if(ckdb == null){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }

        //终审使用专用方法
        ckdbMapper.updateStatusToCkFinksh(ckdb.getLsh());


        for(CkdbDetail mx : ckdbMapper.selectMx(ckdb.getLsh())){

            //如果冲减库存失败，抛出失败
            if(kucnMapper.reduceKucn(mx.getHh(),mx.getSl(),ckdb.getOutStoreCode(),ckdb.getLx())!=1){
                throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
            }

            //借用返厂冲减库存方法，允许即时库存小于0
            if(kucnMapper.fcReduceRtKucn(mx.getHh(),mx.getSl(),ckdb.getOutStoreCode(),ckdb.getLx())!=1){
                throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
            }

            kucnMapper.insertRtKucnLog(mx.getHh(),mx.getSl(),ckdb.getOutStoreCode(),ckdb.getDeptCode(),
                    ckdb.getLx(),ckdb.getLsh(),"CKDB");


            Kucn kc = new Kucn();
            kc.setHh(mx.getHh());
            kc.setLx(ckdb.getLx());
            //增加区域和店铺信息
            kc.setDeptCode(ckdb.getDeptCode());
            kc.setStoreCode(ckdb.getInStoreCode());
            Kucn result = kucnMapper.selectOne(kc);
            //更新库存
            if(result == null){
                kc.setKucn(mx.getSl());
                kucnMapper.insert(kc);
            }
            else{
                kucnMapper.addKucn(mx.getSl(),result.getId());
            }

            if(kucnMapper.addRtKucn(mx.getHh(),mx.getSl(),ckdb.getInStoreCode(),ckdb.getLx())==0){
                Kucn rt = new Kucn();
                rt.setHh(mx.getHh());
                rt.setLx(ckdb.getLx());
                rt.setDeptCode(ckdb.getDeptCode());
                rt.setStoreCode(ckdb.getInStoreCode());
                rt.setKucn(mx.getSl());
                kucnMapper.insertRtKucn(rt);
            }

            kucnMapper.insertRtKucnLog(mx.getHh(),mx.getSl()*(-1),ckdb.getInStoreCode(),ckdb.getDeptCode(),
                    ckdb.getLx(),ckdb.getLsh(),"CKDB");

            KucnOut kucnOut = new KucnOut();
            kucnOut.setYwbm("CKDB");
            kucnOut.setHh(mx.getHh());
            kucnOut.setSl(mx.getSl());
            kucnOut.setLsh(ckdb.getLsh());
            kucnOut.setLx(ckdb.getLx());
            kucnOut.setDeptCode(ckdb.getDeptCode());
            kucnOut.setStoreCode(ckdb.getOutStoreCode());
            //插入出库记录
            kucnOutMapper.insert(kucnOut);

            KucnIn kucnIn = new KucnIn();
            kucnIn.setYwbm("CKDB");
            kucnIn.setHh(mx.getHh());
            kucnIn.setSl(mx.getSl());
            kucnIn.setLsh(ckdb.getLsh());
            kucnIn.setLx(ckdb.getLx());
            kucnIn.setStoreCode(ckdb.getInStoreCode());
            kucnIn.setDeptCode(ckdb.getDeptCode());
            //插入入库记录
            kucnInMapper.insert(kucnIn);
        }
    }

    public Ckdb bill(String lsh) {

        Ckdb ckdb = ckdbMapper.selectBill(lsh,null,null).get(0);

        if(ckdb == null){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        ckdb.setList(ckdbMapper.selectMx(lsh));

        return ckdb;
    }

    public void shenhe1(String lsh) {
        Ckdb ckdb = ckdbMapper.selectBill(lsh,null,1).get(0);

        if(ckdb == null){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }

        ckdbMapper.updateStatus(lsh,2,1);
    }

    public void shenhe2(String lsh) {
        Ckdb ckdb = ckdbMapper.selectBill(lsh,null,2).get(0);

        if(ckdb == null){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }

        ckdbMapper.updateStatus(lsh,3,2);
    }

    public void delete(String lsh) {
        if(ckdbMapper.updateStatus(lsh,9,0)!=1){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }
    }

    public void reject(Ckdb ckdb) {
        if(ckdbMapper.updateStatusToZero(ckdb.getLsh())!=1){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }
}
