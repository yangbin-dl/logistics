package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.CkdbMapper;
import com.mallfe.item.pojo.Ckdb;
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

        Ckdb c = ckdbMapper.selectBill(ckdb.getLsh(),null).get(0);

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

    public PageResult<Ckdb> page(Integer page, Integer rows, String lsh, Integer hh) {
        //分页
        PageHelper.startPage(page, rows);

        List<Ckdb> list = ckdbMapper.selectBill(lsh,hh);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Ckdb> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);

    }

    public void commitBill(Ckdb ckdb) {

    }

    public Ckdb bill(String lsh) {

        Ckdb ckdb = ckdbMapper.selectBill(lsh,null).get(0);

        ckdb.setList(ckdbMapper.selectMx(lsh));

        return ckdb;
    }
}
