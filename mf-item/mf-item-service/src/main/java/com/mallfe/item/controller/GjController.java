package com.mallfe.item.controller;

import com.mallfe.item.pojo.Gj;
import com.mallfe.item.service.GjServcie;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    GjServcie gjServcie;

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
}
