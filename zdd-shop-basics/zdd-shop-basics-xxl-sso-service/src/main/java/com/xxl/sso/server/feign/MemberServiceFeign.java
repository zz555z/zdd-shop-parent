package com.xxl.sso.server.feign;

import com.zdd.service.api.member.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Xin
 * @date 2021/3/10 4:25 下午
 * @Content:
 */
@FeignClient("app-zdd-member")
public interface MemberServiceFeign extends MemberService {
}
