package com.mallfe.item.service;

import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.CkdbMapper;
import com.mallfe.item.pojo.Ckdb;
import com.mallfe.item.pojo.CkdbDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Ckdb insert(Ckdb ckdb) {

        //获取流水号
        String lsh = commonService.getLsh("CKDB");
        ckdb.setLsh(lsh);
        try{
            ckdbMapper.insertBill(ckdb);

            for (CkdbDetail mx: ckdb.getList()) {
                ckdbMapper.insertMx(ckdb.getLsh(),mx.getHh(),mx.getSl());
            }
        }
        catch (Exception e ){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }

        return ckdb;
    }

    public Ckdb update(Ckdb ckdb) {

        Ckdb c = ckdbMapper.selectBill(ckdb.getLsh(),null).get(0);

        if(c == null){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        if(c.getStatus()!=0){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }
        try{
            ckdbMapper.deleteMx(ckdb.getLsh());
            for (CkdbDetail mx: ckdb.getList()) {
                ckdbMapper.insertMx(ckdb.getLsh(),mx.getHh(),mx.getSl());
            }
        }
        catch (Exception e){
            throw new MallfeException(ExceptionEnum.OPERATION_FALURE);
        }

        return ckdb;
    }

    public PageResult<Ckdb> page(Integer page, Integer rows, String lsh, Integer hh) {
        return null;

    }

    public void commitBill(Ckdb ckdb) {

    }

    public Ckdb bill(String lsh) {
        return null;
    }
}
