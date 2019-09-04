package com.mallfe.item.controller;

import com.mallfe.item.pojo.Lxtz;
import com.mallfe.item.service.LxtzService;
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
 * @author yangbin
 * @since 2019-09-04
 */
@RestController
@RequestMapping("lxtz")
public class LxtzController {

    @Autowired
    LxtzService lxtzService;

    @PostMapping("insert")
    public ResponseEntity<Lxtz> insert(@RequestBody Lxtz lxtz){
        lxtz = lxtzService.insert(lxtz);
        return ResponseEntity.status(HttpStatus.CREATED).body(lxtz);
    }

}
