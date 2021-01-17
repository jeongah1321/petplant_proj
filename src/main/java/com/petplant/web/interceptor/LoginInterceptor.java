package com.petplant.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
 
 
// HandlerInterceptorAdapter 추상클래스 상속
// preHandle(), postHandler() 오버라이딩
public class LoginInterceptor extends HandlerInterceptorAdapter {
    
	// 로그인을 하기전에 로그인이 되어있는 상태인지 검사하고 로그인이 되어있으면 메인 액션으로 이동하고,
	// 로그인이 안되어있으면 로그인 페이지로 이동시킨다.
	/** 메인 액션이 실행되기 전 실행되는 메소드 **/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		//세션 객체 생성
		HttpSession session = request.getSession();
        
		if(session.getAttribute("admin_login_id") == null) {
			//세션이 없으면(로그인되지 않은 상태)
			if(session.getAttribute("login_id") == null) {
				//login 페이지로 이동
				response.sendRedirect(request.getContextPath() + "/member/login.do?message=nologin");
				return false; //메인 액션으로 가지 않음
			} else {
				return true; //메인 액션으로 이동
			}
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