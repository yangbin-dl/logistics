package com.mallfe.item.controller;

import com.mallfe.common.json.JsonObject;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.AllBill;
import com.mallfe.item.pojo.Th;
import com.mallfe.item.pojo.Xs;
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
    @PostMapping("modifyth")
    public ResponseEntity<Null> modifyTh(@RequestBody Th th){
        xsThService.modifyTh(th);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 销售单分页查询
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("pagexs")
    public ResponseEntity<PageResult<Xs>> queryXsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Xs> result = xsThService.queryXsByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    /**
     * 退货单分页查询
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("pageth")
    public ResponseEntity<PageResult<Th>> queryThByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Th> result = xsThService.queryThByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    /**
     * 销售单提交
     * @param xs
     * @return
     */
    @PostMapping("commitxs")
    public JsonObject commitXs(@RequestBody Xs xs){

        return xsThService.commitXs(xs);
    }

    /**
     * 销售单作废
     * @param xs
     * @return
     */
    @PostMapping("deletexs")
    public JsonObject deleteXs(@RequestBody Xs xs){

        return xsThService.deleteXs(xs);
    }

    /**
     * 退货单提交
     * @param th
     * @return
     */
    @PostMapping("committh")
    public JsonObject commitTh(@RequestBody Th th){

        return xsThService.commitTh(th);
    }

    /**
     * 退货单作废
     * @param th
     * @return
     */
    @PostMapping("deleteth")
    public JsonObject deleteTh(@RequestBody Th th){

        return xsThService.deleteTh(th);
    }

    @GetMapping("queryxs")
    public ResponseEntity<AllBill> queryXs(@RequestParam("lsh") String lsh) {
        return ResponseEntity.ok(xsThService.queryBill(lsh));
    }

    @GetMapping("queryth")
    public ResponseEntity<AllBill> queryTh(@RequestParam("lsh") String lsh) {
        return ResponseEntity.ok(xsThService.queryBill(lsh));
    }

    @GetMapping("pageall")
    public JsonObject queryAll(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "lruserid", required = false) String lruserid,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "hh", required = false) Integer hh,
            @RequestParam(value = "lsh", required = false) String lsh

    ) {
        JsonObject result = xsThService.queryAll(page, lruserid, phone, hh, lsh);
        return result;
    }

    @GetMapping("phone")
    public JsonObject queryByPhone(@RequestParam("phone") String phone) {
        return xsThService.queryByPhone(phone);
    }

    @GetMapping("psmx")
    public ResponseEntity<List<Xs>> queryXsForPs(@RequestParam(value = "lsh", required = false) String lsh){
        return ResponseEntity.ok(xsThService.queryXsForPs(lsh));
    }

    @GetMapping("tpmx")
    public ResponseEntity<List<Th>> queryThForTp(@RequestParam(value = "lsh", required = false) String lsh){
        return ResponseEntity.ok(xsThService.queryThForTp(lsh));
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
}
