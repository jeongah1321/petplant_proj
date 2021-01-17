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
@RequestMapping("board/*") // 공통적인 url pattern
public class BoardController {
    
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject 
	BoardService boardService; // 서비스를 사용하기 위해 의존성을 주입
	
	@Inject // ReplyService 주입(ReplyService의 댓글 개수를 구하는 메소드 호출하기 위해)
	ReplyService replyService;
    
	/** 게시판 글쓰기 **/
	@RequestMapping("write.do")
	public String write(HttpSession session) {
		return "board/write";
	}
	
	@RequestMapping("insert.do")
	public String insert(@ModelAttribute BoardDTO dto, HttpSession session) throws Exception {
		
		if((String)session.getAttribute("login_id") != null) { // 로그인한 사용자의 아이디
			String writer = (String)session.getAttribute("login_id");
			System.out.println("login_id"+ writer);
			
			dto.setWriter(writer); // 레코드가 저장됨
			
			boardService.create(dto);
		} else if((String)session.getAttribute("admin_login_id") != null) { // 로그인한 관리자의 아이디
			String writer = (String)session.getAttribute("admin_login_id");
			System.out.println("admin_login_id"+ writer);
			
			dto.setWriter(writer); // 레코드가 저장됨
		
			boardService.create(dto);
		}
		return "redirect:/board/list.do"; // 목록 갱신
	}
	
	
    /* 게시물 첨부파일 목록을 리턴 */
    // ArrayList를 json 배열로 변환하여 리턴
    @RequestMapping("getAttach/{bno}") // view에서 넘긴 bno를 경로값 (url에 포함된 변수)로 받아서 맵핑
    @ResponseBody // view가 아닌 데이터 자체를 리턴 
    public List<String> getAttach(@PathVariable("bno") int bno){
        return boardService.getAttach(bno);
    }
    
    /** 게시글 삭제 **/
    @RequestMapping("delete.do")
    public String delete(int bno) throws Exception {
        boardService.delete(bno); // 글 삭제
        return "redirect:/board/list.do"; // 목록으로 이동
    }  

    /** 게시글 수정 **/  
    @RequestMapping("update.do")
    public String update(@ModelAttribute BoardDTO dto) throws Exception {
        System.out.println("dto:"+dto);
        if(dto != null) {
            boardService.update(dto); // 글 수정
        }
        // 수정 완료 후 목록으로 이동
        return "redirect:/board/list.do";
    }
    
	/** 게시판 목록 **/
	@RequestMapping("list.do")
	public ModelAndView list(//RequestParam으로 옵션, 키워드, 페이지의 기본값을 각각 설정해준다.
		    @RequestParam(defaultValue="1") int curPage, // defaultValue로 null값과 400에러 방지
		    @RequestParam(defaultValue="all") String search_option,
		    @RequestParam(defaultValue="") String keyword)
		    //defaultValue를 설정하지 않으면 null point 에러가 발생할수 있기 때문에 기본값을 설정해주어야 한다.
		                        throws Exception{

        int count = boardService.countArticle(search_option, keyword); // 게시글 개수 계산(검색옵션과 키워드를 고려)
        
        System.out.println("count" + count);
        
        // 페이지 관련 설정, 시작번호와 끝번호를 구해서 각각 변수에 저장함
        Pager pager = new Pager(count, curPage); // 게시글 개수, 현재 페이지 번호
        int start = pager.getPageBegin();
        int end = pager.getPageEnd();//
        
        System.out.println("Board start : "+start + " / end : "+end);
        
        // 게시물 목록을 출력하기 위해 <BoardDTO>타입에 list변수에 게시물 목록관련 값들을 저장함.
        // 넣어야될 값들이 여러개 있으므로 haspmap.put 메소드를 사용해서 값들을 넣어서 list에 저장  
		List<BoardDTO> list = boardService.listAll(search_option, keyword, start, end); // 게시물 목록
		
		System.out.println("list:"+list);
		
		ModelAndView mav = new ModelAndView();
		// 자료를 보낼 페이지를 지정해야하고, 자료를 지정해야 하기 때문에 ModelAndView 객체를 생성한다.		
		Map<String,Object> map = new HashMap<String, Object>(); // 데이터가 많기 때문에 hashmap<>에 저장한다.
        map.put("list", list); //map에 자료 저장
        map.put("count", count); // 게시글 수
        map.put("pager", pager); //페이지 네비게이션을 위한 변수
        map.put("search_option", search_option);
        map.put("keyword",keyword); 
        mav.addObject("map", map); // ModelAndView에 map을 저장
        mav.setViewName("board/list"); //포워딩할 뷰의 이름
        return mav;
	}
	
	/** 상세 페이지 : 게시글 상세내용 조회, 게시글 조회수 증가 처리 **/
	@RequestMapping(value="view.do", method=RequestMethod.GET)
	//list.jsp 페이지에서 넘긴 게시물번호, 페이지번호, 검색옵션, 키워드를 받는다.
	public ModelAndView view(@RequestParam int bno, 
			@RequestParam int curPage,
			@RequestParam String search_option,
			@RequestParam String keyword, 
			HttpSession session) throws Exception {
 
		boardService.increaseViewcnt(bno, session); //조회수 증가 처리
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("count", replyService.count(bno)); // 댓글 개수
		mav.addObject("dto", boardService.read(bno)); // 게시물 내용 
		mav.addObject("curPage", curPage); // 현재 페이지 
        mav.addObject("search_option", search_option); // 검색 옵션
        mav.addObject("keyword", keyword); // 키워드
        mav.setViewName("board/view"); // 포워딩할 뷰의 이름
        logger.info("mav:", mav);
        return mav;
    }
}