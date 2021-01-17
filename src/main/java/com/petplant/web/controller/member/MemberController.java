package com.petplant.web.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.petplant.web.model.board.dto.BoardDTO;
import com.petplant.web.model.member.dao.MemberDAO;
import com.petplant.web.model.member.dto.MemberDTO;
import com.petplant.web.service.member.MemberService;


@Controller
@RequestMapping("/member/*") // 공통적인 url mapping
public class MemberController {
		
	//로깅툴 slf4j.Logger
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    
    // 인터페이스 객체를 생성해서 구현객체 사용
    @Inject
	MemberDAO memberDao;
    
    //인터페이스 객체를 생성해서 구현객체 사용
	@Inject
    MemberService memberService;
	
	/** 로그인 페이지 **/
	@RequestMapping("login.do")
    public String login() {
        return "member/login";
    }
	
	/** 로그인 기능 **/
	// @RequestParam은 폼의 개별값, 
    // @ModelAtrribute는 폼의 전체 데이터(dto) 받아올 때 사용
    @RequestMapping("login_check.do")
    public ModelAndView login_check(@ModelAttribute MemberDTO dto,// 폼데이터 세트!
            HttpSession session, ModelAndView mav) {
    	/* @ModelAttribute는 DTO 객체를 정의 해놓은 것이 있다면 DTO 객체를 생성하고 
    	 * Http Request메시지의 body 에 담긴 데이터들을 DTO객체의 setter와 getter를 이용하여 초기화 시키는 역할을 수행합니다. 
    	 * @RequestBody 와 가장 큰 차이점은 Json이나 XML 데이터를 자바 객체로 변환하는 능력이 없다는 점입니다. 
    	 * 그래서 form 을 통해 전달받은 파라미터들을 객체로 바인딩 시키는 경우에만 사용할 수 있습니다.
    	 */
    	
        // 로그인 성공 => 이름이 넘어옴, 실패 => null이 넘어옴 
        String login_name = memberService.loginCheck(dto, session);
        
        // 위에 생성한 로깅툴 실행
        logger.info("member_name : " + login_name);
        
        // 세션에 정보를 저장하거나, 세션을 초기화하는 처리는 서비스에서 진행한다.
        // DAO에서는 데이터베이스와 관련된 과정만 받아서 처리한다.
        // DAO까지 session객체를 매개값으로 보낼 필요는 없다.
        // 서비스단에서 name 정보를 받아와 받아온 name정보가 있으면(로그인 성공이면) home으로 포워딩한다.
        
        if(login_name != null) { // 로그인 성공하면 시작 페이지로 이동
        	session.setAttribute("login_id", dto.getLogin_id());
        	session.setAttribute("login_name", login_name);
        	
            mav.setViewName("customer/main");
        } else { // 로그인에 실패하면 login 페이지로 다시 되돌아감
            mav.setViewName("member/login");
            mav.addObject("message", "error");
        }
        
        return mav;
    }
    
    /** 로그아웃 **/
    @RequestMapping("logout.do")
    public ModelAndView logout(HttpSession session, ModelAndView mav) {
        
        memberService.logout(session); //세션 초기화 작업 맡기기
        mav.setViewName("member/login");
        mav.addObject("message", "logout");
    
        return mav; //로그인 페이지로 이동
    }
	
	/** 회원가입 페이지 **/
	@RequestMapping("join.do")
	public String write() {
		return "member/join";
	}
	
	/** 회원가입 **/
	@RequestMapping("insert.do")
	public String insert(@ModelAttribute MemberDTO dto) { // @ModelAttribute로 MemberDTO의 내용 한번에 받기
		memberDao.insert(dto); // 여기서 insert 됨
		return "redirect:/member/login.do";
	}
	
	/** 로그인 아이디 찾기 **/
	@RequestMapping("/findLoginId")
	public String findLoginId() {
		return "member/findLoginId";
	}

	@RequestMapping("/findLoginId.do")
	public ModelAndView doFindLoginId(@ModelAttribute MemberDTO dto, ModelAndView mav) {
		String result = memberDao.doFindLoginId(dto); // result에는 login_id가 들어감
		// 위에 생성한 로깅툴 실행
        logger.info("member login_id result : " + result);
//        logger.info("member login_id dto : " + dto);
		if(result != null) {
        	Map<String,Object> map = new HashMap<String, Object>();
        	map.put("login_name", dto.getLogin_name()); //map에 자료 저장
        	map.put("login_id", result); //map에 자료 저장
        	mav.addObject("map", map);
            mav.setViewName("member/result_id");  
		} else {
			mav.setViewName("member/findLoginId");
            mav.addObject("message", "error");
        }
        return mav;
	}

	/** 로그인 비밀번호 찾기 **/
	@RequestMapping("/findLoginPasswd")
	public String findLoginPasswd() {
		return "member/findLoginPasswd";
	}

	@RequestMapping("/findLoginPasswd.do")
	public ModelAndView doFindLoginPasswd(@ModelAttribute MemberDTO dto, ModelAndView mav) {
		
		String result = memberDao.doFindLoginPasswd(dto);
		logger.info("member login_passwd : " + result);
		
		if(result != null) { // 성공하면 결과 페이지로 이동
//			session.setAttribute("login_id", dto.getLogin_id());
			mav.addObject("login_id", dto.getLogin_id());
			mav.setViewName("member/result_pw");   
        } else { // 실패하면 다시 되돌아감
        	mav.setViewName("member/findLoginPasswd");
        	mav.addObject("message", "error");
        }
		return mav;
	}
	
	/** 로그인 비밀번호 변경 **/
	@RequestMapping("updateLoginPasswd.do")
	public ModelAndView updateLoginPasswd(@ModelAttribute MemberDTO dto, ModelAndView mav) {
		System.out.println("updatepw"+dto);
		memberDao.updateLoginPasswd(dto);
		mav.setViewName("member/login");
		
		return mav;
	}
}
