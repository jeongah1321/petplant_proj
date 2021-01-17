package com.petplant.web.service.board;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.petplant.web.model.board.dto.ReplyDTO;


public interface ReplyService {
	public List<ReplyDTO> list(int bno, int start, int end, HttpSession session); // 댓글 목록
	public int count(int bno);           // 댓글 개수
	public void create(ReplyDTO dto);    // 댓글 작성
	public void update(ReplyDTO dto);	 // 댓글 수정
	public void delete(int rno);		 // 댓글 삭제
	public ReplyDTO detail(int rno);	 // 댓글 상세보기
}
