package com.zdd.service.api.member;

import com.zdd.weixin.entry.AppEntry;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Xin
 * @date 2020/7/1 1:48 下午
 * @Content:
 */
public interface MemberService {

    @GetMapping("/memberToWeixin")
    public AppEntry memberToWeixin();
}
