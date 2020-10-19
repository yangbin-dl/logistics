package com.mallfe.item.controller;

import com.mallfe.common.json.JsonObject;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.*;
import com.mallfe.item.service.XsThService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-21
 */
@RestController
@RequestMapping("xsth")
public class XsThController {
    @Autowired
    XsThService xsThService;

    /**
     * 销售单新增
     * @param xs
     * @return
     */
    @Deprecated
    @PostMapping("insertxs")
    public ResponseEntity<Xs> insertXs(@RequestBody Xs xs){
        xsThService.insertXs(xs);
        return ResponseEntity.status(HttpStatus.CREATED).body(xs);
    }

    /**
     * 退货单新增
     * @param th
     * @return
     */
    @Deprecated
    @PostMapping("insertth")
    public ResponseEntity<Th> insertTh(@RequestBody Th th){
        xsThService.insertTh(th);
        return ResponseEntity.status(HttpStatus.CREATED).body(th);
    }

    /**
     * 销售单修改
     * @param xs
     * @return
     */
    @Deprecated
    @PostMapping("modifyxs")
    public ResponseEntity<Null> modifyXs(@RequestBody Xs xs){
        xsThService.modifyXs(xs);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 退货单修改
     * @param th
     * @return
     */
    @Deprecated
    @PostMapping("modifyth")
    public ResponseEntity<Null> modifyTh(@RequestBody Th th){
        xsThService.modifyTh(th);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 销售单分页查询
     * @param page
     * @param rows
     * @param status
     * @param key
     * @return
     */
    @GetMapping("pagexs")
    public ResponseEntity<PageResult<Xs>> queryXsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "uid") Long uid
    ) {
        PageResult<Xs> result = xsThService.queryXsByPage(page, rows, key,uid,status);
        return ResponseEntity.ok(result);
    }

