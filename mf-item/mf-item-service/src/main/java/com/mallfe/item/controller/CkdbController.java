package com.mallfe.item.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.Ckdb;
import com.mallfe.item.service.CkdbService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 描述
 * 仓库调拨controller
 * @author yangbin
 * @since 2019-08-21
 */
@RestController
@RequestMapping("ckdb")
public class CkdbController {

    @Autowired
    CkdbService ckdbService;

    @PostMapping("insert")
    public ResponseEntity<Ckdb> insert(@RequestBody Ckdb ckdb){
        ckdbService.insert(ckdb);
        return ResponseEntity.status(HttpStatus.CREATED).body(ckdb);
    }

    @PostMapping("update")
    public ResponseEntity<Ckdb> update(@RequestBody Ckdb ckdb){
        ckdbService.update(ckdb);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<Ckdb>> page(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "lsh", required = false) String lsh,
            @RequestParam(value = "hh", required = false) Integer hh
    ) {
        PageResult<Ckdb> result = ckdbService.page(page,rows,lsh,hh);
        return ResponseEntity.ok(result);
    }

    @PostMapping("commit")
    public ResponseEntity<Null> commit(@RequestBody Ckdb ckdb){
        ckdbService.commitBill(ckdb);
        return ResponseEntity.ok().build();
    }

    @GetMapping("bill")
    public ResponseEntity<Ckdb> bill(@Param("lsh")String lsh){
        ckdbService.bill(lsh);
        return ResponseEntity.ok().build();
    }

}
