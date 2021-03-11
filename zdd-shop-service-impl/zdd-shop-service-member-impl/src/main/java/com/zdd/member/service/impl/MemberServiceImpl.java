package com.zdd.member.service.impl;

import com.zdd.core.base.BaseApiService;
import com.zdd.core.base.BaseResponse;
import com.zdd.core.constants.Constants;
import com.zdd.core.token.GenerateToken;
import com.zdd.core.utils.MD5Util;
import com.zdd.core.utils.MiteBeanUtils;
import com.zdd.core.utils.TypeCastHelper;
import com.zdd.member.dto.UserDO;
import com.zdd.member.intput.dto.UserLoginInpDTO;
import com.zdd.member.mapper.UserMapper;
import com.zdd.member.output.dto.UserOutDTO;
import com.zdd.service.api.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author Xin
 * @date 2020/7/1 1:56 下午
 * @Content:
 */
@RestController
@Slf4j
public class MemberServiceImpl extends BaseApiService<UserOutDTO> implements MemberService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GenerateToken generateToken;

    @Override
    public BaseResponse<UserOutDTO> existMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号不能为空");
        }

        UserDO userDT = userMapper.existMobile(mobile);
        if (userDT == null) {
            return setResultError(Constants.HEEP_RES_CODE_EXISMOBILE_203, "用户信息不存在");
        }
//      userEntity.setPassword(null);
//        UserOutDTO userOutDTO = new UserOutDTO();
//        BeanUtils.copyProperties(userDTO, userOutDTO);
        UserOutDTO userOutDTO = MiteBeanUtils.doToDto(userDT, UserOutDTO.class);
        return setResultSuccess(userOutDTO);
    }

    @Override
    public BaseResponse<UserOutDTO> getInfo(String token) {
        // 1.参数验证
        if (StringUtils.isEmpty(token)) {
            return setResultError("token不能为空!");
        }
        // 2.使用token向redis中查询userId
        String redisValue = generateToken.getToken(token);
        if (StringUtils.isEmpty(redisValue)) {
            return setResultError("token已经失效或者不正确");
        }
        Long userId = TypeCastHelper.toLong(redisValue);
        // 3.根据userId查询用户信息
        UserDO userDo = userMapper.findByUserId(userId);
        if (userDo == null) {
            return setResultError("用户信息不存在!");
        }
        // 4.将Do转换为Dto
        UserOutDTO doToDto = MiteBeanUtils.doToDto(userDo, UserOutDTO.class);
        return setResultSuccess(doToDto);
    }

    @Override
    public BaseResponse<UserOutDTO> ssologin(@RequestBody UserLoginInpDTO userLoginInpDTO) {
//1.验证参数
        // 1.验证参数
        String mobile = userLoginInpDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        String password = userLoginInpDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        String loginType = userLoginInpDTO.getLoginType();
        if (loginType == null) {
            return setResultError("登陆类型不能为空!");
        }
        if (!Constants.LOGIN_TYPE_ANDROID.equals(loginType) && !Constants.LOGIN_TYPE_PC.equals(loginType) && !Constants.LOGIN_TYPE_IOS.equals(loginType)) {
            return setResultError("登陆类型出现错误!");
        }
        //2。密码验证
        String newPwd = MD5Util.MD5(userLoginInpDTO.getPassword());
        UserDO user = userMapper.login(mobile, newPwd);
        if (user == null) {
            return setResultError("用户名或密码错误");
        }
        return setResultSuccess(MiteBeanUtils.doToDto(user,UserOutDTO.class));
    }
}
