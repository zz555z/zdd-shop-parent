package com.zdd.weixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zdd.core.base.BaseApiService;
import com.zdd.core.base.BaseResponse;
import com.zdd.core.constants.Constants;
import com.zdd.core.utils.RedisUtil;
import com.zdd.service.api.weixin.VerificaCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xin
 * @date 2020/7/8 9:39 上午
 * @Content:
 */
@RestController
public class VerificaCodeServiceImpl extends BaseApiService<JSONObject> implements VerificaCodeService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseResponse<JSONObject> verificaWeixinCode(String phone, String weixinCode) {
        // 1.验证参数是否为null
        if (StringUtils.isEmpty(phone)) {
            return setResultError("手机号不能为空");
        }
        if (StringUtils.isEmpty(weixinCode)) {
            return setResultError("微信验证码不能为空");
        }

        String key = Constants.WEIXINCODE_KEY + phone;
        String value = redisUtil.getString(key);
        if (StringUtils.isEmpty(value)) {
            return setResultError("注册码已过期");
        }

        if (!value.equals(weixinCode)) {
            return setResultError("注册码不正确");
        }

        redisUtil.delKey(key);
        return setResultSuccess("验证码正确");
    }
}
