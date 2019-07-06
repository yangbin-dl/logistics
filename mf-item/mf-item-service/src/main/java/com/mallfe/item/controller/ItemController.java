package com.mallfe.item.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.Sp;
import com.mallfe.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-05
 */
@RestController
@RequestMapping("item")
public class ItemController {
    @Autowired
    ItemService itemService;

    /**
     * 新增商品
     * @param sp 实体类
     * @return 商品完整信息
     */
    @PostMapping("insert")
    public ResponseEntity<Sp> insert(@RequestBody Sp sp){
        itemService.insert(sp);
        return ResponseEntity.status(HttpStatus.CREATED).body(sp);
    }

    /**
     * 按实体类查询商品
     * @param sp 实体类
     * @return 商品完整信息
     */
    @PostMapping("query")
    public ResponseEntity<Sp> query(@RequestBody Sp sp){
        return ResponseEntity.ok(itemService.query(sp));
    }

    /**
     * 按货号查询商品
     * @param hh 货号
     * @return 商品完整信息
     */
    @GetMapping("queryhh")
    public ResponseEntity<Sp> queryByHh(@RequestParam("hh") Integer hh){
        Sp s = new Sp();
        s.setHh(hh);
        return ResponseEntity.ok(itemService.query(s));
    }

    /**
     * 按条码查询商品
     * @param tm 条码
     * @return 商品完整信息
     */
    @GetMapping("querytm")
    public ResponseEntity<Sp> queryByHh(@RequestParam("tm") String tm){
        Sp s = new Sp();
        s.setTm(tm);
        return ResponseEntity.ok(itemService.query(s));
    }

    /**
     * 按id查询商品
     * @param id 商品id
     * @return 商品完整信息
     */
    @GetMapping("queryid")
    public ResponseEntity<Sp> queryById(@RequestParam("hh") Long id){
        Sp s = new Sp();
        s.setId(id);
        return ResponseEntity.ok(itemService.query(s));
    }

    /**
     * 按品名模糊查询商品
     * @param pinm 品名
     * @return 商品列表
     */
    @GetMapping("querypinm")
    public ResponseEntity<List<Sp>> queryByPinm(@RequestParam("pinm") String pinm){
        return ResponseEntity.ok(itemService.queryByPinm(pinm));
    }

    /**
     * 根据分页查询商品
     * @param page 显示页
     * @param rows 每页行
     * @param sortBy 排序字段
     * @param desc 倒序
     * @param key 关键字
     * @return 包含商品列表的ResponseEntity
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Sp>> querySpByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ){
        PageResult<Sp> result=itemService.querySpByPage(page,rows,sortBy,desc,key);
        return ResponseEntity.ok(result);
    }
}
