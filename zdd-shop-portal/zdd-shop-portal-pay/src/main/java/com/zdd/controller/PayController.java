package com.zdd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Xin
 * @date 2021/3/11 4:58 下午
 * @Content:
 */
@Controller
public class PayController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
