package com.petplant.web.model.admin.dao;

import com.petplant.web.model.admin.dto.AdminDTO;

public interface AdminDAO {	
	public AdminDTO loginCheck(AdminDTO dto); // dto를 받아서 관리자 로그인을 체크하는 메소드
}
