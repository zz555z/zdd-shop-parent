package com.zdd.service.api.member;

import com.alibaba.fastjson.JSONObject;
import com.zdd.core.base.BaseResponse;
import com.zdd.member.intput.dto.UserLoginInpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "用户登陆服务接口")
public interface MemberLoginService {
	/**
	 * 用户登陆接口
	 * 
	 * @param userLoginInpDTO
	 * @return
	 */
	@PostMapping("/login")
	@ApiOperation(value = "会员用户登陆信息接口")
	BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO);

	/**
	 * 用户登陆退出接口
	 *
	 * @param token
	 * @return
	 */
	@PostMapping("/exit")
	@ApiOperation(value = "会员用户退出登陆信息接口")
	BaseResponse<JSONObject> exit(@RequestParam("token") String token);

}