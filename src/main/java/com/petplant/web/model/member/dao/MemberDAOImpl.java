package com.petplant.web.model.member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.petplant.web.model.member.dto.MemberDTO;

@Repository // Repository가 있어야 모델 클래스로써 스프링에서 관리해줌 
public class MemberDAOImpl implements MemberDAO {

	@Inject // 의존객체 자동 주입 
	SqlSession sqlSession; 
	/* SqlSessionFactoryBuilder클래스 : 설정파일을 읽어서 SqlSessionFactory객체를 생성
	 * SqlSessionFactoryFactory 클래스 : SqlSession을 만드는 역할
		(Dao는 Factory을 멤버로 유지하면서 필요할 때 SqlSession을 open해서 사용하고 다쓰면 sqlSession을 close)
	 * SqlSession클래스 : sql문을 실제 호출해주는 역할(필요할때 open하고 close해줘야함)*/
	
	// 로그인
	@Override
	public String loginCheck(MemberDTO dto) {
		return sqlSession.selectOne("member.login_check", dto);
	}

	// 회원 가입 
	@Override
	public void insert(MemberDTO dto) {
		sqlSession.insert("member.insert", dto); // namespace가 member이고 id가 insert인 태그에 dto 내용 전달 
		// sqlSession.insert(namespace.query_id, mapper에 전달할 파라미터) // id에 대한 insert문을 실행하면서 객체의 값을 테이블에 추가
	}

	// 로그인 아이디 찾기
	@Override
	public String doFindLoginId(MemberDTO dto) {
		String result = null;

		String check = sqlSession.selectOne("member.findLoginId", dto);
					// sqlSession.selectOne(namespace.query_id, mapper에 전달할 파라미터) // id에 대한 select문을 실행하면서 사용되는 mapper에 전달할 파라미터도 전달하여 전달받는 파라미터 타입으로 한 개의 레코드 반환
					//cf. sqlSession.selectOne(query_id) // id에 대한 select문을 실행한 후 지정된 타입으로 한 개의 레코드 반환
		
		if(check != null)
			result = check; // check에는 login_id가 들어감
		
		return result; // controller에 전달
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
		// TODO Auto-generated method stub
		sqlSession.update("member.updateLoginPasswd", dto);
	}
}
