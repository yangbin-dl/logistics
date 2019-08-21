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
import com.mallfe.item.pojo.Gj;
import com.mallfe.item.pojo.GjDetail;
import com.mallfe.item.pojo.Kucn;
import com.mallfe.item.pojo.KucnIn;
import org.springframework.beans.BeanUtils;
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

    /**
     * 新增购进单
     * @param gj
     * @return
     */
    public Gj insertBill(Gj gj){
        //获取流水号
        String lsh = commonService.getLsh("GJ");
        gj.setLsh(lsh);


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

        try {
            //更新单据状态
            if(gjMapper.updateBillStatus(1,gj.getLsh())!=1){
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

                KucnIn kucnIn = new KucnIn();
                BeanUtils.copyProperties(kc,kucnIn);
                kucnIn.setYwbm("GJ");
                kucnIn.setSl(mx.getSl());
                kucnIn.setLsh(gj.getLsh());
                kucnIn.setLx(gj.getLx());
                //插入入库记录
                kucnInMapper.insert(kucnIn);
            }
        } catch (Exception e) {
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public PageResult<Gj> queryGjByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);

        List<Gj> list = gjMapper.selectBill(key);
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

        gj = gjMapper.selectBill(lsh).get(0);

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

        g=gjMapper.selectBill(gj.getLsh()).get(0);

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

}
