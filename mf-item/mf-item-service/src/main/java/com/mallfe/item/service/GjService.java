package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.GjMapper;
import com.mallfe.item.mapper.GjMxMapper;
import com.mallfe.item.mapper.KucnInMapper;
import com.mallfe.item.mapper.KucnMapper;
import com.mallfe.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GjService {


    @Autowired
    private GjMapper gjMapper;

    @Autowired
    private GjMxMapper gjMxMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private KucnMapper kucnMapper;

    @Autowired
    private KucnInMapper kucnInMapper;

    @Autowired
    private UserService userService;

    /**
     * 新增购进单
     * @param gj
     * @return
     */
    public Gj insertBill(Gj gj){
        //获取流水号
        String lsh = commonService.getLsh("GJ");
        gj.setLsh(lsh);

        if(gj.getLx() == null){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        if(gj.getStoreCode() == null){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        User user = userService.selectById(gj.getLrid());

        gj.setDeptCode(user.getDeptCode());
        //gj.setStoreCode(user.getStoreCode());
        gj.setLrsj(CommonService.getStringDate());
        //插入单据
        try {
            gjMapper.insert(gj);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //插入明细
        try {
            for (GjDetail mx: gj.getList()) {
                gjMxMapper.insertMx(lsh,mx.getHh(),mx.getSl());
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return gj;
    }

    /**
     * 提交购进单
     * @param gj
     * @return
     */
    public void commitBill(Gj gj){
        Gj g = new Gj();
        g.setLsh(gj.getLsh());
        g.setStatus(0);

        g=gjMapper.selectBill(gj.getLsh(),null).get(0);

        if(g == null){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        try {
            //更新单据状态
            if(gjMapper.updateBillStatus(1,gj.getLsh())!=1){
                throw new MallfeException(ExceptionEnum.BILL_STATUS_ERROR);
            }
        }
        catch (Exception e){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }
    }



    public PageResult<Gj> queryGjByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key, Integer status) {
        //分页
        PageHelper.startPage(page, rows);

        List<Gj> list = gjMapper.selectBill(key,status);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Gj> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);

    }



    public Gj queryBill(String lsh) {
        Gj gj = new Gj();
        gj.setLsh(lsh);

        gj = gjMapper.selectBill(lsh,null).get(0);

        if(gj == null){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        List<GjDetail> list = gjMxMapper.getMx(lsh);

        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_DETAIL_NOT_EXISTS);
        }

        gj.setList(list);

        return gj;
    }

    public void deleteBill(Gj gj) {
        try {
            if(gjMapper.updateBillStatus(9,gj.getLsh())!=1){
                throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
            }
        }
        catch (Exception e){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }
    }

    public void modifyBill(Gj gj) {

        Gj g = new Gj();
        g.setLsh(gj.getLsh());
        g.setStatus(0);

        g=gjMapper.selectBill(gj.getLsh(),null).get(0);

        if(g == null){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        if(g.getStatus()!=0){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        try{
            //删除明细
            gjMxMapper.deleteMx(gj.getLsh());
            //插入明细
            for (GjDetail mx: gj.getList()) {
                gjMxMapper.insertMx(gj.getLsh(),mx.getHh(),mx.getSl());
            }
        }
        catch (Exception e){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }
    }

    public void shenhe1(Gj gj) {
        Gj g = new Gj();
        g.setLsh(gj.getLsh());
        g.setStatus(1);

        g=gjMapper.selectBill(gj.getLsh(),null).get(0);

        if(g == null){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        try {
            //更新单据状态
            if(gjMapper.updateBillStatus(2,gj.getLsh())!=1){
                throw new MallfeException(ExceptionEnum.BILL_STATUS_ERROR);
            }
        }
        catch (Exception e){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }
    }

    public void shenhe2(Gj gj) {
        try {
            //更新单据状态
            if(gjMapper.updateStatusToFinish(gj.getLsh())!=1){
                throw new MallfeException(ExceptionEnum.BILL_STATUS_ERROR);
            }

            for(GjDetail mx: gj.getList()){
                Kucn kc = new Kucn();
                kc.setHh(mx.getHh());
                kc.setLx(gj.getLx());
                //增加区域和店铺信息
                kc.setDeptCode(gj.getDeptCode());
                kc.setStoreCode(gj.getStoreCode());
                Kucn result = kucnMapper.selectOne(kc);
                //更新库存
                if(result == null){
                    kc.setKucn(mx.getSl());
                    kucnMapper.insert(kc);
                }
                else{
                    kucnMapper.addKucn(mx.getSl(),result.getId());
                }

                if(kucnMapper.addRtKucn(mx.getHh(),mx.getSl(),gj.getStoreCode(),gj.getLx())==0){
                    Kucn rt = new Kucn();
                    rt.setHh(mx.getHh());
                    rt.setLx(gj.getLx());
                    rt.setDeptCode(gj.getDeptCode());
                    rt.setStoreCode(gj.getStoreCode());
                    rt.setKucn(mx.getSl());
                    kucnMapper.insertRtKucn(rt);
                }

                kucnMapper.insertRtKucnLog(mx.getHh(),mx.getSl()*(-1),gj.getStoreCode(),gj.getDeptCode(),
                        gj.getLx(),gj.getLsh(),"GJ");

                KucnIn kucnIn = new KucnIn();
                kucnIn.setYwbm("GJ");
                kucnIn.setHh(mx.getHh());
                kucnIn.setSl(mx.getSl());
                kucnIn.setLsh(gj.getLsh());
                kucnIn.setLx(gj.getLx());
                kucnIn.setStoreCode(gj.getStoreCode());
                kucnIn.setDeptCode(gj.getDeptCode());
                //插入入库记录
                kucnInMapper.insert(kucnIn);
            }
        } catch (Exception e) {
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public void reject(Gj gj) {
        if(gjMapper.updateStatusToZero(gj.getLsh())!=1){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }
}
