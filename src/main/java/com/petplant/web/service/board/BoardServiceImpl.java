package com.petplant.web.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petplant.web.controller.member.MemberController;
import com.petplant.web.model.board.dao.BoardDAO;
import com.petplant.web.model.board.dto.BoardDTO;


@Service 
public class BoardServiceImpl implements BoardService {
	
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	BoardDAO boardDao; 

	// 게시물 첨부파일 목록 
	@Override
	public List<String> getAttach(int bno) {
		
		return boardDao.getAttach(bno);
	}
	
	// 게시물 삭제시 첨부파일 삭제
	@Override
	public void deleteFile(String fullName) {
		
		boardDao.deleteFile(fullName);
	}

	// 1.글쓰기 - 게시물 번호 생성
	// 2.첨부파일 등록 - 게시물 번호 사용
	@Transactional
	@Override
	public void create(BoardDTO dto) throws Exception {
		
    	boardDao.create(dto);
    	
		String[] files = dto.getFiles();
		
		if(files == null) return; 
		
		for(String name : files) {
		    boardDao.addAttach(name);
		}
	}

	// 게시물 내용 
	@Override
	public BoardDTO read(int bno) throws Exception {
		
		return boardDao.read(bno);
	}

	// 게시물 수정
	@Transactional
	@Override
	public void update(BoardDTO dto) throws Exception {
		
		boardDao.update(dto); 
		
		String[] files = dto.getFiles();
		
		if(files == null) return;
		
		for(String name : files) {
			logger.info("첨부파일 이름 : " + name);
			
		    boardDao.updateAttach(name, dto.getBno()); 
		}
	}

	// 게시물 삭제
	@Override
	public void delete(int bno) throws Exception {
		
		boardDao.delete(bno);
	}

	// 게시물 목록
	@Override
	public List<BoardDTO> listAll(String search_option, String keyword, int start, int end) throws Exception {
		
		return boardDao.listAll(search_option, keyword, start, end); 
	}

	// 조회수 증가 처리
	// 조회수 처리를 할때 일정 시간이 지난후 다시 클릭할때만 조회수가 증가하도록 설정.
	@Override
	public void increaseViewcnt(int bno, HttpSession session) throws Exception {
		
		long update_time = 0; // null을 방지하기 위해 초기값을 null로 설정
		
		if(session.getAttribute("update_time_"+bno) != null) {
			update_time = (Long) session.getAttribute("update_time_"+bno);
		}
		
		long current_time=System.currentTimeMillis();
		
		if(current_time - update_time > 5*1000) {
		    boardDao.increateViewcnt(bno);
		    
		    session.setAttribute("update_time_"+bno, current_time);
        }
	}

	//레코드 개수 계산
	@Override
	public int countArticle(String search_option, String keyword) throws Exception {
		
		return boardDao.countArticle(search_option, keyword); 
	}
}