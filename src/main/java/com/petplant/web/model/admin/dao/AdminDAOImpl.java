package com.petplant.web.model.admin.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.petplant.web.model.admin.dto.AdminDTO;

@Repository
public class AdminDAOImpl implements AdminDAO {
 
    @Inject
    SqlSession sqlSession;
    
    @Override 
    public AdminDTO loginCheck(AdminDTO dto) {
    	
        return sqlSession.selectOne("admin.login_check", dto);
    }
}
