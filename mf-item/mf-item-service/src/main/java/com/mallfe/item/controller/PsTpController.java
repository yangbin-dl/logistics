package com.mallfe.item.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.Ps;
import com.mallfe.item.pojo.Psrk;
import com.mallfe.item.pojo.Tp;
import com.mallfe.item.pojo.Tprk;
import com.mallfe.item.service.PsTpService;
import org.apache.ibatis.jdbc.Null;
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
    public ResponseEntity<Null> modifyPs(@RequestBody Ps ps){
        psTpService.modifyPs(ps);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改退配单
     * @param tp
     * @return
     */
    @PostMapping("modifytp")
    public ResponseEntity<Null> modifyPs(@RequestBody Tp tp){
        psTpService.modifyTp(tp);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 作废配送单
     * @param ps
     * @return
     */
    @PostMapping("deleteps")
    public ResponseEntity<Null> deletePs(@RequestBody Ps ps){
        psTpService.deletePs(ps);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("deletetp")
    public ResponseEntity<Null> deletePs(@RequestBody Tp tp){
        psTpService.deleteTp(tp);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("pageps")
    public ResponseEntity<PageResult<Ps>> queryPsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        PageResult<Ps> result = psTpService.queryPsByPage(page,rows, status);
        return ResponseEntity.ok(result);
    }

    @GetMapping("pagetp")
    public ResponseEntity<PageResult<Tp>> queryTpByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        PageResult<Tp> result = psTpService.queryTpByPage(page, rows, status);
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

    /**
     * 配送单发出
     * @param ps
     * @return
     */
    @PostMapping("psfc")
    public ResponseEntity<Null> commitPs(@RequestBody Ps ps){
        psTpService.commitPs(ps);
        return ResponseEntity.ok().build();
    }

    /**
     * 退配单发出
     * @param tp
     * @return
     */
    @PostMapping("tpfc")
    public ResponseEntity<Null> commitPs(@RequestBody Tp tp){
        psTpService.commitTp(tp);
        return ResponseEntity.ok().build();
    }


    /**
     * 配送到货
     * @param ps
     * @return
     */
    @PostMapping("psdh")
    public ResponseEntity<Null> arrivedPs(@RequestBody Ps ps){
        psTpService.arrivedPs(ps);
        return ResponseEntity.ok().build();
    }

    /**
     * 退配到货
     * @param tp
     * @return
     */
    @PostMapping("tpdh")
    public ResponseEntity<Null> arrivedTp(@RequestBody Tp tp){
        psTpService.arrivedTp(tp);
        return ResponseEntity.ok().build();
    }

    /**
     * 配送入库
     * @param psrk
     * @return
     */
    @PostMapping("psrk")
    public ResponseEntity<Null> inStorePs(@RequestBody Psrk psrk){
        psTpService.inStorePs(psrk);
        return ResponseEntity.ok().build();
    }

    @PostMapping("tprk")
    public ResponseEntity<Null> inStoreTp(@RequestBody Tprk tprk){
        psTpService.inStoreTp(tprk);
        return ResponseEntity.ok().build();
    }

    /**
     * 配送入库分页查询
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("pagepsrk")
    public ResponseEntity<PageResult<Psrk>> pagePsrk(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ){
        PageResult<Psrk> result = psTpService.queryPsrkByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    @GetMapping("querypsrk")
    public ResponseEntity<Psrk> queryPsrk(@RequestParam("lsh") String lsh){
        Psrk reulut = psTpService.queryPsrkByLsh(lsh);
        return ResponseEntity.ok(reulut);
    }
}
