package com.mallfe.item.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.Fc;
import com.mallfe.item.pojo.Gj;
import com.mallfe.item.service.FcService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 描述
 * 注释参考购进
 * @author Yangbin
 * @since 2019/07/16
 */
@RestController
@RequestMapping("fc")
public class FcController {
    @Autowired
    FcService fcService;
    

    @PostMapping("insert")
    public ResponseEntity<Fc> insert(@RequestBody Fc fc){
        fc = fcService.insertBill(fc);
        return ResponseEntity.status(HttpStatus.CREATED).body(fc);
    }

    @PostMapping("commit")
    public ResponseEntity<Null> commit(@RequestBody Fc fc){
        fcService.commitBill(fc);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("delete")
    public ResponseEntity<Null> delete(@RequestBody Fc fc){
        fcService.deleteBill(fc);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<Fc>> queryByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Fc> result = fcService.queryFcByPage(page, rows, sortBy, desc, key,0);
        return ResponseEntity.ok(result);
    }

    @GetMapping("page1")
    public ResponseEntity<PageResult<Fc>> queryByPage1(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Fc> result = fcService.queryFcByPage(page, rows, sortBy, desc, key,1);
        return ResponseEntity.ok(result);
    }

    @GetMapping("page2")
    public ResponseEntity<PageResult<Fc>> queryByPage2(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Fc> result = fcService.queryFcByPage(page, rows, sortBy, desc, key,2);
        return ResponseEntity.ok(result);
    }

    @GetMapping("pageall")
    public ResponseEntity<PageResult<Fc>> queryByPageall(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Fc> result = fcService.queryFcByPage(page, rows, sortBy, desc, key,null);
        return ResponseEntity.ok(result);
    }

    @GetMapping("bill")
    public ResponseEntity<Fc> queryBill(@RequestParam(value = "lsh",required = true) String lsh){
        Fc result = fcService.queryBill(lsh);
        return ResponseEntity.ok(result);
    }

    /**
     *
     * @param fc
     * @return
     */
    @PostMapping("modify")
    public ResponseEntity<Null> modify(@RequestBody Fc fc){
        fcService.modifyBill(fc);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("shenhe1")
    public ResponseEntity<Null> shenhe1(@RequestBody Fc fc){
        fcService.shenhe1(fc);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("shenhe2")
    public ResponseEntity<Null> shenhe2(@RequestBody Fc fc){
        fcService.shenhe2(fc);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("reject")
    public ResponseEntity<Null> reject(@RequestBody Fc fc){
        fcService.reject(fc);
        return ResponseEntity.ok().build();
    }
}
