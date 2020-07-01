package com.zdd.service.api.member;

import com.zdd.weixin.entry.AppEntry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Xin
 * @date 2020/7/1 1:48 下午
 * @Content:
 */
@Api(tags = "会员服务接口")
public interface MemberService {

    @ApiOperation(value = "会员服务调用微信服务接口")
    @GetMapping("/memberToWeixin")
    public AppEntry memberToWeixin();
}
