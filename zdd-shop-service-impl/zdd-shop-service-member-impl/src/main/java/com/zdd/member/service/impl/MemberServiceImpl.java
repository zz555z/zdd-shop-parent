package com.zdd.member.service.impl;

import com.zdd.member.feign.WeixinServiceFeign;
import com.zdd.service.api.member.MemberService;
import com.zdd.weixin.entry.AppEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xin
 * @date 2020/7/1 1:56 下午
 * @Content:
 */
@RestController
@Slf4j
public class MemberServiceImpl implements MemberService {
    @Autowired
    private WeixinServiceFeign weixinServiceFeign;

    @Override
    public AppEntry memberToWeixin() {
        log.info("会员调用微信服务");
        return weixinServiceFeign.getApp();
    }
}
