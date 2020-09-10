package com.zdd.qq.controller;

import com.alibaba.fastjson.JSONObject;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.zdd.core.base.BaseResponse;
import com.zdd.core.constants.Constants;
import com.zdd.member.feign.MemberLoginFeign;
import com.zdd.member.feign.QQAuthoriFeign;
import com.zdd.member.intput.dto.UserLoginInpDTO;
import com.zdd.vo.LoginVo;
import com.zdd.web.base.BaseWebController;
import com.zdd.web.constants.ConstantsWeb;
import com.zdd.web.utils.CookieUtils;
import com.zdd.web.utils.MeiteBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class QQAuthoriController extends BaseWebController {
    @Autowired
    private QQAuthoriFeign qqAuthoriFeign;
    private static final String MB_QQ_QQLOGIN = "member/qqlogin";
    @Autowired
    private MemberLoginFeign memberLoginFeign;
    /**
     * 重定向到首页
     */
    private static final String REDIRECT_INDEX = "redirect:/";

    /**
     * 生成QQ授权回调地址
     *
     * @return
     */
    @RequestMapping("/qqAuth")
    public String qqAuth(HttpServletRequest request) {
        try {
            String authorizeURL = new Oauth().getAuthorizeURL(request);
            return "redirect:" + authorizeURL;
        } catch (Exception e) {
            return ERROR_500_FTL;
        }
    }


    /**
     * qq 回调地址
     *
     * @param request
     * @param response
     * @param httpSession
     * @return
     */
    @RequestMapping("/qqLoginBack")
    public String qqLoginBack(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            if (accessTokenObj == null) {
                return ERROR_500_FTL;
            }
            String accessToken = accessTokenObj.getAccessToken();
            if (StringUtils.isEmpty(accessToken)) {
                return ERROR_500_FTL;
            }
            // 获取用户openid
            OpenID openIDObj = new OpenID(accessToken);

            String openId = openIDObj.getUserOpenID();
            if (StringUtils.isEmpty(openId)) {
                return ERROR_500_FTL;
            }
            BaseResponse<JSONObject> findByOpenId = qqAuthoriFeign.findByOpenId(openId);
            if (!isSuccess(findByOpenId)) {
                return ERROR_500_FTL;
            }
            Integer resultCode = findByOpenId.getCode();
            // 如果使用openid没有查询到用户信息，则跳转到绑定用户信息页面
            if (resultCode.equals(Constants.HTTP_RES_CODE_NOTUSER_203)) {
                // 使用openid获取用户信息
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openId);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                if (userInfoBean == null) {
                    return ERROR_500_FTL;
                }
                String avatarURL100 = userInfoBean.getAvatar().getAvatarURL100();
                // 返回用户头像页面展示
                request.setAttribute("avatarURL100", avatarURL100);
                httpSession.setAttribute(ConstantsWeb.LOGIN_QQ_OPENID, openId);
                return MB_QQ_QQLOGIN;
            }
            // 自动实现登陆
            JSONObject data = findByOpenId.getData();
            String token = data.getString("token");
            CookieUtils.setCookie(request, response, ConstantsWeb.LOGIN_TOKEN_COOKIENAME, token);
            return REDIRECT_INDEX;
        } catch (Exception e) {
            return ERROR_500_FTL;
        }

    }


    @RequestMapping("/qqJointLogin")
    public String qqJointLogin(@ModelAttribute("loginVo") LoginVo loginVo, Model model, HttpServletRequest request,
                               HttpServletResponse response, HttpSession httpSession) {

        // 1.获取用户openid
        String qqOpenId = (String) httpSession.getAttribute(ConstantsWeb.LOGIN_QQ_OPENID);
        if (StringUtils.isEmpty(qqOpenId)) {
            return ERROR_500_FTL;
        }

        // 2.将vo转换dto调用会员登陆接口
        UserLoginInpDTO userLoginInpDTO = MeiteBeanUtils.voToDto(loginVo, UserLoginInpDTO.class);
        userLoginInpDTO.setQqOpenId(qqOpenId);
        userLoginInpDTO.setLoginType(Constants.LOGIN_TYPE_PC);
        String info = webBrowserInfo(request);
        userLoginInpDTO.setDeviceInfor(info);
        BaseResponse<JSONObject> login = memberLoginFeign.login(userLoginInpDTO);
        if (!isSuccess(login)) {
            setErrorMsg(model, login.getMsg());
            return MB_QQ_QQLOGIN;
        }
        // 3.登陆成功之后如何处理 保持会话信息 将token存入到cookie 里面 首页读取cookietoken 查询用户信息返回到页面展示
        JSONObject data = login.getData();
        String token = data.getString("token");
        CookieUtils.setCookie(request, response, ConstantsWeb.LOGIN_TOKEN_COOKIENAME, token);
        return REDIRECT_INDEX;
    }


}