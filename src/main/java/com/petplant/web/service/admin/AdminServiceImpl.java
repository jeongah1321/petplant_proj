package com.petplant.web.service.admin;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.petplant.web.model.admin.AdminDAO;
import com.petplant.web.model.member.dto.MemberDTO;

@Service // service이므로 service어노테이션을 사용함
public class AdminServiceImpl implements AdminService {

	@Inject
	AdminDAO adminDao; // dao를 호출해야하므로 inject로 의존성을 주입하고 사용
	
	/** 로그인 **/
	@Override
	public String loginCheck(MemberDTO dto, HttpSession session) {
		return adminDao.loginCheck(dto); 
	}
	
	/** 로그아웃 **/
	@Override
	public void logout(HttpSession session) {
		// 세션을 모두 초기화시킴 
		session.invalidate();
	}

	
}
