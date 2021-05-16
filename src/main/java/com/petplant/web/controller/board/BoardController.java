package com.petplant.web.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger; // 하단의 로깅툴 설명 참고  
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.petplant.web.model.board.dto.BoardDTO;
import com.petplant.web.service.board.BoardService;
import com.petplant.web.service.board.ReplyService;
import com.petplant.web.util.Pager;

@Controller
@RequestMapping("board/*")
public class BoardController {
    
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject 
	BoardService boardService;
	
	@Inject
	ReplyService replyService;
    
	/** 게시판 글쓰기 **/
	@RequestMapping("write.do")
	public String write(HttpSession session) {
		
		return "board/write";
	}
	
	@RequestMapping("insert.do")
	public String insert(@ModelAttribute BoardDTO dto, HttpSession session) throws Exception {
		
		if((String)session.getAttribute("login_id") != null) { 
			String writer = (String)session.getAttribute("login_id");
			
			logger.info("login_id : " + writer);
			
			dto.setWriter(writer); 
			
			boardService.create(dto);
		} else if((String)session.getAttribute("admin_login_id") != null) {
			String writer = (String)session.getAttribute("admin_login_id");
			
			logger.info("admin_login_id : " + writer);
			
			dto.setWriter(writer);
			boardService.create(dto);
		}
		return "redirect:/board/list.do"; 
	}
	
	
    /* 게시물 첨부파일 목록을 리턴 */
    @RequestMapping("getAttach/{bno}") 
    @ResponseBody
    public List<String> getAttach(@PathVariable("bno") int bno){
    	
        return boardService.getAttach(bno);
    }
    
    /** 게시글 삭제 **/
    @RequestMapping("delete.do")
    public String delete(int bno) throws Exception {
    	
        boardService.delete(bno); 
        
        return "redirect:/board/list.do";
    }  

    /** 게시글 수정 **/  
    @RequestMapping("update.do")
    public String update(@ModelAttribute BoardDTO dto) throws Exception {
    	
    	logger.info("dto : " + dto);

        if(dto != null)
            boardService.update(dto); 

        return "redirect:/board/list.do";
    }
    
	/** 게시판 목록 **/
	@RequestMapping("list.do")
	public ModelAndView list(
		    @RequestParam(defaultValue="1") int curPage, 
		    @RequestParam(defaultValue="all") String search_option,
		    @RequestParam(defaultValue="") String keyword)
		                        throws Exception{

        int count = boardService.countArticle(search_option, keyword);
        
        logger.info("count : " + count);
        
        Pager pager = new Pager(count, curPage);
        
        int start = pager.getPageBegin();
        int end = pager.getPageEnd();//
        
        logger.info("Board start : " + start + " / end : " + end);
        
		List<BoardDTO> list = boardService.listAll(search_option, keyword, start, end);
		
		logger.info("list : " + list);
		
		ModelAndView mav = new ModelAndView();
		
		Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("count", count);
        map.put("pager", pager);
        map.put("search_option", search_option);
        map.put("keyword",keyword); 
        mav.addObject("map", map);
        mav.setViewName("board/list");
        
        return mav;
	}
	
	/** 상세 페이지 : 게시글 상세내용 조회, 게시글 조회수 증가 처리 **/
	@RequestMapping(value="view.do", method=RequestMethod.GET)
	public ModelAndView view(@RequestParam int bno, 
			@RequestParam int curPage,
			@RequestParam String search_option,
			@RequestParam String keyword, 
			HttpSession session) throws Exception {
 
		boardService.increaseViewcnt(bno, session);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("count", replyService.count(bno));
		mav.addObject("dto", boardService.read(bno));
		mav.addObject("curPage", curPage);
        mav.addObject("search_option", search_option);
        mav.addObject("keyword", keyword);
        mav.setViewName("board/view");
        
        logger.info("mav : " + mav);
        
        return mav;
    }
}