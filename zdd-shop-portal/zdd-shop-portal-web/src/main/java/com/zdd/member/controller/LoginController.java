package com.zdd.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Xin
 * @date 2020/8/20 5:24 下午
 * @Content:
 */
@Controller
public class LoginController {
    private static final String LOGIN_FTL = "/member/login";

    /**
     * 跳页面
     * @return
     */
    @GetMapping("/login.html")
    public String login() {
        return LOGIN_FTL;
    }

    /**
     * 接收参数
     * @return
     */
    @PostMapping("/login.html")
    public String postLogin() {
        return LOGIN_FTL;
    }

}
