package com.petplant.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.petplant.web.model.admin.dto.AdminDTO;
import com.petplant.web.model.member.dto.MemberDTO;
 

public class LoginInterceptor extends HandlerInterceptorAdapter {
    
	/** 메인 액션이 실행되기 전 실행되는 메소드 **/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {

		HttpSession session = request.getSession();
		
		if(session.getAttribute("resultDTO") == null) {
			response.sendRedirect(request.getContextPath() + "/member/login.do?message=nologin");
			
			return false; //메인 액션으로 가지 않음
		} else {
			return true; //메인 액션으로 이동
		}
	}
    
	/** 메인 액션이 실행된 후 실행되는 메소드 **/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}