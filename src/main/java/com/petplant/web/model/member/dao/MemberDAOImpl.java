package com.petplant.web.model.member.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.petplant.web.model.member.dto.MemberDTO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Inject
	SqlSession sqlSession; 
	
	// 로그인
	@Override
	public MemberDTO loginCheck(MemberDTO dto) {
		
		return sqlSession.selectOne("member.login_check", dto);
	}
	
	//아이디 중복 여부 확인 
	@Override
	public int idCheck(String login_id){
		return sqlSession.selectOne("member.idCheck", login_id);
	}

	// 회원 가입 
	@Override
	public void insert(MemberDTO dto) {
		
		sqlSession.insert("member.insert", dto); 
	}

	// 로그인 아이디 찾기
	@Override
	public String doFindLoginId(MemberDTO dto) {
		
		String result = null;

		String check = sqlSession.selectOne("member.findLoginId", dto);
					
		if(check != null)
			result = check;
		
		return result;
	}

	// 로그인 비밀번호 찾기 
	@Override
	public String doFindLoginPasswd(MemberDTO dto) {
		
		String result = null;

		String check = sqlSession.selectOne("member.findLoginPasswd", dto);
		
		if(check != null)
			result = check;
		
		return result;
	}

	// 로그인 비밀번호 변경
	@Override
	public void updateLoginPasswd(MemberDTO dto) {

		sqlSession.update("member.updateLoginPasswd", dto);
	}
}
