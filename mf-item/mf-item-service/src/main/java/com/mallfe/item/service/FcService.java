package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.FcMapper;
import com.mallfe.item.mapper.FcMxMapper;
import com.mallfe.item.mapper.KucnMapper;
import com.mallfe.item.mapper.KucnOutMapper;
import com.mallfe.item.pojo.Fc;
import com.mallfe.item.pojo.FcDetail;
import com.mallfe.item.pojo.Kucn;
import com.mallfe.item.pojo.KucnOut;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FcService {
    @Autowired
    private FcMapper fcMapper;

    @Autowired
    private FcMxMapper fcMxMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private KucnMapper kucnMapper;

    @Autowired
    private KucnOutMapper kucnOutMapper;

    /**
     * 新增购进单
     * @param fc
     * @return
     */
    public Fc insertBill(Fc fc){
        //获取流水号
        String lsh = commonService.getLsh("FC");
        fc.setLsh(lsh);


        fc.setLrsj(CommonService.getStringDate());
        //插入单据
        try {
            fcMapper.insert(fc);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //插入明细
        try {
            for (FcDetail mx: fc.getList()) {
                fcMxMapper.insertMx(lsh,mx.getHh(),mx.getSl());
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return fc;
    }

    /**
     * 提交购进单
     * @param fc
     * @return
     */
    public void commitBill(Fc fc){

        try {
            //更新单据状态
            fcMapper.updateBillStatus(1,fc.getLsh());

            for(FcDetail mx: fc.getList()){
                Kucn kc = new Kucn();
                kc.setHh(mx.getHh());
                kc.setKucn(mx.getSl());
                kc.setLx(fc.getLx());
                Kucn result = kucnMapper.selectOne(kc);
                //更新库存
                long i = kc.getKucn();
                if(result == null){
                    kucnMapper.insert(kc);
                }
                else{
                    kucnMapper.reduceKucn(mx.getSl(),result.getId());
                }

                KucnOut kucnOut = new KucnOut();
                BeanUtils.copyProperties(kc,kucnOut);
                kucnOut.setYwbm("FC");
                kucnOut.setSl(mx.getSl());
                kucnOut.setLsh(fc.getLsh());
                kucnOut.setLx(fc.getLx());
                //插入出库记录
                kucnOutMapper.insert(kucnOut);
            }
        } catch (Exception e) {
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }

    public PageResult<Fc> queryFcByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(Fc.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("lsh",key+"%")
                    .orLike("truename","%"+key+"%");
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Fc> list = fcMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.USER_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Fc> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);

    }

    public Fc queryBill(String lsh) {
        Fc fc = new Fc();
        fc.setLsh(lsh);

        fc=fcMapper.selectOne(fc);

        if(fc == null){
            throw new MallfeException(ExceptionEnum.BILL_NOT_EXISTS);
        }

        List<FcDetail> list = fcMxMapper.getMx(lsh);

        if(list.size() == 0){
            throw new MallfeException(ExceptionEnum.BILL_DETAIL_NOT_EXISTS);
        }

        fc.setList(list);

        return fc;
    }
}
