package com.petplant.web.model.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.petplant.web.model.board.dto.BoardDTO;


@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	SqlSession sqlSession;
	
	// 게시물 첨부파일 목록  
	@Override
	public List<String> getAttach(int bno) {
		
		return sqlSession.selectList("board.getAttach", bno);
	}
	
	// 첨부파일 추가
	@Override
	public void addAttach(String fullName) {
		
		sqlSession.update("board.addAttach", fullName); 
	}

	// 첨부파일 첨부파일 수정
	@Override
	public void updateAttach(String fullName, int bno) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("fullName", fullName);
		map.put("bno", bno);
		
		sqlSession.insert("board.updateAttach", map);
	}
	
	// 게시물 삭제시 첨부파일 삭제
	@Override
	public void deleteFile(String fullName) {
		
		sqlSession.delete("board.deleteAttach",fullName);
	}

	// 게시물 등록 
	@Override
	public void create(BoardDTO dto) throws Exception {
		
		sqlSession.insert("board.insert", dto);
	}
	
	// 게시물 내용
	@Override
	public BoardDTO read(int bno) throws Exception {
		
		return sqlSession.selectOne("board.read", bno);
	}
	
	// 게시물 수정
	@Override
	public void update(BoardDTO dto) throws Exception {
		
		sqlSession.update("board.update", dto); 
	}

	// 게시물 삭제
	@Override
	public void delete(int bno) throws Exception {
		
		sqlSession.delete("board.delete", bno);
	}

	// 게시물 목록 리턴
	@Override
	public List<BoardDTO> listAll(
			String search_option, String keyword, int start, int end) throws Exception {     
		
		Map<String,Object> map = new HashMap<String, Object>();
        map.put("search_option", search_option);
        map.put("keyword", "%" + keyword + "%");
        map.put("start", start);
        map.put("end", end);

        return sqlSession.selectList("board.listAll", map); 
	}

	// 조회수 증가 처리
	@Override
	public void increateViewcnt(int bno) throws Exception {
		
		sqlSession.update("board.increaseViewcnt", bno);
	}

	//레코드 개수 계산
	@Override
	public int countArticle(String search_option, String keyword) throws Exception {
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("search_option", search_option);
		map.put("keyword", "%"+keyword+"%");
		
		return sqlSession.selectOne("board.countArticle", map);
	}
}