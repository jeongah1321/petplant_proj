package com.petplant.web.model.board.dao;

import java.util.List;

import com.petplant.web.model.board.dto.ReplyDTO;

public interface ReplyDAO {
	public List<ReplyDTO> list(int bno, int start, int end); // 댓글 목록
	public int count(int bno);           // 댓글 개수
	public void create(ReplyDTO dto);    // 댓글 작성
	public void update(ReplyDTO dto);	 // 댓글 수정
	public void delete(int rno);		 // 댓글 삭제
	public ReplyDTO detail(int rno);	 // 댓글 상세보기
}