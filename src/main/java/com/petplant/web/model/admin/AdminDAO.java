package com.petplant.web.model.admin;

import com.petplant.web.model.member.dto.MemberDTO;

public interface AdminDAO {
	public String loginCheck(MemberDTO dto); // dto를 받아서 관리자 로그인을 체크하는 메소드

}
