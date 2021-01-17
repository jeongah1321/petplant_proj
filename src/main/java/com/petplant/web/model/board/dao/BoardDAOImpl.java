package com.petplant.web.model.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.petplant.web.model.board.dto.BoardDTO;


@Repository // dao bean
public class BoardDAOImpl implements BoardDAO {
	
	@Inject //의존관계 주입(Dependency Injection, DI)
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
		Map<String,Object> map = new HashMap<String, Object>(); // 값을 여러개 담을때는 haspmap를 사용한다. 
		map.put("fullName", fullName); // 첨부파일 이름
		map.put("bno", bno); // 게시물 번호
		sqlSession.insert("board.updateAttach", map); // updateAttach mapper을 호출
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
	public List<BoardDTO> listAll( // 매개변수는 시작 레코드번호, 끝번호, 옵션과 키워드가 들어간다
			String search_option, String keyword, int start, int end) throws Exception {     
		Map<String,Object> map = new HashMap<String, Object>();
        map.put("search_option", search_option);
        map.put("keyword", "%" + keyword + "%");
        map.put("start", start); // 맵에 자료 저장
        map.put("end", end);
        // mapper에는 2개 이상의 값을 전달할 수 없음(dto 또는 map 사용)        
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