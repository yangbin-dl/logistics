package com.mallfe.logistics.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.logistics.pojo.Driver;
import com.mallfe.logistics.pojo.Path;
import com.mallfe.logistics.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
