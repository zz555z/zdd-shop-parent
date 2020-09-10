package com.zdd.member.feign;

import com.zdd.service.api.member.QQAuthoriService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Xin
 * @date 2020/8/26 3:23 下午
 * @Content:
 */
@FeignClient("app-zdd-member")
public interface QQAuthoriFeign extends QQAuthoriService {
}
