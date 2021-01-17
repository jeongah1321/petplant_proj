package com.petplant.web.service.member;

import java.util.List;

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
	public String loginCheck(MemberDTO dto, HttpSession session) {
		// 맞으면 이름이 넘어오고 틀리면 null이 넘어옴 
		String login_name = memberDao.loginCheck(dto);
		
		if(login_name != null) { // 맞으면 
			// 세션변수 등록
			session.setAttribute("login_id", dto.getLogin_id());
			session.setAttribute("login_name", login_name);
		}
		return login_name;
	}

	// 로그아웃
	@Override
	public void logout(HttpSession session) {
		// 세션을 모두 초기화시킴 
		session.invalidate();
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
