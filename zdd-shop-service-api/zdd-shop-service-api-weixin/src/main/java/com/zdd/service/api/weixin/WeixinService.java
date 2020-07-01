package com.zdd.service.api.weixin;

import com.zdd.weixin.entry.AppEntry;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Xin
 * @date 2020/7/1 11:50 上午
 * @Content:
 */
public interface WeixinService {

    @GetMapping("/getApp")
    public AppEntry getApp();
}
