package com.petplant.web.service.member;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.petplant.web.model.member.dao.MemberDAO;
import com.petplant.web.model.member.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	MemberDAO memberDao;

	// 로그인
	@Override
	public MemberDTO loginCheck(MemberDTO dto, HttpSession session) {

		MemberDTO resultDTO = memberDao.loginCheck(dto);
		
		if(resultDTO != null) {
			session.setAttribute("resultDTO", resultDTO);
			
			return resultDTO;
		}	
		return null;
	}

	// 로그아웃
	@Override
	public void logout(HttpSession session) {
		
		session.invalidate();
	}
	
	//아이디 중복 확인
	@Override
	public int idCheck(String login_id){
		
		return memberDao.idCheck(login_id);
	}
		

	// 회원 가입 
	@Override
	public void insert(MemberDTO dto) {
		
		memberDao.insert(dto);
	}

	// 로그인 아이디 찾기
	@Override
	public String doFindLoginId(MemberDTO dto) {
		
		return memberDao.doFindLoginId(dto);
	}

	// 로그인 비밀번호 찾기 
	@Override
	public String doFindLoginPasswd(MemberDTO dto) {
		
		return memberDao.doFindLoginPasswd(dto);
	}

	// 로그인 비밀번호 변경
	@Override
	public void updateLoginPasswd(MemberDTO dto) {
		
		memberDao.updateLoginPasswd(dto);
	}
}
