package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.KucnInMapper;
import com.mallfe.item.mapper.KucnMapper;
import com.mallfe.item.mapper.KucnOutMapper;
import com.mallfe.item.mapper.LxtzMapper;
import com.mallfe.item.pojo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-09-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LxtzService {
    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @Autowired
    LxtzMapper lxtzMapper;

    @Autowired
    KucnMapper kucnMapper;

    @Autowired
    KucnOutMapper kucnOutMapper;

    @Autowired
    KucnInMapper kucnInMapper;

    public Lxtz insert(Lxtz lxtz) {
        //获取流水号
        String lsh = commonService.getLsh("TZ");
        lxtz.setLsh(lsh);
        User user = userService.selectById(lxtz.getLrid());

        lxtz.setDeptCode(user.getDeptCode());
        lxtz.setLrsj(CommonService.getStringDate());

        lxtzMapper.insertBill(lxtz);
        return lxtz;

    }

    public void update(Lxtz lxtz) {
        User user = userService.selectById(lxtz.getLrid());
        lxtz.setDeptCode(user.getDeptCode());

        lxtzMapper.updateBill(lxtz);
    }

    public void commit(String lsh) {
        if(lxtzMapper.updateStatus(lsh,0,1)!= 1){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }
    }

    public void delete(String lsh) {
        lxtzMapper.updateStatus(lsh,0,9);
    }

    public PageResult<Lxtz> queryByPage(Integer page, Integer rows, String sortBy, Boolean desc, String lsh,
                                        Integer status) {
        //分页
        PageHelper.startPage(page, rows);

        List<Lxtz> list = lxtzMapper.selectBills(lsh,status);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Lxtz> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    public void shenhe1(String lsh) {
        if(lxtzMapper.updateStatus(lsh,1,2)!= 1){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }
    }

    public void shenhe2(String lsh) {
        try {
            if(lxtzMapper.updateStatsuToFinish(lsh)!= 1){
                throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
            }

            Lxtz lxtz = lxtzMapper.selectOneBill(lsh);

            //冲减库存

            Kucn kc1 = new Kucn();
            kc1.setHh(lxtz.getHh());
            kc1.setLx(lxtz.getOldLx());
            //增加区域和店铺信息
            kc1.setDeptCode(lxtz.getDeptCode());
            kc1.setStoreCode(lxtz.getStoreCode());
            Kucn result1 = kucnMapper.selectOne(kc1);
            //更新库存
            if(result1 == null){
                throw new MallfeException(ExceptionEnum.UNDER_STOCK);
            }
            else{
                if(kucnMapper.reduceKucn(lxtz.getHh(),lxtz.getSl(),lxtz.getStoreCode(),lxtz.getOldLx())!=1){
                    throw new MallfeException(ExceptionEnum.UNDER_STOCK);
                }
            }

            if(kucnMapper.fcReduceRtKucn(lxtz.getHh(),lxtz.getSl(),lxtz.getStoreCode(),lxtz.getOldLx())!=1){
                throw new MallfeException(ExceptionEnum.UNDER_STOCK);
            }

            KucnOut kucnOut = new KucnOut();
            BeanUtils.copyProperties(kc1,kucnOut);
            kucnOut.setYwbm("TZ");
            kucnOut.setHh(lxtz.getHh());
            kucnOut.setSl(lxtz.getSl());
            kucnOut.setLsh(lxtz.getLsh());
            kucnOut.setLx(lxtz.getOldLx());
            kucnOut.setDeptCode(lxtz.getDeptCode());
            kucnOut.setStoreCode(lxtz.getStoreCode());
            //插入出库记录
            kucnOutMapper.insert(kucnOut);

            //增加新库存


            Kucn kc2 = new Kucn();
            kc2.setHh(lxtz.getHh());
            kc2.setLx(lxtz.getNewLx());
            //增加区域和店铺信息
            kc2.setDeptCode(lxtz.getDeptCode());
            kc2.setStoreCode(lxtz.getStoreCode());
            Kucn result2 = kucnMapper.selectOne(kc2);
            //更新库存
            if(result2 == null){
                kc2.setKucn(lxtz.getSl());
                kucnMapper.insert(kc2);
            }
            else{
                kucnMapper.addKucn(lxtz.getSl(),result2.getId());
            }

            if(kucnMapper.addRtKucn(lxtz.getHh(),lxtz.getSl(),lxtz.getStoreCode(),lxtz.getNewLx())==0){
                Kucn rt = new Kucn();
                rt.setHh(lxtz.getHh());
                rt.setLx(lxtz.getNewLx());
                rt.setDeptCode(lxtz.getDeptCode());
                rt.setStoreCode(lxtz.getStoreCode());
                rt.setKucn(lxtz.getSl());
                kucnMapper.insertRtKucn(rt);
            }

            KucnIn kucnIn = new KucnIn();
            kucnIn.setYwbm("TZ");
            kucnIn.setHh(lxtz.getHh());
            kucnIn.setSl(lxtz.getSl());
            kucnIn.setLsh(lxtz.getLsh());
            kucnIn.setLx(lxtz.getNewLx());
            kucnIn.setStoreCode(lxtz.getStoreCode());
            kucnIn.setDeptCode(lxtz.getDeptCode());
            //插入入库记录
            kucnInMapper.insert(kucnIn);
        }
        catch (Exception e){
            throw new MallfeException(ExceptionEnum.UNDER_STOCK);
        }
    }

    public Lxtz queryBill(String lsh) {
        Lxtz lxtz = lxtzMapper.selectOneBill(lsh);
        return lxtz;
    }
}
