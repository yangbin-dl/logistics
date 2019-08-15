package com.mallfe.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.mapper.DriverMapper;
import com.mallfe.item.mapper.PathMapper;
import com.mallfe.item.pojo.Driver;
import com.mallfe.item.pojo.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-06
 */
@Service
public class LogisticsService {
    @Autowired
    DriverMapper driverMapper;

    @Autowired
    PathMapper pathMapper;

    /**
     * 根据分页查询司机
     * @param page 显示页
     * @param rows 每页行
     * @param sortBy 排序字段
     * @param desc 倒序
     * @param key 关键字
     * @return 包含商品列表的ResponseEntity
     */
    public PageResult<Driver> queryDriverByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);


        //查询
        List<Driver> list = driverMapper.list(key);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.DRIVER_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Driver> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    /**
     * 根据分页查询路线
     * @param page 显示页
     * @param rows 每页行
     * @param sortBy 排序字段
     * @param desc 倒序
     * @param key 关键字
     * @return 包含商品列表的ResponseEntity
     */
    public PageResult<Path> queryPathByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);


        //查询
        List<Path> list = pathMapper.list(key);
        if(CollectionUtils.isEmpty(list)){
            throw new MallfeException(ExceptionEnum.PATH_NOT_EXISTS);
        }

        //解析分页结果
        PageInfo<Path> info = new PageInfo<>(list);

        return new PageResult<>(info.getTotal(), list);
    }

    /**
     * 新增司机
     * @param driver 司机编码、名称、电话等信息
     * @return 司机完整信息
     */
    public Driver insertDriver(Driver driver) {
        if(driver.getDriverCode() == null){
            throw new MallfeException(ExceptionEnum.CODE_CANNOT_BE_NULL);
        }

        if(driver.getDriverName()== null){
            throw new MallfeException(ExceptionEnum.NAME_CANNOT_BE_NULL);
        }

        if(driver.getDeptCode() == null){
            driver.setDeptCode("0001");
        }

        // 插入后自动获得id
        driverMapper.insert(driver);

        return driver;

    }

    public Path insertPath(Path path){
        if(path.getPathCode() == null){
            throw new MallfeException(ExceptionEnum.CODE_CANNOT_BE_NULL);
        }

        if(path.getPathName() == null){
            throw new MallfeException(ExceptionEnum.NAME_CANNOT_BE_NULL);
        }

        if(path.getDeptCode() == null){
            path.setDeptCode("0001");
        }

        // 插入后自动获得id
        pathMapper.insert(path);

        return path;
    }

    public Driver selectDriverById(Integer id) {
        if(null == id){
            throw new MallfeException(ExceptionEnum.CODE_CANNOT_BE_NULL);
        }
        return  driverMapper.selectByPrimaryKey(id);
    }

    public Path selectPathById(Integer id) {

        if(null == id){
            throw new MallfeException(ExceptionEnum.CODE_CANNOT_BE_NULL);
        }
        return  pathMapper.selectByPrimaryKey(id);
    }

    public List<Driver> queryDriverList(String deptCode) {
        return driverMapper.list(deptCode);
    }

    public List<Path> queryPathList(String deptCode) {
        return pathMapper.list(deptCode);
    }
}

