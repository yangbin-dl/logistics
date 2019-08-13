package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.json.JsonData;
import com.mallfe.common.json.JsonError;
import com.mallfe.common.json.JsonObject;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.DepartmentMapper;
import com.mallfe.item.mapper.StoreMapper;
import com.mallfe.item.mapper.UserMapper;
import com.mallfe.item.pojo.Department;
import com.mallfe.item.pojo.Store;
import com.mallfe.item.pojo.User;
import org.apache.commons.lang3.StringUtils;
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
 * @since 2019/07/05
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * 用户验证
     * @param username 用户名
     * @param password 密码
     * @return 完整用户信息，包括密码
     */
    public User verify(String username, String password){
        User t =new User();
        t.setUsername(username);
        t.setPassword(password);
        User user =userMapper.selectOne(t);
        if(user == null){
            throw new MallfeException(ExceptionEnum.USER_OR_PASSWORD_NOT_CORRECT);
        }

        return user;
    }

    /**
     * 更新用户信息
     * 注意：会根据id更新所有not null 字段。
     * 适用于更新密码、用户名等。
     * @param user 带有id的用户实体类
     */
    public void update(User user){
        int i;
        try {
            i=userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            throw new MallfeException(ExceptionEnum.USER_UPDATE_FAILURE);
        }

        if(i !=1 ){
            throw new MallfeException(ExceptionEnum.USER_NOT_EXISTS);
        }
    }


    /**
     * 新增用户
     * @param user 用户信息
     * @return 带有id的用户信息
     */
    public User insert(User user){

        //1.检查用户名是否重复
        Long userId = findUserByUsername(user.getUsername());
        if (userId != null) {
            throw new MallfeException(ExceptionEnum.USERNAME_DUPLICATE);
        }

        //2.插入用户，插入后会自动获取id
        userMapper.insertUser(user);
        if(null != user.getPl() && user.getPl().size()!=0){
            userMapper.insertUserPl(user);
        }

        return user;
    }


    /**
     * 根据用户明查询用户信息
     * @param username 用户名
     * @return 用户id
     */
    private Long findUserByUsername(String username){
        User t  = new User();
        t.setUsername(username);

        User user =userMapper.selectOne(t);
        if(user == null){
            return null;
        }
        return user.getId();
    }


    public PageResult<User> querySpByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);
        //条件过滤
        Example example = new Example(User.class);
        if(StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("username",key+"%")
                    .orLike("truename","%"+key+"%");
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<User> list = userMapper.selectUserByPage(key);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.USER_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<User> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);

    }

    public User selectById(Integer id) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id",id);
        return  userMapper.selectOneByExample(example);
    }

    public JsonObject checkUser(String username, String password) {
        User t =new User();
        t.setUsername(username);
        t.setPassword(password);
        User user =userMapper.selectOne(t);
        if(user == null){
            return new JsonError("用户名或密码错误");
        }

        return new JsonData(user);
    }

    public List<Store> selectStoreList() {
        return storeMapper.selectStoreList();
    }

    public List<Department> selectDeptList(String stroecode) {
        return departmentMapper.selectDepartmentList(stroecode);
    }
}
