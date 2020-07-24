package com.zdd.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zdd.core.base.BaseApiService;
import com.zdd.core.base.BaseResponse;
import com.zdd.core.utils.MD5Util;
import com.zdd.member.entry.UserEntity;
import com.zdd.member.mapper.UserMapper;
import com.zdd.service.api.member.MemberRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xin
 * @date 2020/7/24 2:34 下午
 * @Content:
 */
@RestController
public class MemberRegisterServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public BaseResponse<JSONObject> register(UserEntity userEntity, String registCode) {
        if (StringUtils.isEmpty(userEntity.getUserName())) {
            return setResultError("用户名不能为空");
        }
        if (StringUtils.isEmpty(userEntity.getMobile())) {
            return setResultError("手机号不能为空");
        }
        String password = userEntity.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空");
        }

        String newPwd = MD5Util.MD5(password);
        userEntity.setPassword(newPwd);
        int register = userMapper.register(userEntity);
        return register > 0 ? setResultSuccess("注册成功") : setResultError("注册失败");
    }


}
