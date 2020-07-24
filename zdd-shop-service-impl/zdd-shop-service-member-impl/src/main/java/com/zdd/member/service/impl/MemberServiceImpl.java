package com.zdd.member.service.impl;

import com.zdd.core.base.BaseApiService;
import com.zdd.core.base.BaseResponse;
import com.zdd.core.constants.Constants;
import com.zdd.member.entry.UserEntity;
import com.zdd.member.feign.WeixinServiceFeign;
import com.zdd.member.mapper.UserMapper;
import com.zdd.service.api.member.MemberService;
import com.zdd.weixin.entry.AppEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xin
 * @date 2020/7/1 1:56 下午
 * @Content:
 */
@RestController
@Slf4j
public class MemberServiceImpl extends BaseApiService<UserEntity> implements MemberService {
    @Autowired
    private WeixinServiceFeign weixinServiceFeign;

    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseResponse<UserEntity> existMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)){
            return setResultError("手机号不能为空");
        }

        UserEntity userEntity = userMapper.existMobile(mobile);
        if (userEntity==null){
            return setResultError(Constants.HEEP_RES_CODE_EXISMOBILE_203,"用户信息不存在");
        }
        userEntity.setPassword(null);

        return setResultSuccess(userEntity);
    }
}
