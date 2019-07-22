package com.mallfe.item.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.Th;
import com.mallfe.item.pojo.Xs;
import com.mallfe.item.service.XsThService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Xs> modifyXs(@RequestBody Xs xs){
        xsThService.modifyXs(xs);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 退货单修改
     * @param th
     * @return
     */
    @PostMapping("modifyth")
    public ResponseEntity<Th> modifyTh(@RequestBody Th th){
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
    public ResponseEntity<Xs> commitXs(@RequestBody Xs xs){
        xsThService.commitXs(xs);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 销售单作废
     * @param xs
     * @return
     */
    @PostMapping("deletexs")
    public ResponseEntity<Xs> deleteXs(@RequestBody Xs xs){
        xsThService.deleteXs(xs);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 退货单提交
     * @param th
     * @return
     */
    @PostMapping("committh")
    public ResponseEntity<Xs> commitTh(@RequestBody Th th){
        xsThService.commitTh(th);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 退货单作废
     * @param th
     * @return
     */
    @PostMapping("deleteth")
    public ResponseEntity<Xs> deleteTh(@RequestBody Th th){
        xsThService.deleteTh(th);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
