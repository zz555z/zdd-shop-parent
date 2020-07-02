package com.zdd.weixin.service.impl;

import com.zdd.service.api.weixin.WeixinService;
import com.zdd.weixin.entry.AppEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xin
 * @date 2020/7/1 11:52 上午
 * @Content:
 */
@RestController
public class WeixinServiceImpl implements WeixinService {
    @Value("${zdd}")
    private String value;

    @Override
    public AppEntry getApp() {
        return new AppEntry(value, "1234");
    }

}
