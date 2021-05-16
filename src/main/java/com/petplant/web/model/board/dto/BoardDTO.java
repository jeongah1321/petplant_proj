package com.petplant.web.model.board.dto;

import java.util.Arrays;
import java.util.Date;

//회원 게시판 관련 dto 클래스
public class BoardDTO {
	private int bno; // 게시물 번호
    private String title; // 제목
    private String content; // 내용
    private String writer; // 작성자 아이디
    private Date regdate; // 날짜
    private int viewcnt; // 조회수
    private String login_name; // 작성자 이름
    private int cnt; // 댓글 수
    private String board_show; // 게시글 삭제 상태 유무(y, n) 
    private String[] files; // 첨부파일 배열
    
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getViewcnt() {
		return viewcnt;
	}
	public void setViewcnt(int viewcnt) {
		this.viewcnt = viewcnt;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getBoard_show() {
		return board_show;
	}
	public void setBoard_show(String board_show) {
		this.board_show = board_show;
	}
	public String[] getFiles() {
		return files;
	}
	public void setFiles(String[] files) {
		this.files = files;
	}
	 
	@Override
	public String toString() {
		return "BoardDTO [bno=" + bno + ", title=" + title + ", content=" + content + ", writer=" + writer
				+ ", regdate=" + regdate + ", viewcnt=" + viewcnt + ", login_name=" + login_name + ", cnt=" + cnt
				+ ", board_show=" + board_show + ", files=" + Arrays.toString(files) + "]";
	}
}
