package com.zdd.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.zdd.core.base.BaseResponse;
import com.zdd.member.feign.MemberRegisterFeign;
import com.zdd.member.intput.dto.UserIntDTO;
import com.zdd.vo.RegisterVo;
import com.zdd.web.base.BaseWebController;
import com.zdd.web.utils.MeiteBeanUtils;
import com.zdd.web.utils.RandomValidateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
//@Validated
public class RegisterController extends BaseWebController {
	private static final String MEMBER_REGISTER_FTL = "member/register";
	private static final String LOGIN_FTL = "/member/login";

	@Autowired
	private MemberRegisterFeign memberRegisterFeign;

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@GetMapping("/register.html")
	public String getRegister() {
		return MEMBER_REGISTER_FTL;
	}

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@PostMapping("/register.html")
	public String postRegister( @ModelAttribute("registerVo") @Validated RegisterVo registerVo,
							   BindingResult bindingResult, Model model, HttpSession httpSession) {
		log.info("接收参数：{}",registerVo.toString());
		// 1。接收参数
		if (bindingResult.hasErrors()){
			String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
			setErrorMsg(model,defaultMessage);
			return MEMBER_REGISTER_FTL;
		}
		// 2。验证图形验证吗
		Boolean aBoolean = RandomValidateCodeUtil.checkVerify(registerVo.getGraphicCode(), httpSession);
		if (!aBoolean){
			setErrorMsg(model,"验证码错误");
			return MEMBER_REGISTER_FTL;
		}

		// 3。调用接口
		UserIntDTO userIntDTO = MeiteBeanUtils.voToDto(registerVo, UserIntDTO.class);
		BaseResponse<JSONObject> register = memberRegisterFeign.register(userIntDTO, registerVo.getRegistCode());
		log.info("接口返回参数：{}",register.toString());

		if (!isSuccess(register)){
			setErrorMsg(model,register.getMsg());
			return MEMBER_REGISTER_FTL;
		}
		// 跳转登陆页面
		return LOGIN_FTL;
	}

}