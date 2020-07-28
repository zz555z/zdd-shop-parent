package com.zdd.weixin.feign;

import com.zdd.service.api.member.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Xin
 * @date 2020/7/28 2:02 下午
 * @Content:
 */
@FeignClient("app-zdd-member")
public interface MemberServiceFeign extends MemberService {
}
