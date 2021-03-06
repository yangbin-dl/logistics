package com.mallfe.item.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.Driver;
import com.mallfe.item.pojo.Path;
import com.mallfe.item.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-06
 */
@RestController
@RequestMapping("logistics")
public class LogisticsController {
    @Autowired
    LogisticsService logisticsService;

    @GetMapping("driver")
    public ResponseEntity<PageResult<Driver>> queryDriverByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ){
        PageResult<Driver> result=logisticsService.queryDriverByPage(page,rows,sortBy,desc,key);
        return ResponseEntity.ok(result);
    }

    @GetMapping("driverlist")
    public ResponseEntity<List<Driver>> queryDriverList(@RequestParam(value = "deptcode",required = false)String deptCode){
        List<Driver> result=logisticsService.queryDriverList(deptCode);
        return ResponseEntity.ok(result);
    }


    @GetMapping("path")
    public ResponseEntity<PageResult<Path>> queryPathByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ){
        PageResult<Path> result=logisticsService.queryPathByPage(page,rows,sortBy,desc,key);
        return ResponseEntity.ok(result);
    }

    @GetMapping("pathlist")
    public ResponseEntity<List<Path>> queryPathList(@RequestParam(value = "deptcode",required = false)String deptCode){
        List<Path> result=logisticsService.queryPathList(deptCode);
        return ResponseEntity.ok(result);
    }

    @PostMapping("insertdriver")
    public ResponseEntity<Driver> insertDriver(@RequestBody Driver driver){
        logisticsService.insertDriver(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @PostMapping("insertpath")
    public ResponseEntity<Path> insertDriver(@RequestBody Path path){
        logisticsService.insertPath(path);
        return ResponseEntity.status(HttpStatus.CREATED).body(path);
    }

    @PostMapping("updatedriver")
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver driver){
        logisticsService.updateDriver(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @GetMapping("driver/{id}")
    public ResponseEntity<Driver> queryDriverById(@PathVariable("id") Integer id){
        Driver driver = logisticsService.selectDriverById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }
    @GetMapping("path/{id}")
    public ResponseEntity<Path> queryPathById(@PathVariable("id") Integer id){
        Path paths = logisticsService.selectPathById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(paths);
    }
}
