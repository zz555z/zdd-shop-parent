package com.zdd.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.zdd.core.base.BaseResponse;
import com.zdd.core.constants.Constants;
import com.zdd.member.feign.MemberLoginFeign;
import com.zdd.member.intput.dto.UserIntDTO;
import com.zdd.member.intput.dto.UserLoginInpDTO;
import com.zdd.vo.LoginVo;
import com.zdd.vo.RegisterVo;
import com.zdd.web.base.BaseWebController;
import com.zdd.web.constants.ConstantsWeb;
import com.zdd.web.utils.CookieUtils;
import com.zdd.web.utils.MeiteBeanUtils;
import com.zdd.web.utils.RandomValidateCodeUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Xin
 * @date 2020/8/20 5:24 下午
 * @Content:
 */
@Controller
@Slf4j
public class LoginController extends BaseWebController {
    private static final String LOGIN_FTL = "/member/login";
    /**
     * 重定向到首页
     */
    private static final String REDIRECT_INDEX = "redirect:/";

    @Autowired
    private MemberLoginFeign memberLoginFeign;

    /**
     * 跳页面
     *
     * @return
     */
    @GetMapping("/login.html")
    public String login() {
        return LOGIN_FTL;
    }

    /**
     * 接收参数
     *
     * @return
     */
    @PostMapping("/login.html")
    public String postLogin(@ModelAttribute("loginVo") @Validated LoginVo loginVo,
                            BindingResult bindingResult, Model model, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
        log.info("接收参数：{}", loginVo.toString());
        // 1。接收参数
        if (bindingResult.hasErrors()) {
            String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
            setErrorMsg(model, defaultMessage);
            return LOGIN_FTL;
        }
        // 2。验证图形验证吗
        Boolean aBoolean = RandomValidateCodeUtil.checkVerify(loginVo.getGraphicCode(), httpSession);
        if (!aBoolean) {
            setErrorMsg(model, "验证码错误");
            return LOGIN_FTL;
        }

        // 3。调用接口
        UserLoginInpDTO userLoginInpDTO = MeiteBeanUtils.voToDto(loginVo, UserLoginInpDTO.class);
        userLoginInpDTO.setLoginType(Constants.LOGIN_TYPE_PC);
        userLoginInpDTO.setDeviceInfor(webBrowserInfo(request));
        BaseResponse<JSONObject> login = memberLoginFeign.login(userLoginInpDTO);
        log.info("接口返回参数：{}", login.toString());

        if (!isSuccess(login)) {
            setErrorMsg(model, login.getMsg());
            return LOGIN_FTL;
        }
        String token = login.getData().getString("token");
        //4.登陆成功 token存入cookie
        CookieUtils.setCookie(request, response, ConstantsWeb.LOGIN_TOKEN_COOKIENAME, token);

        return REDIRECT_INDEX;
    }





}
