package com.mallfe.item.controller;

import com.mallfe.common.json.JsonObject;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.*;
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
     * @param ps 配送单
     * @return 200
     */
    @PostMapping("insertps")
    public ResponseEntity<Null> insertPs(@RequestBody Ps ps){
        psTpService.insertPs(ps);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增退配单
     * @param tp 退配单
     * @return 200
     */
    @PostMapping("inserttp")
    public ResponseEntity<Null> insertPs(@RequestBody Tp tp){
        psTpService.insertTp(tp);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增往返配送单
     * @param ghps 往返配送单
     * @return 200
     */
    @PostMapping("insertghps")
    public ResponseEntity<Null> insertPs(@RequestBody Ghps ghps){
        psTpService.insertGhps(ghps);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改配送单
     * @param ps 配送单
     * @return 200
     */
    @PostMapping("modifyps")
    public ResponseEntity<Null> modifyPs(@RequestBody Ps ps){
        psTpService.modifyPs(ps);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改退配单
     * @param tp 退配单
     * @return 200
     */
    @PostMapping("modifytp")
    public ResponseEntity<Null> modifyPs(@RequestBody Tp tp){
        psTpService.modifyTp(tp);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改往返配送单
     * @param ghps 往返配送单
     * @return 200
     */
    @PostMapping("modifyghps")
    public ResponseEntity<Null> modifyGhps(@RequestBody Ghps ghps){
        psTpService.modifyGhps(ghps);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 作废配送单
     * @param ps 配送单信息
     * @return 200
     */
    @PostMapping("deleteps")
    public ResponseEntity<Null> deletePs(@RequestBody Ps ps){
        psTpService.deletePs(ps);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 作废退配单
     * @param tp 退配单信息
     * @return 200
     */
    @PostMapping("deletetp")
    public ResponseEntity<Null> deletePs(@RequestBody Tp tp){
        psTpService.deleteTp(tp);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 作废往返配送单
     * @param ghps 往返配送单
     * @return 200
     */
    @PostMapping("deleteghps")
    public ResponseEntity<Null> deleteGhps(@RequestBody Ghps ghps){
        psTpService.deleteGhps(ghps);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 配送单新增及历史查询
     * @param page 页数
     * @param rows 每页行数
     * @param sortBy 排序字段
     * @param desc 是否倒序
     * @param key 主键
     * @param status 状态
     * @param uid 查询人ID
     * @return 查询结果
     */
    @GetMapping("pageps")
    public ResponseEntity<PageResult<Ps>> queryPsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "uid", required = false) Long uid
    ) {
        PageResult<Ps> result = psTpService.queryPsByPage(page,rows, status,key);
        return ResponseEntity.ok(result);
    }

    /**
     * 配送单出库查询
     * @param page 页数
     * @param rows 行数
     * @param sortBy 排序字段
     * @param desc 是否倒序
     * @param key 主键
     * @param status 状态
     * @param uid 查询人ID
     * @return 查询结果
     */
    @GetMapping("pagepsck")
    public ResponseEntity<PageResult<Ps>> queryPsckByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "uid", required = false) Long uid
    ) {
        PageResult<Ps> result = psTpService.queryPsckByPage(page,rows, status,uid);
        return ResponseEntity.ok(result);
    }

    /**
     * 退配单新增及历史查询
     * @param page 页数
     * @param rows 行数
     * @param sortBy 排序字段
     * @param desc 是否倒序
     * @param key 主键
     * @param status 状态
     * @param uid 查询人ID
     * @return 查询结果
     */
    @GetMapping("pagetp")
    public ResponseEntity<PageResult<Tp>> queryTpByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "uid", required = false) Long uid
    ) {
        PageResult<Tp> result = psTpService.queryTpByPage(page, rows, status, key);
        return ResponseEntity.ok(result);
    }

    /**
     * 退配单发出查询
     * @param page 页数
     * @param rows 行数
     * @param sortBy 排序字段
     * @param desc 是否倒序
     * @param key 主键
     * @param status 状态
     * @param uid 查询人ID
     * @return 查询结果
     */
    @GetMapping("pagetpfc")
    public ResponseEntity<PageResult<Tp>> queryTpfcByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "uid", required = false) Long uid
    ) {
        PageResult<Tp> result = psTpService.queryTpfcByPage(page, rows, status, uid);
        return ResponseEntity.ok(result);
    }

    /**
     * 往返配送单新增及历史查询
     * @param page 页数
     * @param rows 每页行数
     * @param sortBy 排序字段
     * @param desc 是否倒序
     * @param key 主键
     * @param status 状态
     * @param uid 查询人ID
     * @return 查询结果
     */
    @GetMapping("pageghps")
    public ResponseEntity<PageResult<Ghps>> queryGhpsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "uid", required = false) Long uid
    ) {
        PageResult<Ghps> result = psTpService.queryGhpsByPage(page,rows, status,key);
        return ResponseEntity.ok(result);
    }

    /**
     * 往返配送单出库查询
     * @param page 页数
     * @param rows 行数
     * @param sortBy 排序字段
     * @param desc 是否倒序
     * @param key 主键
     * @param status 状态
     * @param uid 查询人ID
     * @return 查询结果
     */
    @GetMapping("pageghpsck")
    public ResponseEntity<PageResult<Ghps>> queryGhpsckByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "uid", required = false) Long uid
    ) {
        PageResult<Ghps> result = psTpService.queryGhpsckByPage(page,rows, status,uid);
        return ResponseEntity.ok(result);
    }

    /**
     * 配送明细查询
     * @param lsh 流水号
     * @return 配送单
     */
    @GetMapping("queryps")
    public ResponseEntity<Ps> queryPsByLsh(@RequestParam("lsh")String lsh){
        Ps reulut = psTpService.queryPsByLsh(lsh);
        return ResponseEntity.ok(reulut);
    }

    /**
     * 退配明细查询
     * @param lsh 流水号
     * @return 退配单
     */
    @GetMapping("querytp")
    public ResponseEntity<Tp> queryTpByLsh(@RequestParam("lsh")String lsh){
        Tp reulut = psTpService.queryTpByLsh(lsh);
        return ResponseEntity.ok(reulut);
    }

    /**
     * 往返配送查询
     * @param lsh 流水号
     * @return 往返配送单
     */
    @GetMapping("queryghps")
    public ResponseEntity<Ghps> queryGhpsByLsh(@RequestParam("lsh")String lsh){
        Ghps reulut = psTpService.queryGhpsByLsh(lsh);
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
     * 往返配送单发出
     * @param ghps
     * @return
     */
    @PostMapping("ghpsfc")
    public ResponseEntity<Null> commitGhps(@RequestBody Ghps ghps){
        psTpService.commitGhps(ghps);
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

    @PostMapping("ghpsrk")
    public ResponseEntity<Null> inStoreGhps(@RequestBody Ghpsrk ghpsrk){
        psTpService.inStoreGhps(ghpsrk);
        return ResponseEntity.ok().build();
    }
    /**
     * 配送入库单查询
     * @param page 页数
     * @param rows 行数
     * @param sortBy 排序字段
     * @param desc 是否倒序
     * @param key 主键
     * @param uid 查询人ID
     * @return 查询结果
     */
    @GetMapping("pagepsrk")
    public ResponseEntity<PageResult<Psrk>> pagePsrk(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "uid") Long uid
    ){
        PageResult<Psrk> result = psTpService.queryPsrkByPage(page, rows, sortBy, desc, key, uid);
        return ResponseEntity.ok(result);
    }

    /**
     * 配送入库单历史查询
     * @param page 页数
     * @param rows 行数
     * @param sortBy 排序字段
     * @param desc 是否倒序
     * @param key 主键
     * @param uid 查询人ID
     * @return 查询结果
     */
    @GetMapping("pagepsrkhis")
    public ResponseEntity<PageResult<Psrk>> pagePsrkhis(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "uid") Long uid
    ){
        PageResult<Psrk> result = psTpService.queryPsrkhisByPage(page, rows, sortBy, desc, key, uid);
        return ResponseEntity.ok(result);
    }


    @GetMapping("querypsrk")
    public ResponseEntity<Psrk> queryPsrk(@RequestParam("lsh") String lsh){
        Psrk reulut = psTpService.queryPsrkByLsh(lsh);
        return ResponseEntity.ok(reulut);
    }

    @GetMapping("pagetprk")
    public ResponseEntity<PageResult<Tprk>> pageTprk(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "uid") Long uid
    ){
        PageResult<Tprk> result = psTpService.queryTprkByPage(page, rows, sortBy, desc, key, uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("pagetprkhis")
    public ResponseEntity<PageResult<Tprk>> pageTprkhis(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "uid") Long uid
    ){
        PageResult<Tprk> result = psTpService.queryTprkhisByPage(page, rows, sortBy, desc, key, uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("querytprk")
    public ResponseEntity<Tprk> queryTprk(@RequestParam("lsh") String lsh){
        Tprk reulut = psTpService.queryTprkByLsh(lsh);
        return ResponseEntity.ok(reulut);
    }

    /**
     * 往返配送入库分页查询
     * @param page 页数
     * @param rows 行数
     * @param sortBy 排序字段
     * @param desc 是否倒序
     * @param key 主键
     * @param uid 查询人ID
     * @return 查询结果
     */
    @GetMapping("pageghpsrk")
    public ResponseEntity<PageResult<Ghpsrk>> pageGhpsrk(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "uid") Long uid
    ){
        PageResult<Ghpsrk> result = psTpService.queryGhpsrkByPage(page, rows, sortBy, desc, key, uid);
        return ResponseEntity.ok(result);
    }

    /**
     * 往返配送入库单历史查询
     * @param page 页数
     * @param rows 行数
     * @param sortBy 排序字段
     * @param desc 是否倒序
     * @param key 主键
     * @param uid 查询人ID
     * @return 查询结果
     */
    @GetMapping("pageghpsrkhis")
    public ResponseEntity<PageResult<Ghpsrk>> pageGhpsrkhis(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "uid") Long uid
    ){
        PageResult<Ghpsrk> result = psTpService.queryGhpsrkhisByPage(page, rows, sortBy, desc, key, uid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("queryghpsrk")
    public ResponseEntity<Ghpsrk> queryGhpsrk(@RequestParam("lsh") String lsh){
        Ghpsrk reulut = psTpService.queryGhpsrkByLsh(lsh);
        return ResponseEntity.ok(reulut);
    }

    @GetMapping("applist")
    public JsonObject applist(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "userid") String userid,
                              @RequestParam(value = "phone", required = false) String phone,
                              @RequestParam(value = "hh", required = false) Integer hh,
                              @RequestParam(value = "lsh", required = false) String lsh,
                              @RequestParam(value = "psdh", required = false) String psdh
    ) {
        return psTpService.applist(page, userid,phone,hh,lsh,psdh);
    }

    @GetMapping("applisthis")
    public JsonObject applisthis(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "userid") String userid,
                              @RequestParam(value = "phone", required = false) String phone,
                              @RequestParam(value = "hh", required = false) Integer hh,
                              @RequestParam(value = "lsh", required = false) String lsh,
                              @RequestParam(value = "psdh", required = false) String psdh
    ) {
        return psTpService.applisthis(page, userid,phone,hh,lsh,psdh);
    }

    @GetMapping("appmx")
    public JsonObject appmx(@RequestParam(value = "lsh", required = false) String lsh) {
        return psTpService.appmx(lsh);
    }

    @PostMapping("apppsarrive")
    public JsonObject appPsArrive(@RequestBody Ps ps){

        return psTpService.appPsArrive(ps.getLsh());
    }

    @PostMapping("apppsnotarrive")
    public JsonObject appPsNotArrive(@RequestBody Ps ps){
        return psTpService.appPsNotArrive(ps.getLsh());
    }
}
