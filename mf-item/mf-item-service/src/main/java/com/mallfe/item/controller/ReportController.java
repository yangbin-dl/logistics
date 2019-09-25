package com.mallfe.item.controller;

import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.AllBill;
import com.mallfe.item.pojo.KucnReport;
import com.mallfe.item.pojo.KucnStructure;
import com.mallfe.item.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/09/04
 */
@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("plkucn")
    public ResponseEntity<List<KucnStructure>> getPlKucn(@RequestParam("rq")String rq,
                                                         @RequestParam(value = "deptcode",required = false)String deptCode,
                                                         @RequestParam(value = "storecode",required = false)String storeCode){
        return ResponseEntity.ok(reportService.selectPlKucnStructure(rq,deptCode,storeCode));
    }

    @GetMapping("ppkucn")
    public ResponseEntity<List<KucnStructure>> getPpKucn(@RequestParam("rq")String rq,
                                                         @RequestParam(value = "plbm",required = false)String plbm,
                                                         @RequestParam(value = "deptcode",required = false)String deptCode,
                                                         @RequestParam(value = "storecode",required = false)String storeCode){
        return ResponseEntity.ok(reportService.selectPpKucnStructure(rq, plbm, deptCode, storeCode));
    }

    @GetMapping("xsthlist")
    public ResponseEntity<PageResult<AllBill>> getXsthList(@RequestParam("type") String type,
                                                           @RequestParam("uid") Long uid,
                                                           @RequestParam("strq") String strq,
                                                           @RequestParam("torq") String torq,
                                                           @RequestParam(value = "lsh",required = false) String lsh,
                                                           @RequestParam(value = "hh",required = false) Integer hh,
                                                           @RequestParam(value = "plbm",required = false) String plbm,
                                                           @RequestParam(value = "storecode",required = false) String storeCode,
                                                           @RequestParam(value = "storagecode",required = false) String storageCode,
                                                           @RequestParam(value = "page", required = false) Integer page,
                                                           @RequestParam(value = "rows", required = false) Integer rows
                                  ){
        PageResult<AllBill> result = reportService.getXsthList(type,uid,strq,torq,lsh,hh,plbm,storeCode,storageCode,
                page,
                rows);
        return ResponseEntity.ok(result);
    }

    @GetMapping("xsthdetail")
    public AllBill getXsthDetail(@RequestParam("lsh") String lsh){
        return reportService.getXsthDetail(lsh);
    }

    @GetMapping("kucnlist")
    public ResponseEntity<PageResult<KucnReport>> getKucnList(@RequestParam(value = "deptcode",required = false) String deptCode,
                                                              @RequestParam(value = "storagecode",required = false) String storageCode,
                                                              @RequestParam(value = "plbm",required = false) String plbm,
                                                              @RequestParam(value = "hh",required = false) Integer hh,
                                                              @RequestParam(value = "page", required = false) Integer page,
                                                              @RequestParam(value = "rows", required = false) Integer rows){
        PageResult<KucnReport> result = reportService.getKucnList(deptCode,storageCode,plbm,hh,page,rows);
        return ResponseEntity.ok(result);
    }

    @GetMapping("rtkucnlist")
    public ResponseEntity<PageResult<KucnReport>> getRtKucnList(@RequestParam(value = "deptcode",required = false) String deptCode,
                                                              @RequestParam(value = "storagecode",required = false) String storageCode,
                                                              @RequestParam(value = "plbm",required = false) String plbm,
                                                              @RequestParam(value = "hh",required = false) Integer hh,
                                                              @RequestParam(value = "page", required = false) Integer page,
                                                              @RequestParam(value = "rows", required = false) Integer rows){
        PageResult<KucnReport> result = reportService.getRtKucnList(deptCode,storageCode,plbm,hh,page,rows);
        return ResponseEntity.ok(result);
    }
}
