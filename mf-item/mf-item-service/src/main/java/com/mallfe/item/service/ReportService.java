package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.ReportMapper;
import com.mallfe.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    public PageResult<AllBill> getXsthList(String type, Long uid, String strq, String torq, String lsh, Integer hh, String plbm,
                                                           String storeCode, String storageCode, Integer page, Integer rows) {



        User user = userService.selectById(uid);

        String deptCode = user.getLx() == 0 ? null:user.getDeptCode();

        if(page != null && rows !=null){
            PageHelper.startPage(page, rows);
        }

        List<AllBill> list = null;
        if(type.equals("XS")){
            list = reportMapper.selectXsList(deptCode,strq,torq,lsh,hh,plbm,storeCode,
                    storageCode);
        }
        else if(type.equals("TH")){
            list = reportMapper.selectThList(deptCode,strq,torq,lsh,hh,plbm,storeCode,
                    storageCode);
        }


        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        PageInfo<AllBill> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(),(rows == null || rows==0 )  ? 1: info.getTotal()%rows == 0 ?
                info.getTotal()/rows : info.getTotal()/rows+1,
                list);
    }

    public AllBill getXsthDetail(String lsh) {

        return reportMapper.selectXsthDetail(lsh);
    }

    public PageResult<KucnReport> getKucnList(String deptCode, String storageCode, String plbm, Integer hh,
                                              Integer page, Integer rows) {
        if(page != null && rows !=null){
            PageHelper.startPage(page, rows);
        }

        if(deptCode == null){
            deptCode = "0001";
        }

        List<KucnReport> list = reportMapper.selectKucnList(deptCode,storageCode,plbm,hh);

        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        PageInfo<KucnReport> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(),(rows == null || rows==0 )  ? 1: info.getTotal()%rows == 0 ?
                info.getTotal()/rows : info.getTotal()/rows+1,
                list);
    }

    public PageResult<KucnReport> getRtKucnList(String deptCode, String storageCode, String plbm, Integer hh, Integer page, Integer rows) {
        if(page != null && rows !=null){
            PageHelper.startPage(page, rows);
        }

        if(deptCode == null){
            deptCode = "0001";
        }

        List<KucnReport> list = reportMapper.selectRtKucnList(deptCode, storageCode, plbm, hh);

        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        PageInfo<KucnReport> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(),(rows == null || rows==0 )  ? 1: info.getTotal()%rows == 0 ?
                info.getTotal()/rows : info.getTotal()/rows+1,
                list);
    }

    public PageResult<KucnCompareReport> getKucnCompareList(String deptCode, String storageCode, String plbm, Integer hh, Integer page, Integer rows) {

        if(page != null && rows !=null){
            PageHelper.startPage(page, rows);
        }

        if(deptCode == null){
            deptCode = "0001";
        }

        List<KucnCompareReport> list= reportMapper.selectKucnLxList(deptCode,storageCode,plbm,hh);

        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        PageInfo<KucnCompareReport> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(),(rows == null || rows==0 )  ? 1: info.getTotal()%rows == 0 ?
                info.getTotal()/rows : info.getTotal()/rows+1,
                list);

    }

    public PageResult<SpInAndOutDeatilReport> getSpInAndOutDeatil(String deptCode,
                                                                  String storeCode, Integer hh, Integer lx,
                                                                  String strq,
                                                                  String torq, Integer page, Integer rows) {
        if(deptCode == null || deptCode.equals("")){
            deptCode = "0001";
        }

        if(page != null && rows !=null){
            PageHelper.startPage(page, rows);
        }

        List<SpInAndOutDeatilReport> list= reportMapper.selectSpInAndOutDeatil(deptCode,storeCode,hh,lx,strq,torq);

        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        PageInfo<SpInAndOutDeatilReport> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(),(rows == null || rows==0 )  ? 1: info.getTotal()%rows == 0 ?
                info.getTotal()/rows : info.getTotal()/rows+1,
                list);
    }

}
