package com.zdd.service.api.member;

import com.zdd.core.base.BaseResponse;
import com.zdd.member.intput.dto.UserIntDTO;
import com.zdd.member.intput.dto.UserLoginInpDTO;
import com.zdd.member.output.dto.UserOutDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Xin
 * @date 2020/7/1 1:48 下午
 * @Content:
 */
@Api(tags = "会员服务接口")
public interface MemberService {




    /**
     * 根据手机号码查询是否已经存在,如果存在返回当前用户信息
     *
     * @param mobile
     * @return
     */
    @ApiOperation(value = "根据手机号码查询是否已经存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "用户手机号码"),})
    @PostMapping("/existMobile")
    BaseResponse<UserOutDTO> existMobile(@RequestParam("mobile") String mobile);


    /**
     * 根据token查询用户信息
     *
     * @param token
     * @return
     */
    @GetMapping("/getUserInfo")
    @ApiOperation(value = "/getUserInfo")
    BaseResponse<UserOutDTO> getInfo(@RequestParam("token") String token);


    /**
     * sso单点登录查询用户信息
     * @param userLoginInpDTO
     * @return
     */
    @PostMapping("/ssologin")
    @ApiOperation(value = "/ssologin")
    BaseResponse<UserOutDTO> ssologin(@RequestBody UserLoginInpDTO userLoginInpDTO);



}
