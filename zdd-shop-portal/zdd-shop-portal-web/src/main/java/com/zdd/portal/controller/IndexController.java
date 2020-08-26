package com.zdd.portal.controller;

import com.alibaba.fastjson.JSONObject;
import com.zdd.core.base.BaseResponse;
import com.zdd.core.constants.Constants;
import com.zdd.member.feign.MemberLoginFeign;
import com.zdd.member.feign.MemberServiceFeign;
import com.zdd.member.output.dto.UserOutDTO;
import com.zdd.web.base.BaseWebController;
import com.zdd.web.constants.ConstantsWeb;
import com.zdd.web.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Xin
 * @date 2020/8/20 5:21 下午
 * @Content:
 */
@Controller
@Slf4j
public class IndexController extends BaseWebController {
    private static final String INDEX_FTL = "index";
    @Autowired
    private MemberServiceFeign memberServiceFeign;
    @Autowired
    private MemberLoginFeign memberLoginFeign;

    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 1.从cookie 中 获取 会员token
        String token = CookieUtils.getCookieValue(request, ConstantsWeb.LOGIN_TOKEN_COOKIENAME, true);
        if (!StringUtils.isEmpty(token)) {
            // 2.调用会员服务接口,查询会员用户信息
            BaseResponse<UserOutDTO> userInfo = memberServiceFeign.getInfo(token);
            if (isSuccess(userInfo)) {
                UserOutDTO data = userInfo.getData();
                if (data != null) {
                    String mobile = data.getMobile();
                    // 对手机号码实现脱敏
                    String desensMobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                    model.addAttribute("desensMobile", desensMobile);
                }

            }

        }
        return INDEX_FTL;
    }

    /**
     * 跳页面
     *
     * @return
     */
    @GetMapping("/index.html")
    public String indexFtl() {
        return INDEX_FTL;
    }


    @GetMapping("/exit.html")
    public String exit(HttpServletRequest request, HttpServletResponse response) {
        //  1 获取当前cookie信息
        String token = CookieUtils.getCookieValue(request, ConstantsWeb.LOGIN_TOKEN_COOKIENAME, true);
        log.info("当前cookie中token为：{}", token);
        if (!StringUtils.isEmpty(token)) {
            // 修改数据库  删除redis
            BaseResponse<JSONObject> exit = memberLoginFeign.exit(token);
            log.info("调用接口返回数据", exit.toString());
            if (isSuccess(exit)) {
                //2  删除cookie
                CookieUtils.deleteCookie(request, response, ConstantsWeb.LOGIN_TOKEN_COOKIENAME);
            }
        }

        return INDEX_FTL;
    }
}
