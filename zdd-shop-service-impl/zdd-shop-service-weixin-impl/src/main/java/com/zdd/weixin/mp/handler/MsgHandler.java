package com.zdd.weixin.mp.handler;

import com.zdd.core.base.BaseResponse;
import com.zdd.core.constants.Constants;
import com.zdd.core.utils.RedisUtil;
import com.zdd.core.utils.RegexUtils;
import com.zdd.member.output.dto.UserOutDTO;
import com.zdd.weixin.feign.MemberServiceFeign;
import com.zdd.weixin.mp.builder.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    // 用户发送手机验证码提示
    @Value("${zdd.weixin.registration.code.message}")
    private String registrationCodeMessage;
    // 默认用户发送验证码提示
    @Value("${zdd.weixin.default.registration.code.message}")
    private String defaultRegistrationCodeMessage;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            // TODO 可以选择将消息保存到本地
        }

        // 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                    && weixinService.getKefuService().kfOnlineList().getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        // 获取微信传过来的消息
        String wxMessageContent = wxMessage.getContent();

        if (RegexUtils.checkMobile(wxMessageContent)) {
            BaseResponse<UserOutDTO> response = memberServiceFeign.existMobile(wxMessageContent);
            if (response.getCode().equals(Constants.HTTP_RES_CODE_200)) {
                return new TextBuilder().build("该手机号：" + wxMessageContent + "已经存在！！", wxMessage, weixinService);
            }

            if (!response.getCode().equals(Constants.HEEP_RES_CODE_EXISMOBILE_203)) {
                return new TextBuilder().build(response.getMsg(), wxMessage, weixinService);
            }
            // 生成验证码
            int registCode = registCode();
            // 替换字符
            String format = registrationCodeMessage.format(registrationCodeMessage, registCode);
            // 注册码存入redis
            redisUtil.setString(Constants.WEIXINCODE_KEY + wxMessageContent, registCode + "", Constants.WEIXINCODE_TIMEOUT);
            return new TextBuilder().build(format, wxMessage, weixinService);
        }

        return new TextBuilder().build(defaultRegistrationCodeMessage, wxMessage, weixinService);
    }

    // 获取注册码
    private int registCode() {
        int registCode = (int) (Math.random() * 9000 + 1000);
        return registCode;
    }

}
