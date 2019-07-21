package com.mallfe.item.controller;

import com.mallfe.item.service.XsThService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
