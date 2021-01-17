package com.petplant.web.service.admin;

import javax.servlet.http.HttpSession;

import com.petplant.web.model.member.dto.MemberDTO;

public interface AdminService {
	public String loginCheck(MemberDTO dto, HttpSession session); // 로그인 
	public void logout(HttpSession session); // 로그아웃	
}
