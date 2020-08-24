package com.zdd.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Xin
 * @date 2020/8/20 5:21 下午
 * @Content:
 */
@Controller
public class IndexController {
    private static final String INDEX_FTL = "index";

    @GetMapping("/")
    public String index() {
        return INDEX_FTL;
    }


    @GetMapping("/index.html")
    public String aindex() {
        return INDEX_FTL;
    }
}
