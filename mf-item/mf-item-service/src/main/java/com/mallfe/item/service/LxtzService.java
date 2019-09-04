package com.mallfe.item.service;

import com.mallfe.item.mapper.LxtzMapper;
import com.mallfe.item.pojo.Lxtz;
import com.mallfe.item.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Lxtz insert(Lxtz lxtz) {
        //获取流水号
        String lsh = commonService.getLsh("TZ");
        lxtz.setLsh(lsh);
        User user = userService.selectById(lxtz.getLrid());

        lxtz.setDeptCode(user.getDeptCode());
        lxtz.setLrsj(CommonService.getStringDate());

        lxtzMapper.insertBill(lxtz);
        return null;

    }
}
