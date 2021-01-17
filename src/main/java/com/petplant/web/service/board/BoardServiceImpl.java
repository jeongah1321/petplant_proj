package com.petplant.web.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petplant.web.model.board.dao.BoardDAO;
import com.petplant.web.model.board.dto.BoardDTO;


@Service // service bean
public class BoardServiceImpl implements BoardService {
	
	@Inject //dao를 호출하기 때문에 의존성을 주입
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
    	boardDao.create(dto); // board 테이블에 레코드 추가
		// attach 테이블에 레코드 추가
		String[] files = dto.getFiles(); // 첨부파일 이름 배열
		if(files == null) return;  // 첨부파일이 없으면 skip
		for(String name : files) {
		    boardDao.addAttach(name);  // attach 테이블에 insert
		}
	}

	// 게시물 내용 
	@Override
	public BoardDTO read(int bno) throws Exception {
		return boardDao.read(bno);
	}

	// 게시물 수정
	@Transactional //트랜잭션 처리 method
	// 코드 수정과 첨부파일을 첨부하는 기능이 동시에 같이 들어가야하기 때문에 일관성을 유지하기 위해서 트랜잭션 처리를 실시한다.
	// 만약 두개중에 하나라도 되지않으면 작업을 취소시키기고, 롤백을 한다.
	@Override
	public void update(BoardDTO dto) throws Exception {
		//board 테이블 수정
		boardDao.update(dto); 
		//attach 테이블 수정
		String[] files = dto.getFiles();
		if(files == null) return;
		for(String name : files) {
		    System.out.println("첨부파일 이름:"+name);
		    boardDao.updateAttach(name, dto.getBno()); 
		}
	}

	// 게시물 삭제
	@Override
	public void delete(int bno) throws Exception {
		boardDao.delete(bno);
		//reply 레코드 삭제, attach 레코드 삭제, 첨부파일 삭제, board 레코드 삭제
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
			// 최근에 조회수를 올린 시간이 null이 아니면
			update_time = (Long) session.getAttribute("update_time_"+bno);
		}
		long current_time=System.currentTimeMillis();
		// 일정 시간이 경과한 후 조회수 증가 처리
		if(current_time - update_time > 5*1000) {
			// 그러니까 조회수가 1증가했을때로부터 5000초 후에 다시 클릭을 해야 조회수가 다시 1증가한다는 말..
		    // 조회수 증가 처리
		    boardDao.increateViewcnt(bno);
		    // 조회수를 올린 시간 저장
		    session.setAttribute("update_time_"+bno, current_time);
        }
	}

	//레코드 개수 계산
	@Override
	public int countArticle(String search_option, String keyword) throws Exception {
		return boardDao.countArticle(search_option, keyword); 
		//boardDao를 호출해서 리턴
	}
}