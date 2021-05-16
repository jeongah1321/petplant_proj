package com.petplant.web.util;

public class Pager {
    public static final int PAGE_SCALE = 10; // 페이지당 출력할 게시물 수
    public static final int BLOCK_SCALE = 5; // 화면당 페이지 수(화면에 5개씩 끊어 보여주겠다)
    
    private int curPage; // 현재 페이지
    private int prevPage; // 이전 페이지
    private int nextPage; // 다음 페이지
    private int totPage; // 전체 페이지 개수
    private int totBlock; // 전체 페이지 블록 개수
    private int curBlock; // 현재 블록
    private int prevBlock; // 이전 블록
    private int nextBlock; // 다음 블록
    private int pageBegin; // #{start} 변수에 전달될 값
    private int pageEnd; // #{end} 변수에 전달될 값
    private int blockBegin; // 블록의 시작페이지 번호
    private int blockEnd; // 블록의 끝페이지 번호
    
    public Pager(int count, int curPage) {
        curBlock = 1; // 현재 블록 번호
        this.curPage = curPage; // 현재 페이지 번호
        setTotPage(count); // 전체 페이지 개수 계산
        setPageRange(); // #{start}, #{end} 값 계산하는 메소드
        setTotBlock(); // 전체 블록 개수 계산
        setBlockRange(); // 블록의 시작, 끝 번호 계산
    }
    
    public void setBlockRange() {
        // *현재 페이지가 몇번째 페이지 블록에 속하는지 계산
        curBlock = (int)Math.ceil((curPage-1) / BLOCK_SCALE)+1;
        // *현재 페이지 블록의 시작, 끝 번호 계산
        blockBegin = (curBlock-1)*BLOCK_SCALE+1;
        // 페이지 블록의 끝번호
        blockEnd = blockBegin+BLOCK_SCALE-1;
        // *마지막 블록이 범위를 초과하지 않도록 계산
        if(blockEnd > totPage) blockEnd = totPage;
        // *이전을 눌렀을 때 이동할 페이지 번호
        prevPage=(curBlock==1) ? 1 : (curBlock-1)*BLOCK_SCALE;
        // *다음을 눌렀을 때 이동할 페이지 번호
        nextPage=(curBlock>totBlock) ? (curBlock*BLOCK_SCALE) : (curBlock*BLOCK_SCALE)+1;
        // 마지막 페이지가 범위를 초과하지 않도록 처리
        if(nextPage >= totPage) nextPage = totPage;
    }
    
    public void setPageRange() {
	// 시작번호 = (현재페이지-1)x페이지당 게시물수
	// 끝번호 = 페이지당 게시물수     
        pageBegin = (curPage-1) * PAGE_SCALE;
        pageEnd = PAGE_SCALE;
    }
    
    public int getCurPage() {
        return curPage;
    }
    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }
    public int getPrevPage() {
        return prevPage;
    }
    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }
    public int getNextPage() {
        return nextPage;
    }
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
    public int getTotPage() {
        return totPage;
    }
    
    //전체 페이지 개수 계산
    public void setTotPage(int count) {
        // Math.ceil(실수) 올림 int를 실수로 나누면 그 결과는 실수        
        totPage = (int)Math.ceil(count*1.0 / PAGE_SCALE);
    }
    public int getTotBlock() {
        return totBlock;
    }
    // 페이지 블록의 개수 계산(총 100페이지라면 10개의 블록)
    public void setTotBlock() {
        // 전체 페이지 갯수 / 10 ; 91 / 10 => 9.1 => 10개
        totBlock = (int)Math.ceil(totPage*1.0 / BLOCK_SCALE);
    }
    public int getCurBlock() {
        return curBlock;
    }
    public void setCurBlock(int curBlock) {
        this.curBlock = curBlock;
    }
    public int getPrevBlock() {
        return prevBlock;
    }
    public void setPrevBlock(int prevBlock) {
        this.prevBlock = prevBlock;
    }
    public int getNextBlock() {
        return nextBlock;
    }
    public void setNextBlock(int nextBlock) {
        this.nextBlock = nextBlock;
    }
    public int getPageBegin() {
        return pageBegin;
    }
    public void setPageBegin(int pageBegin) {
        this.pageBegin = pageBegin;
    }
    public int getPageEnd() {
        return pageEnd;
    }
    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }
    public int getBlockBegin() {
        return blockBegin;
    }
    public void setBlockBegin(int blockBegin) {
        this.blockBegin = blockBegin;
    }
    public int getBlockEnd() {
        return blockEnd;
    }
    public void setBlockEnd(int blockEnd) {
        this.blockEnd = blockEnd;
    }    
}