package com.mallfe.item.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.Ps;
import com.mallfe.item.pojo.Tp;
import com.mallfe.item.service.PsTpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/25
 */
@RestController
@RequestMapping("pstp")
public class PsTpController {

    @Autowired
    private PsTpService psTpService;

    /**
     * 新增配送单
     * @param ps
     * @return
     */
    @PostMapping("insertps")
    public ResponseEntity<Ps> insertPs(@RequestBody Ps ps){
        psTpService.insertPs(ps);
        return ResponseEntity.status(HttpStatus.CREATED).body(ps);
    }

    /**
     * 新增退配单
     * @param tp
     * @return
     */
    @PostMapping("inserttp")
    public ResponseEntity<Tp> insertPs(@RequestBody Tp tp){
        psTpService.insertTp(tp);
        return ResponseEntity.status(HttpStatus.CREATED).body(tp);
    }

    /**
     * 修改配送单
     * @param ps
     * @return
     */
    @PostMapping("modifyps")
    public ResponseEntity<Ps> modifyPs(@RequestBody Ps ps){
        psTpService.modifyPs(ps);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改退配单
     * @param tp
     * @return
     */
    @PostMapping("modifytp")
    public ResponseEntity<Tp> modifyPs(@RequestBody Tp tp){
        psTpService.modifyTp(tp);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 作废配送单
     * @param ps
     * @return
     */
    @PostMapping("deleteps")
    public ResponseEntity<Ps> deletePs(@RequestBody Ps ps){
        psTpService.deletePs(ps);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("deletetp")
    public ResponseEntity<Tp> deletePs(@RequestBody Tp tp){
        psTpService.deleteTp(tp);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("pageps")
    public ResponseEntity<PageResult<Ps>> queryPsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Ps> result = psTpService.queryPsByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    @GetMapping("pagetp")
    public ResponseEntity<PageResult<Tp>> queryTpByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Tp> result = psTpService.queryTpByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    @GetMapping("queryps")
    public ResponseEntity<Ps> queryPsByLsh(@RequestParam("lsh")String lsh){
        Ps reulut = psTpService.queryPsByLsh(lsh);
        return ResponseEntity.ok(reulut);
    }

    @GetMapping("querytp")
    public ResponseEntity<Tp> queryTpByLsh(@RequestParam("lsh")String lsh){
        Tp reulut = psTpService.queryTpByLsh(lsh);
        return ResponseEntity.ok(reulut);
    }
}
