package com.petplant.web.model.member.dao;

import com.petplant.web.model.member.dto.MemberDTO;

public interface MemberDAO {
	public String loginCheck(MemberDTO dto); // 로그인
	public void insert(MemberDTO dto);  // 회원가입 
	public String doFindLoginId(MemberDTO dto); // 로그인 아이디 찾기
	public String doFindLoginPasswd(MemberDTO dto); // 로그인 비밀번호 찾기
	public void updateLoginPasswd(MemberDTO dto); // 로그인 비밀번호 변경	
}
