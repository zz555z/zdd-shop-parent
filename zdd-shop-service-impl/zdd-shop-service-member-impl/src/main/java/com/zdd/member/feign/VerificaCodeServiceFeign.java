package com.zdd.member.feign;

import com.zdd.service.api.weixin.VerificaCodeService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Xin
 * @date 2020/7/28 2:38 下午
 * @Content:
 */
@FeignClient("app-zdd-weixin")
public interface VerificaCodeServiceFeign extends VerificaCodeService {
}
