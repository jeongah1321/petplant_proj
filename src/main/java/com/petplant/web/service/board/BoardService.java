package com.petplant.web.service.board;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.petplant.web.model.board.dto.BoardDTO;


public interface BoardService {
	public List<String> getAttach(int bno); // 첨부파일 목록
	public void deleteFile(String fullName); // 첨부파일 삭제
	
	public void create(BoardDTO dto) throws Exception; // 글쓰기
	public BoardDTO read(int bno) throws Exception; // 글 읽기
	public void update(BoardDTO dto) throws Exception; // 글 수정
	public void delete(int bno) throws Exception; // 글 삭제
	
	//목록 (페이지 나누기, 검색 기능 포함)
	//매개변수는 시작 레코드번호, 끝번호, 옵션과 키워드가 들어간다)
	public List<BoardDTO> listAll(String search_option, String keyword,int start, int end) throws Exception;
    
	//조회수 증가 처리
	public void increaseViewcnt(int bno, HttpSession session) throws Exception; 
    
	//레코드 개수 계산
	public int countArticle(String search_option, String keyword) throws Exception;
}