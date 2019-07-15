package com.mallfe.item.service;

import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.item.mapper.LshMapper;
import com.mallfe.item.pojo.LshBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/15
 */
@Service
public class CommonService {

    @Autowired
    private LshMapper lshMapper;

    public String getLsh(String ywbm){

        Long seq;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        LshBean o = new LshBean();

        o.setYwbm(ywbm);

        o = lshMapper.select(o).get(0);

        if(o ==null){
            throw new MallfeException(ExceptionEnum.GET_BILLBUMBER_FALURE);
        }
        else{
            if(o.getRq().equals(sdf.format(new Date()))){
                seq = o.getLsh();
                lshMapper.updateLsh(ywbm);
            }
            else {
                seq = 1L;
                lshMapper.clearLsh(ywbm,sdf.format(new Date()));
            }
        }

        return ywbm + sdf.format(new Date()) + Long.toString(seq+10000L).substring(1,5);
    }

}
