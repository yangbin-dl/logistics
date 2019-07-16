package com.mallfe.item.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.Gj;
import com.mallfe.item.service.GjService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/15
 */
@RestController
@RequestMapping("gj")
public class GjController {
    @Autowired
    GjService gjServcie;

    @PostMapping("insert")
    public ResponseEntity<Gj> insert(@RequestBody Gj gj){
        gjServcie.insertBill(gj);
        return ResponseEntity.status(HttpStatus.CREATED).body(gj);
    }

    @PostMapping("commit")
    public ResponseEntity<Null> commit(@RequestBody Gj gj){
        gjServcie.commitBill(gj);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<Gj>> queryByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Gj> result = gjServcie.queryGjByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    @GetMapping("bill")
    public ResponseEntity<Gj> queryBill(@RequestParam(value = "lsh",required = true) String lsh){
        Gj result = gjServcie.queryBill(lsh);
        return ResponseEntity.ok(result);
    }
}
