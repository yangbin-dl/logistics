package com.mallfe.item.service;

import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.item.mapper.*;
import com.mallfe.item.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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

    @Autowired
    private XsMapper xsMapper;

    @Autowired
    private ThMapper thMapper;

    /**
     * 新增配送单
     * @param ps
     * @return
     */
    public Ps insertPs(Ps ps){
        //获取流水号
        String lsh = commonService.getLsh("PS");
        ps.setLsh(lsh);

        //插入单据
        try {
            psMapper.insert(ps);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //更新销售单状态
        try {
            for (PsDetail mx: ps.getList()) {
                //如果更新的行数不为1，则代表单据状态异常，回滚事务
                if(xsMapper.updateStatusToPs(mx.getDdh(),lsh,ps.getDriverCode(),ps.getPathCode())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

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

    /**
     * 修改配送单
     * @param ps
     * @return
     */
    public Ps modifyPs(Ps ps){

        //修改销售单为待配送
        try {
            for (PsDetail mx: ps.getList()) {
                xsMapper.updateStatusToUnPs(ps.getLsh());
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //更新销售单状态
        try {
            for (PsDetail mx: ps.getList()) {
                //如果更新的行数不为1，则代表单据状态异常，回滚事务
                if(xsMapper.updateStatusToPs(mx.getDdh(),ps.getLsh(),ps.getDriverCode(),ps.getPathCode())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //删除配送明细
        Example example = new Example(PsDetail.class);
        example.createCriteria().andEqualTo("lsh",ps.getLsh());
        try {
            psMxMapper.deleteByExample(example);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }


        //插入配送明细
        try {
            for (PsDetail mx: ps.getList()) {
                psMxMapper.insertPsMx(ps.getLsh(),mx.getDdh(),0);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return ps;
    }


    /**
     * 新增退配单
     * @param tp
     * @return
     */
    public Tp insertTp(Tp tp){
        //获取流水号
        String lsh = commonService.getLsh("TP");
        tp.setLsh(lsh);

        tp.setLrsj(CommonService.getStringDate());

        //插入单据
        try {
            tpMapper.insert(tp);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //更新销售单状态
        try {
            for (TpDetail mx: tp.getList()) {
                //如果更新的行数不为1，则代表单据状态异常，回滚事务
                if(thMapper.updateStatusToTp(mx.getDdh(),lsh,tp.getDriverCode(),tp.getPathCode())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

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

    public Tp modifyTp(Tp tp){

        //修改销售单为待配送
        try {
            for (TpDetail mx: tp.getList()) {
                thMapper.updateStatusToUnTp(tp.getLsh());
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //更新销售单状态
        try {
            for (TpDetail mx: tp.getList()) {
                //如果更新的行数不为1，则代表单据状态异常，回滚事务
                if(thMapper.updateStatusToTp(mx.getDdh(),tp.getLsh(),tp.getDriverCode(),tp.getPathCode())!=1){
                    throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
                }
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        //删除配送明细
        Example example = new Example(TpDetail.class);
        example.createCriteria().andEqualTo("lsh",tp.getLsh());
        try {
            tpMxMapper.deleteByExample(example);
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }


        //插入配送明细
        try {
            for (TpDetail mx: tp.getList()) {
                tpMxMapper.insertTpMx(tp.getLsh(),mx.getDdh(),0);
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
        return tp;
    }

    /**
     * 配送单作废
     * @param ps
     */
    public void deletePs(Ps ps) {
        //修改销售单为待配送
        try {
            for (PsDetail mx: ps.getList()) {
                xsMapper.updateStatusToUnPs(ps.getLsh());
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        try {
            psMapper.updateStatusToCancel(ps.getLsh());
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

    }

    public void deleteTp(Tp tp) {
        //修改销售单为待配送
        try {
            for (TpDetail mx: tp.getList()) {
                thMapper.updateStatusToUnTp(tp.getLsh());
            }
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }

        try {
            tpMapper.updateStatusToCancel(tp.getLsh());
        } catch (Exception e){
            throw new MallfeException(ExceptionEnum.BILL_SAVE_FALURE);
        }
    }
}
