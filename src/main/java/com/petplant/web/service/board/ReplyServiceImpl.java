package com.petplant.web.service.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.petplant.web.model.board.dao.ReplyDAO;
import com.petplant.web.model.board.dto.ReplyDTO;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	ReplyDAO replyDao;
	
	//댓글 목록
	@Override
	public List<ReplyDTO> list(int bno, int start, int end, HttpSession session)  {
		List<ReplyDTO> items = replyDao.list(bno, start, end);
		// 세션에서 현재 사용자 id값 저장
		String login_id = (String)session.getAttribute("login_id");
		for(ReplyDTO dto : items) {
			// 댓글 목록 중에 비밀 댓글이 있을 경우
			if(dto.getSecretReply().equals("y")) {
				if(login_id == null) { // 비로그인 상태면 비밀 댓글로 처리
					dto.setReplytext("비밀 댓글입니다.");
				} else { // 로그인 상태일 경우 
					String writer = dto.getWriter(); // 게시물 작성자 저장
					String replyer = dto.getReplyer(); // 댓글 작성자 저장
					// 로그인한 사용자가 게시물의 작성자X 댓글 작성자도 X 비밀댓글로 처리
					if(!login_id.equals(writer) && !login_id.equals(replyer)) {
						dto.setReplytext("비밀 댓글입니다.");
					}
				}
			}
		}
		return items;
	}
	
	// 댓글 개수 
	@Override
	public int count(int bno) {
		return replyDao.count(bno);
	}
	
	// 댓글 쓰기    
	@Override
	public void create(ReplyDTO dto) {
		replyDao.create(dto);
	}

	// 댓글 수정
	@Override
	public void update(ReplyDTO dto) {
		replyDao.update(dto);
	}

	// 댓글 삭제
	@Override
	public void delete(int rno) {
		replyDao.delete(rno);
	}

	// 댓글 상세보기 
	@Override
	public ReplyDTO detail(int rno) {
		return replyDao.detail(rno);
	}
}