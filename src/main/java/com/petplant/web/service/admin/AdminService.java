package com.petplant.web.service.admin;

import javax.servlet.http.HttpSession;

import com.petplant.web.model.admin.dto.AdminDTO;

public interface AdminService {
	public AdminDTO loginCheck(AdminDTO dto, HttpSession session); // 로그인 
	public void logout(HttpSession session); // 로그아웃	
}
