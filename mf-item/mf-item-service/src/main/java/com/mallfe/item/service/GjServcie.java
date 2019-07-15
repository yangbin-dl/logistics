package com.mallfe.item.service;

import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.item.mapper.GjMapper;
import com.mallfe.item.mapper.GjMxMapper;
import com.mallfe.item.pojo.Gj;
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
@Transactional
public class GjServcie {


    @Autowired
    private GjMapper gjMapper;

    @Autowired
    private GjMxMapper gjMxMapper;

    @Autowired
    private CommonService commonService;

    /**
     * 新增购进单
     * @param gj
     * @return
     */
    public Gj insertBill(Gj gj){
        //获取流水号
        gj.setLsh(commonService.getLsh("GJ"));
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
        gjMapper.updateBillStatus(1,gj.getLsh());
    }
}
