package com.petplant.web.controller.member;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.petplant.web.model.member.dao.MemberDAO;
import com.petplant.web.model.member.dto.MemberDTO;
import com.petplant.web.service.member.MemberService;


@Controller
@RequestMapping("/member/*")
public class MemberController {
		
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    
    @Inject
	MemberDAO memberDao;
    
	@Inject
    MemberService memberService;
	
	/** 로그인 페이지 **/
	@RequestMapping("login")
	public String login()
	{
		return "member/login";
	}
	
	@RequestMapping(value="login.do", method= RequestMethod.GET)
    public String loginDo() {
		
        return "redirect:/member/login";
    }
	
	/** 로그인 기능 **/
    @RequestMapping(value="login.do", method = RequestMethod.POST)
    public ModelAndView login_check(@ModelAttribute MemberDTO dto,
            HttpSession session, ModelAndView mav) {
    	
    	logger.info("dto : " + dto);
    	
    	MemberDTO result = memberService.loginCheck(dto, session);
        
        if(result != null) { 
            mav.setViewName("customer/main");
            
            return mav;
        } else { 
            mav.setViewName("member/login");
            mav.addObject("message", "error");
            
            return mav;
        }
    }
    
    /** 로그아웃 **/
    @RequestMapping("logout.do")
    public String logout(HttpSession session, ModelAndView mav) {
        
        memberService.logout(session);
        
        mav.addObject("message", "logout");
    
        return "redirect:/member/login";
    }
    
	/** 회원가입시 아이디 중복 확인 **/
	@RequestMapping("/idCheck.do")
	public @ResponseBody int idCheck(@RequestParam String login_id) {
		
		logger.info("login_id = " + login_id); 
		
		int result = memberService.idCheck(login_id);
		
		logger.info("result : " + result);
		
		return result;
	}
	
	/** 회원가입 페이지 **/
	@RequestMapping("join.do")
	public String write() {
		
		return "member/join";
	}
	
	/** 회원가입 **/
	@RequestMapping("insert.do")
	public String insert(@ModelAttribute MemberDTO dto) {
		
		memberDao.insert(dto);
		
		return "redirect:/member/login.do";
	}
	
	/** 로그인 아이디 찾기 **/
	@RequestMapping("/findLoginId")
	public String findLoginId() {
		
		return "member/findLoginId";
	}

	@RequestMapping("/findLoginId.do")
	public ModelAndView doFindLoginId(@ModelAttribute MemberDTO dto, ModelAndView mav) {
		
		String result = memberDao.doFindLoginId(dto); 

        logger.info("member login_id result : " + result);
        
		if(result != null) {
        	Map<String,Object> map = new HashMap<String, Object>();
        	map.put("login_name", dto.getLogin_name());
        	map.put("login_id", result); 
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
		
		if(result != null) { 
			mav.addObject("login_id", dto.getLogin_id());
			mav.setViewName("member/result_pw");   
        } else { 
        	mav.setViewName("member/findLoginPasswd");
        	mav.addObject("message", "error");
        }
		return mav;
	}
	
	/** 로그인 비밀번호 변경 **/
	@RequestMapping("updateLoginPasswd.do")
	public ModelAndView updateLoginPasswd(@ModelAttribute MemberDTO dto, ModelAndView mav) {
		
		logger.info("pdatepw : " + dto);

		memberDao.updateLoginPasswd(dto);
		mav.setViewName("member/login");
		
		return mav;
	}
}
