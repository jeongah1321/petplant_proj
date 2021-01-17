package com.petplant.web.model.admin;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.petplant.web.model.member.dto.MemberDTO;

@Repository //DAO를 구현한 클래스이므로 Repository 어노테이션을 사용
public class AdminDAOImpl implements AdminDAO {
 
    @Inject //mybatis mapper을 호출해야 하므로 sqlsession에 inject로 의존성을 주입하고 사용한다.
    SqlSession sqlSession;
    
    @Override 
    //DAO 인터페이스에서 만든 메소드를 구현
    public String loginCheck(MemberDTO dto) {
        return sqlSession.selectOne("admin.login_check", dto);
        //sqlsession에 mapper에 맵핑된 namespace와 id로 sql문을 저장한다.
    }
}
