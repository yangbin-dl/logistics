package com.mallfe.item.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.Lxtz;
import com.mallfe.item.service.LxtzService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("modify")
    public ResponseEntity<Lxtz> modify(@RequestBody Lxtz lxtz) {
        lxtzService.update(lxtz);
        return ResponseEntity.ok().build();
    }

    @PostMapping("commit")
    public ResponseEntity<Null> commit(@RequestBody Lxtz lxtz) {
        lxtzService.commit(lxtz.getLsh());
        return ResponseEntity.ok().build();
    }

    @PostMapping("delete")
    public ResponseEntity<Null> delete(@RequestBody Lxtz lxtz) {
        lxtzService.delete(lxtz.getLsh());
        return ResponseEntity.ok().build();
    }

    @PostMapping("shenhe1")
    public ResponseEntity<Null> shenhe1(@RequestBody Lxtz lxtz) {
        lxtzService.shenhe1(lxtz.getLsh());
        return ResponseEntity.ok().build();
    }

    @PostMapping("shenhe2")
    public ResponseEntity<Null> shenhe2(@RequestBody Lxtz lxtz) {
        lxtzService.shenhe2(lxtz.getLsh());
        return ResponseEntity.ok().build();
    }

    @GetMapping("page")
    public ResponseEntity<PageResult<Lxtz>> queryPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "lsh", required = false) String lsh
    ) {
        PageResult<Lxtz> result = lxtzService.queryByPage(page, rows, sortBy, desc, lsh,0);
        return ResponseEntity.ok(result);
    }

    @GetMapping("page1")
    public ResponseEntity<PageResult<Lxtz>> queryPage1(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "lsh", required = false) String lsh
    ) {
        PageResult<Lxtz> result = lxtzService.queryByPage(page, rows, sortBy, desc, lsh,1);
        return ResponseEntity.ok(result);
    }

    @GetMapping("page2")
    public ResponseEntity<PageResult<Lxtz>> queryPage2(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "lsh", required = false) String lsh
    ) {
        PageResult<Lxtz> result = lxtzService.queryByPage(page, rows, sortBy, desc, lsh,2);
        return ResponseEntity.ok(result);
    }

    @GetMapping("pageall")
    public ResponseEntity<PageResult<Lxtz>> queryPageall(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "lsh", required = false) String lsh
    ) {
        PageResult<Lxtz> result = lxtzService.queryByPage(page, rows, sortBy, desc, lsh,null);
        return ResponseEntity.ok(result);
    }

    @GetMapping("bill")
    public ResponseEntity<Lxtz> queryMx(@RequestParam("lsh") String lsh) {
        Lxtz result = lxtzService.queryBill(lsh);
        return ResponseEntity.ok(result);
    }
}
