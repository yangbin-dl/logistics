package com.mallfe.item.service;

import com.mallfe.item.mapper.ReportMapper;
import com.mallfe.item.pojo.KucnStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019/9/13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportService {

    @Autowired
    ReportMapper reportMapper;

    public List<KucnStructure> selectPlKucnStructure(String rq, String deptCode,String storeCode) {
        if(deptCode == null){
            deptCode = "0001";
        }

        return reportMapper.selectPlKucnStructure(rq,deptCode,storeCode);
    }

    public List<KucnStructure> selectPpKucnStructure(String rq, String plbm, String deptCode,String storeCode) {
        if(deptCode == null){
            deptCode = "0001";
        }

        return reportMapper.selectPpKucnStructure(rq, plbm, deptCode, storeCode);
    }
}
