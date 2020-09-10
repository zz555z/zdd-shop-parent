package com.zdd.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zdd.core.base.BaseApiService;
import com.zdd.core.base.BaseResponse;
import com.zdd.core.constants.Constants;
import com.zdd.core.enums.loginTypeEnum;
import com.zdd.core.token.GenerateToken;
import com.zdd.core.transaction.RedisDataSoureceTransaction;
import com.zdd.core.utils.MD5Util;
import com.zdd.core.utils.RedisUtil;
import com.zdd.core.utils.TypeCastHelper;
import com.zdd.member.dto.UserDO;
import com.zdd.member.dto.UserTokenDo;
import com.zdd.member.intput.dto.UserLoginInpDTO;
import com.zdd.member.mapper.UserMapper;
import com.zdd.member.mapper.UserTokenMapper;
import com.zdd.service.api.member.MemberLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xin
 * @date 2020/8/6 4:43 下午
 * @Content:
 */
@RestController
public class MemberLoginServiceImpl extends BaseApiService<JSONObject> implements MemberLoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GenerateToken generateToken;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private RedisDataSoureceTransaction redisDataSoureceTransaction;

    @Override
    public BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO) {
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
        UserDO login = userMapper.login(mobile, newPwd);
        if (login == null) {
            return setResultError("用户名或密码错误");
        }
        TransactionStatus transactionStatus = null;
        JSONObject data = new JSONObject();

        try {
            // 根据userid和logintype 查询是否登陆， 登陆的话就删除redis 修改数据库状态。
            Long userid = login.getUserid();
            UserTokenDo userTokenDo = userTokenMapper.selectByUserIdAndLoginType(userid, loginType);
            // 开启数据库，redis 事务
            transactionStatus = redisDataSoureceTransaction.begin();
            if (userTokenDo != null) {
                generateToken.removeToken(userTokenDo.getToken());
                int availability = userTokenMapper.updateTokenAvailability(userTokenDo.getToken());
                if (!toDaoResult(availability)) {
                    redisDataSoureceTransaction.rollback(transactionStatus);
                    setResultError("系统错误");
                }
            }

            // 如果有传递oppid参数， 修改数据库
            String qqOpenId = userLoginInpDTO.getQqOpenId();
            if (!StringUtils.isEmpty(qqOpenId)) {
                userMapper.updateUserOpenId(qqOpenId, userid);
            }


            String token = generateToken.createToken(Constants.MEMBER_TOKEN_KEYPREFIX + loginType, login.getUserid() + "");
            // 插入一个新token
            UserTokenDo newUsertoken = new UserTokenDo();
            newUsertoken.setLoginType(loginType);
            newUsertoken.setUserId(userid);
            newUsertoken.setToken(token);
            newUsertoken.setDeviceInfor(userLoginInpDTO.getDeviceInfor());
            int token1 = userTokenMapper.insertUserToken(newUsertoken);
            if (!toDaoResult(token1)) {
                redisDataSoureceTransaction.rollback(transactionStatus);
                setResultError("系统错误");
            }
            //3。生成token
            data.put("token", token);
            redisDataSoureceTransaction.commit(transactionStatus);
        } catch (Exception e) {
            try {
                redisDataSoureceTransaction.rollback(transactionStatus);
                return setResultError("系统错误");
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }

        return setResultSuccess(data);

    }


    @Override
    public BaseResponse<JSONObject> exit(String token) {
        // 删除redis的token 修改数据库状态

        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = redisDataSoureceTransaction.begin();
            generateToken.removeToken(token);
            int availability = userTokenMapper.updateTokenAvailability(token);
            if (!toDaoResult(availability)) {
                redisDataSoureceTransaction.rollback(transactionStatus);
                setResultError("系统错误");
            }
            redisDataSoureceTransaction.commit(transactionStatus);

        } catch (Exception e) {
            try {
                redisDataSoureceTransaction.rollback(transactionStatus);
                return setResultError("系统错误");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return setResultSuccess();
    }


}
