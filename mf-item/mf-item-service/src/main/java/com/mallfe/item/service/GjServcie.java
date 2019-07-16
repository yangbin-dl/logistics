package com.mallfe.item.service;

import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
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

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GjServcie {


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
        for (int i = 0; i < gj.getList().size(); i++) {
            gj.getList().get(i).setLsh(lsh);
        }
        //插入单据
        try {
            gjMapper.insert(gj);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //插入明细
        try {
            gjMxMapper.insertList(gj.getList());
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
            gjMapper.updateBillStatus(1,gj.getLsh());

            for(GjDetail mx: gj.getList()){
                Kucn kc = new Kucn();
                kc.setHh(mx.getHh());
                kc.setKucn(mx.getSl());
                kc.setLx(gj.getLx());
                Kucn result = kucnMapper.selectOne(kc);
                //更新库存
                if(result == null){
                    kucnMapper.insert(kc);
                }
                else{
                    kucnMapper.updateKucn(mx.getSl(),result.getId());
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
}
