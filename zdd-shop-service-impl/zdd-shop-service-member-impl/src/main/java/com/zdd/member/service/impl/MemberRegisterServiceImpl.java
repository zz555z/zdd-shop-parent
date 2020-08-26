package com.zdd.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zdd.core.base.BaseApiService;
import com.zdd.core.base.BaseResponse;
import com.zdd.core.constants.Constants;
import com.zdd.core.utils.MD5Util;
import com.zdd.core.utils.MiteBeanUtils;
import com.zdd.member.dto.UserDO;
import com.zdd.member.feign.VerificaCodeServiceFeign;
import com.zdd.member.intput.dto.UserIntDTO;
import com.zdd.member.mapper.UserMapper;
import com.zdd.service.api.member.MemberRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private VerificaCodeServiceFeign verificaCodeServiceFeign;

    @Override
    @Transactional
    public BaseResponse<JSONObject> register(@RequestBody UserIntDTO userIntDTO, String registCode) {
//        if (StringUtils.isEmpty(userIntDTO.getUserName())) {
//            return setResultError("用户名不能为空");
//        }
        if (StringUtils.isEmpty(userIntDTO.getMobile())) {
            return setResultError("手机号不能为空");
        }
        String password = userIntDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空");
        }
        BaseResponse<JSONObject> response = verificaCodeServiceFeign.verificaWeixinCode(userIntDTO.getMobile(), registCode);
        if (!response.getCode().equals(Constants.HTTP_RES_CODE_200)) {
            return setResultError(response.getMsg());
        }
//        UserDO userDO = new UserDO();
//        BeanUtils.copyProperties(userIntDTO, userDO);
        String newPwd = MD5Util.MD5(password);
        userIntDTO.setPassword(newPwd);
        UserDO userDO = MiteBeanUtils.dtoToDo(userIntDTO, UserDO.class);
        return userMapper.register(userDO) > 0 ? setResultSuccess("注册成功") : setResultError("注册失败");
    }


}