    /**
     * 退货单分页查询
     * @param page
     * @param rows
     * @param status
     * @param key
     * @return
     */
    @GetMapping("pageth")
    public ResponseEntity<PageResult<Th>> queryThByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "uid") Long uid
    ) {
        PageResult<Th> result = xsThService.queryThByPage(page, rows, key, uid, status);
        return ResponseEntity.ok(result);
    }

    /**
     * 销售单提交
     * @param xs
     * @return
     */
    @Deprecated
    @PostMapping("commitxs")
    public JsonObject commitXs(@RequestBody Xs xs){

        return xsThService.commitXs(xs);
    }

    /**
     * 销售单作废
     * @param xs
     * @return
     */
    @Deprecated
    @PostMapping("deletexs")
    public JsonObject deleteXs(@RequestBody Xs xs){

        return xsThService.deleteXs(xs);
    }

    /**
     * 退货单提交
     * @param th
     * @return
     */
    @Deprecated
    @PostMapping("committh")
    public JsonObject commitTh(@RequestBody Th th){

        return xsThService.commitTh(th);
    }

    /**
     * 退货单作废
     * @param th
     * @return
     */
    @Deprecated
    @PostMapping("deleteth")
    public JsonObject deleteTh(@RequestBody Th th){

        return xsThService.deleteTh(th);
    }

    /**
     * 换货单作废
     * @param gh
     * @return
     */
    @Deprecated
    @PostMapping("deletegh")
    public JsonObject deleteGh(@RequestBody Gh gh){

        return xsThService.deleteGh(gh);
    }

    @Deprecated
    @GetMapping("queryxs")
    public ResponseEntity<AllBill> queryXs(@RequestParam("lsh") String lsh) {
        return ResponseEntity.ok(xsThService.queryBill(lsh));
    }

    @Deprecated
    @GetMapping("queryth")
    public ResponseEntity<AllBill> queryTh(@RequestParam("lsh") String lsh) {
        return ResponseEntity.ok(xsThService.queryBill(lsh));
    }

    @GetMapping("pageall")
    public JsonObject queryAll(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "userid") String userid,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "hh", required = false) Integer hh,
            @RequestParam(value = "lsh", required = false) String lsh,
            @RequestParam(value = "contact", required = false) String contact

    ) {
        JsonObject result = xsThService.queryAll(page, userid, phone, hh, lsh, contact);
        return result;
    }

    @GetMapping("pageall2")
    public JsonObject queryAll2(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "userid") String userid,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "hh", required = false) Integer hh,
            @RequestParam(value = "lsh", required = false) String lsh,
            @RequestParam(value = "contact", required = false) String contact

    ) {
        JsonObject result = xsThService.queryAll2(page, userid, phone, hh, lsh, contact);
        return result;
    }

    @GetMapping("pagesh")
    public JsonObject querySh(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "userid") String userid,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "hh", required = false) Integer hh,
            @RequestParam(value = "lsh", required = false) String lsh,
            @RequestParam(value = "contact", required = false) String contact

    ) {
        JsonObject result = xsThService.querySh(page, userid, phone, hh, lsh, contact);
        return result;
    }

    @GetMapping("pagesh2")
    public JsonObject querySh2(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "userid") String userid,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "hh", required = false) Integer hh,
            @RequestParam(value = "lsh", required = false) String lsh,
            @RequestParam(value = "contact", required = false) String contact

    ) {
        JsonObject result = xsThService.querySh2(page, userid, phone, hh, lsh, contact);
        return result;
    }

    @GetMapping("phone")
    public JsonObject queryByPhone(@RequestParam("phone") String phone) {
        return xsThService.queryByPhone(phone);
    }

    @GetMapping("psmx")
    public ResponseEntity<List<Xs>> queryXsForPs(@RequestParam(value = "lsh", required = false) String lsh,
                                                 @RequestParam(value = "uid") Long uid,
                                                 @RequestParam(value = "rq", required = false) String rq){
        return ResponseEntity.ok(xsThService.queryXsForPs(uid,lsh,rq));
    }

    @GetMapping("tpmx")
    public ResponseEntity<List<Th>> queryThForTp(@RequestParam(value = "lsh", required = false) String lsh,
                                                 @RequestParam(value = "uid") Long uid,
                                                 @RequestParam(value = "rq", required = false) String rq){
        return ResponseEntity.ok(xsThService.queryThForTp(uid,lsh,rq));
    }

    @GetMapping("ghmx")
    public ResponseEntity<List<Gh>> queryThForGh(@RequestParam(value = "lsh", required = false) String lsh,
                                                 @RequestParam(value = "uid") Long uid,
                                                 @RequestParam(value = "rq", required = false) String rq){
        return ResponseEntity.ok(xsThService.queryGhForGh(uid,lsh,rq));
    }

    /**
     * App销售单新增
     * @param xs
     * @return
     */
    @PostMapping("appinsertxs")
    public JsonObject appInsertXs(@RequestBody Xs xs){
        return xsThService.appInsertXs(xs);
    }

    /**
     * App退货单新增
     * @param th
     * @return
     */
    @PostMapping("appinsertth")
    public JsonObject appInsertTh(@RequestBody Th th){

        return xsThService.appInsertTh(th);
    }

    /**
     * App销售单提交
     * @param xs
     * @return
     */
    @PostMapping("appcommitxs")
    public JsonObject appCommitXs(@RequestBody Xs xs){
        return xsThService.appCommitXs(xs);
    }

    /**
     * App退货单提交
     * @param th
     * @return
     */
    @PostMapping("appcommitth")
    public JsonObject appCommitTh(@RequestBody Th th){
        return xsThService.appCommitTh(th);
    }

    /**
     * App查询销售明细
     * @param lsh
     * @return
     */
    @GetMapping("appqueryxs")
    public JsonObject appQueryXs(@RequestParam("lsh") String lsh) {
        return xsThService.appQueryXs(lsh);
    }

    /**
     * App查询退货明细
     * @param lsh
     * @return
     */
    @GetMapping("appqueryth")
    public JsonObject appQueryTh(@RequestParam("lsh") String lsh) {
        return xsThService.appQueryTh(lsh);
    }

    @GetMapping("province")
    public JsonObject getProvince(){
        return xsThService.getProvince();
    }

    @GetMapping("city")
    public JsonObject getProvince(@RequestParam("province") String province){
        return xsThService.getCity(province);
    }

    /**
     * App换货单新增
     * @param gh 更换单
     * @return
     */
    @PostMapping("appinsertgh")
    public JsonObject appInsertGh(@RequestBody Gh gh){
        return xsThService.appInsertGh(gh);
    }

    /**
     * App换货单提交
     * @param gh
     * @return
     */
    @PostMapping("appcommitgh")
    public JsonObject appCommitGh(@RequestBody Gh gh){
        return xsThService.appCommitGh(gh);
    }

    /**
     * App查询换货明细
     * @param lsh
     * @return
     */
    @GetMapping("appquerygh")
    public JsonObject appQueryGh(@RequestParam("lsh") String lsh) {
        return xsThService.appQueryGh(lsh);
    }

    /**
     * 退货单分页查询
     * @param page
     * @param rows
     * @param status
     * @param key
     * @return
     */
    @GetMapping("pagegh")
    public ResponseEntity<PageResult<Gh>> queryGhByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "uid") Long uid
    ) {
        PageResult<Gh> result = xsThService.queryGhByPage(page, rows, key, uid, status);
        return ResponseEntity.ok(result);
    }

    /**
     * 物流经理撤销销售单
     * @param xs 流水号
     * @return 200
     */
    @PostMapping("revertxs")
    public ResponseEntity<Null> revertXs(@RequestBody Xs xs){
        xsThService.revertXs(xs);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 物流经理撤销退货单
     * @param th 流水号
     * @return 200
     */
    @PostMapping("revertth")
    public ResponseEntity<Null> revertTh(@RequestBody Th th){
        xsThService.revertTh(th);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 物流经理撤销往返单
     * @param gh 流水号
     * @return 200
     */
    @PostMapping("revertgh")
    public ResponseEntity<Null> revertTh(@RequestBody Gh gh){
        xsThService.revertGh(gh);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
