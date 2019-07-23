package com.mallfe.item.service;

import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.item.mapper.PsMapper;
import com.mallfe.item.mapper.PsMxMapper;
import com.mallfe.item.mapper.TpMapper;
import com.mallfe.item.mapper.TpMxMapper;
import com.mallfe.item.pojo.Ps;
import com.mallfe.item.pojo.PsDetail;
import com.mallfe.item.pojo.Tp;
import com.mallfe.item.pojo.TpDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Ps insertPs(Ps ps){
        //获取流水号
        String lsh = commonService.getLsh("PS");
        ps.setLsh(lsh);

        ps.setLrsj(CommonService.getStringDate());
        //插入明细
        try {
            for (PsDetail mx: ps.getList()) {
                psMxMapper.insertPsMx(lsh,mx.getDdh(),0);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return ps;
    }

    public Tp insertTp(Tp tp){
        //获取流水号
        String lsh = commonService.getLsh("TP");
        tp.setLsh(lsh);

        tp.setLrsj(CommonService.getStringDate());
        //插入明细
        try {
            for (TpDetail mx: tp.getList()) {
                tpMxMapper.insertTpMx(lsh,mx.getDdh(),0);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return tp;
    }

}
