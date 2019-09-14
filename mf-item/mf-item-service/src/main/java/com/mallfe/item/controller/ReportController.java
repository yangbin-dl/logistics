package com.mallfe.item.controller;

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
}
