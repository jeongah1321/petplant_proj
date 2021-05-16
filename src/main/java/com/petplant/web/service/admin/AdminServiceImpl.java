package com.petplant.web.service.admin;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.petplant.web.model.admin.dao.AdminDAO;
import com.petplant.web.model.admin.dto.AdminDTO;

@Service
public class AdminServiceImpl implements AdminService {

	@Inject
	AdminDAO adminDao;
	
	/** 로그인 **/
	@Override
	public AdminDTO loginCheck(AdminDTO dto, HttpSession session) {
		
		AdminDTO resultDTO = adminDao.loginCheck(dto);
		System.out.println(dto);
		
		if(resultDTO != null) {
			session.setAttribute("aresultDTO", resultDTO);
			
			return resultDTO;
		}	
		return null;
	}
	
	/** 로그아웃 **/
	@Override
	public void logout(HttpSession session) {
		
		session.invalidate();
	}

	
}
