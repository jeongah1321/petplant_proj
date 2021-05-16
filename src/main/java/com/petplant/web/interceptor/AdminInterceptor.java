package com.petplant.web.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInterceptor extends HandlerInterceptorAdapter {
    
	/** 로그인 실행전 처리하는 메소드 **/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("aresultDTO") == null) {
			response.sendRedirect(request.getContextPath() + "/admin/login.do?message=nologin");

			return false;
		}else {
			return true; 
		}
	}

	/** 로그인 실행후 처리하는 메소드 **/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}