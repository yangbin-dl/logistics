package com.mallfe.item.service;

import com.mallfe.common.json.JsonData;
import com.mallfe.common.json.JsonObject;
import com.mallfe.item.mapper.ReportMapper;
import com.mallfe.item.pojo.AllBill;
import com.mallfe.item.pojo.KucnStructure;
import com.mallfe.item.pojo.User;
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

    @Autowired
    UserService userService;

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

    public JsonObject getXsthList(String type, Long uid, String strq, String torq, String lsh, Integer hh, String plbm,
                                   String storeCode, String storageCode) {

        User user = userService.selectById(uid);

        String deptCode = user.getLx() == 0 ? null:user.getDeptCode();

        List<AllBill> xsthList  = reportMapper.selectXsthList(type,deptCode,strq,torq,lsh,hh,plbm,storeCode,
                storageCode);


        return new JsonData(xsthList);
    }

    public JsonObject getXsthDetail(String lsh) {

        AllBill xsthDetail = reportMapper.selectXsthDetail(lsh);

        return new JsonData(xsthDetail);
    }
}
