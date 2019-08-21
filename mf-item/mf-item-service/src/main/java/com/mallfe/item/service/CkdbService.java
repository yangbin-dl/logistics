package com.mallfe.item.service;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.CkdbMapper;
import com.mallfe.item.pojo.Ckdb;
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
        ckdbMapper.insertBill(ckdb);

        return null;
    }

    public Ckdb update(Ckdb ckdb) {
        return null;
    }

    public PageResult<Ckdb> page(Integer page, Integer rows, String lsh, Integer hh) {
        return null;

    }

    public void commitBill(Ckdb ckdb) {

    }

    public void commit(String lsh) {

    }
}
