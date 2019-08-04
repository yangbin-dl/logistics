package com.mallfe.item.service;

import com.mallfe.common.entity.UserInfo;
import com.mallfe.common.utils.JwtUtils;
import com.mallfe.item.config.JwtProperties;
import com.mallfe.item.mapper.UserMapper;
import com.mallfe.item.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Qin PengCheng
 * @date 2018/6/13
 */
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    /**
     *获取令牌的方法
     * @param username
     * @param password
     * @return
     */
    public UserInfo getToken(String username, String password) {
        try {
            User q = new User();
            q.setUsername(username);
            q.setPassword(password);
            ResponseEntity<User> userResponseEntity = ResponseEntity.status(200).body(this.userMapper.selectOne(q));
            if (!userResponseEntity.hasBody()) {
                logger.info("用户信息不存在，{}", username);
                return null;
            }
            User user = userResponseEntity.getBody();
            //生成令牌
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(user, userInfo);

            return userInfo;
        } catch (Exception e) {
            logger.error("生成令牌的过程中出错");
            return null;
        }
    }
}
