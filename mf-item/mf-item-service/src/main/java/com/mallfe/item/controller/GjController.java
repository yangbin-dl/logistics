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
    GjService gjService;

    /**
     * 新增
     * @param gj
     * @return
     */
    @PostMapping("insert")
    public ResponseEntity<Gj> insert(@RequestBody Gj gj){
        gj = gjService.insertBill(gj);
        return ResponseEntity.status(HttpStatus.CREATED).body(gj);
    }


    /**
     * 提交
     * @param gj
     * @return
     */
    @PostMapping("commit")
    public ResponseEntity<Null> commit(@RequestBody Gj gj){
        gjService.commitBill(gj);
        return ResponseEntity.ok().build();
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Gj>> queryByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<Gj> result = gjService.queryGjByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    /**
     * 查询明细
     * @param lsh 包含流水号的bean
     * @return 单据明细
     */
    @GetMapping("bill")
    public ResponseEntity<Gj> queryBill(@RequestParam(value = "lsh",required = true) String lsh){
        Gj result = gjService.queryBill(lsh);
        return ResponseEntity.ok(result);
    }

    /**
     * 作废单据
     * @param gj 包含流水号的bean
     * @return 200
     */
    @PostMapping("delete")
    public ResponseEntity<Null> delete(@RequestBody Gj gj){
        gjService.deleteBill(gj);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改单据
     * @param gj 包含流水号的bean
     * @return 200
     */
    @PostMapping("modify")
    public ResponseEntity<Null> modify(@RequestBody Gj gj){
        gjService.modifyBill(gj);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
