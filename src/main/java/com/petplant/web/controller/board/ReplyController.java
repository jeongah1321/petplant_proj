package com.petplant.web.controller.board;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.petplant.web.controller.member.MemberController;
import com.petplant.web.model.board.dto.ReplyDTO;
import com.petplant.web.service.board.ReplyService;
import com.petplant.web.util.Pager;

@Controller
@RequestMapping("reply/*")
public class ReplyController {
	
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Inject
	ReplyService replyService;
	
	/** 댓글 입력 **/
	@ResponseBody
	@RequestMapping(value="insertRest.do", method=RequestMethod.POST)
	public ResponseEntity<String> insertRest(@RequestBody ReplyDTO dto, HttpSession session){
		
		ResponseEntity<String> entity = null;
		
		try {
			if((String)session.getAttribute("login_id") != null) {
				String login_id = (String)session.getAttribute("login_id");
				
				dto.setReplyer(login_id);
				replyService.create(dto);
			} else if((String)session.getAttribute("admin_login_id") != null) {
				String login_id = (String)session.getAttribute("admin_login_id");
				
				dto.setReplyer(login_id);
				replyService.create(dto);
			}
			
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
    /** 댓글 목록 **/
    @RequestMapping(value="/list/{bno}/{curPage}", method=RequestMethod.POST)
	@ResponseBody 
    public ModelAndView replyList(@PathVariable("bno") int bno, @PathVariable int curPage, 
    		ModelAndView mav, HttpSession session) {
        
        int count = replyService.count(bno); 
        
        Pager pager = new Pager(count, curPage); 
        
        int start = pager.getPageBegin();
        int end = pager.getPageEnd();
        
        logger.info("ReplyController start : " + start +" / end : "+ end +" / curPage : "+ curPage);
     
        List<ReplyDTO> list = replyService.list(bno, start, end, session);
        mav.addObject("list", list);
        mav.addObject("pager", pager);
        mav.setViewName("board/replyList"); 
        
        return mav;
    }
    
    /** 댓글 상세 보기 **/
    @RequestMapping(value="/detail/{rno}", method=RequestMethod.POST)
	@ResponseBody
    public ModelAndView replyDetail(@PathVariable("rno") int rno, ModelAndView mav) {
        
    	ReplyDTO dto = replyService.detail(rno);
        mav.setViewName("board/replyDetail");
        mav.addObject("dto", dto);
        
        return mav;
    }
    
    /** 댓글 수정 처리 **/
    @RequestMapping(value="/update/{rno}", method=RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<String> replyUpdate(@PathVariable("rno") int rno, @RequestBody ReplyDTO dto) {
	    
    	ResponseEntity<String> entity = null;
	    
	    try {
	        dto.setRno(rno);
	        replyService.update(dto);
	        
	        entity = new ResponseEntity<String>("success", HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        
	        entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	    return entity;
	}

	/** 댓글 삭제처리 **/
	@RequestMapping(value="/delete/{rno}" ,method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> replyDelete(@PathVariable("rno") int rno) {
	    ResponseEntity<String> entity = null;
	    
	    try {
	        replyService.delete(rno);
	       
	        entity = new ResponseEntity<String>("success", HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	       
	        entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	    return entity;
	}
}
