package com.petplant.web.model.product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.petplant.web.model.product.dto.LikeDTO;

@Repository
public class LikeDAOImpl implements LikeDAO {

	@Inject
    SqlSession sqlSession; //root-context에 빈으로 등록한 mybatis객체
	
	// 개인별 목록
	@Override
	public List<LikeDTO> listAll(String login_id) {
		return sqlSession.selectList("like.listAll", login_id);
	}
	
	// 추가
	@Override
	public void insert(LikeDTO dto) {
		sqlSession.insert("like.insert", dto);
	}
	
	// 수정(동일 아이템 추가 확인)
	@Override
	public void update(LikeDTO dto) {
		sqlSession.update("like.update", dto);
	}
	
	// 장바구니 개별 상품 삭제 
	@Override
	public void delete(int like_id) {
		sqlSession.delete("like.delete", like_id);
	}
	
	// 장바구니 전체 상품 삭제 
	@Override
	public void deleteAll(String login_id) {
		 sqlSession.delete("like.deleteAll", login_id);
	}

	// 동일한 아이템 레코드 확인
	@Override
	public int countLike(String login_id, int product_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("login_id", login_id);
		map.put("product_id", product_id);
		return sqlSession.selectOne("like.countLike", map);
	}
}
