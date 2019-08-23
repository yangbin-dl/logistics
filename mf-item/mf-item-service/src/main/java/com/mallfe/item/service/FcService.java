package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.FcMapper;
import com.mallfe.item.mapper.FcMxMapper;
import com.mallfe.item.mapper.KucnMapper;
import com.mallfe.item.mapper.KucnOutMapper;
import com.mallfe.item.pojo.*;
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
 * @since 2019/07/16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FcService {
    @Autowired
    private FcMapper fcMapper;

    @Autowired
    private FcMxMapper fcMxMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private KucnMapper kucnMapper;

    @Autowired
    private KucnOutMapper kucnOutMapper;

    @Autowired
    private UserService userService;

    /**
     * 新增购进单
     * @param fc
     * @return
     */
    public Fc insertBill(Fc fc){
        //获取流水号
        String lsh = commonService.getLsh("FC");
        fc.setLsh(lsh);

        User user = userService.selectById(fc.getLrid());

        fc.setDeptCode(user.getDeptCode());
        fc.setStoreCode(user.getStoreCode());

        fc.setLrsj(CommonService.getStringDate());
        //插入单据
        try {
            fcMapper.insert(fc);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //插入明细
        try {
            for (FcDetail mx: fc.getList()) {
                fcMxMapper.insertMx(lsh,mx.getHh(),mx.getSl());
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return fc;
    }

    /**
     * 提交购进单
     * @param fc
     * @return
     */
    public void commitBill(Fc fc){

        try {
            //更新单据状态
            if(fcMapper.updateBillStatus(1,fc.getLsh()) !=1){
                throw new MallfeException(ExceptionEnum.BILL_STATUS_ERROR);
            }

            for(FcDetail mx: fc.getList()){
                Kucn kc = new Kucn();
                kc.setHh(mx.getHh());
                kc.setLx(fc.getLx());
                //增加区域和店铺信息
                kc.setDeptCode(fc.getDeptCode());
                kc.setStoreCode(fc.getStoreCode());
                Kucn result = kucnMapper.selectOne(kc);
                //更新库存
                if(result == null){
                    throw new MallfeException(ExceptionEnum.UNDER_STOCK);
                }
                else{
                    if(kucnMapper.reduceKucn(mx.getSl(),result.getId())!=1){
                        throw new MallfeException(ExceptionEnum.UNDER_STOCK);
                    }
                }

                if(kucnMapper.fcReduceRtKucn(mx.getHh(),mx.getSl(),fc.getStoreCode(),fc.getLx())!=1){
                    throw new MallfeException(ExceptionEnum.UNDER_STOCK);
                }

                KucnOut kucnOut = new KucnOut();
                BeanUtils.copyProperties(kc,kucnOut);
                kucnOut.setYwbm("FC");
                kucnOut.setSl(mx.getSl());
                kucnOut.setLsh(fc.getLsh());
                kucnOut.setLx(fc.getLx());
                //插入出库记录
                kucnOutMapper.insert(kucnOut);
            }
        } catch (Exception e) {
            throw new MallfeException(ExceptionEnum.UNDER_STOCK);
        }
    }

    public PageResult<Fc> queryFcByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);


        //查询
        List<Fc> list = fcMapper.selectBill(key);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Fc> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);

    }

    public Fc queryBill(String lsh) {
        Fc fc = new Fc();
        fc.setLsh(lsh);

        fc=fcMapper.selectBill(lsh).get(0);

        if(fc == null){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        List<FcDetail> list = fcMxMapper.getMx(lsh);

        if(list.size() == 0){
            throw new MallfeException(ExceptionEnum.BILL_DETAIL_NOT_EXISTS);
        }

        fc.setList(list);

        return fc;
    }

    public void deleteBill(Fc fc) {
        try {
            if(fcMapper.updateBillStatus(9,fc.getLsh()) != 1){
                throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
            }
        }
        catch (Exception e){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }
    }

    public void modifyBill(Fc fc) {

        Fc g = new Fc();
        g.setLsh(fc.getLsh());
        g.setStatus(0);

        g=fcMapper.selectBill(fc.getLsh()).get(0);

        if(g == null){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        if(g.getStatus()!=0 ){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        try{
            //删除明细
            fcMxMapper.deleteMx(fc.getLsh());
            //插入明细
            for (FcDetail mx: fc.getList()) {
                fcMxMapper.insertMx(fc.getLsh(),mx.getHh(),mx.getSl());
            }
        }
        catch (Exception e){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }
    }
}
