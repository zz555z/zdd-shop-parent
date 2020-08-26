package com.zdd.member.feign;

import com.zdd.service.api.member.MemberLoginService;
import com.zdd.service.api.member.MemberRegisterService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Xin
 * @date 2020/8/24 4:47 下午
 * @Content:
 */
@FeignClient("app-zdd-member")
public interface MemberLoginFeign extends MemberLoginService {
}
