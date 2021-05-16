package com.petplant.web.controller.admin;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.petplant.web.model.admin.dto.AdminDTO;
import com.petplant.web.service.admin.AdminService;

@Controller
@RequestMapping("/admin/*") 
public class AdminController {
	
	@Inject
	AdminService adminService; 
    
	@RequestMapping("login")

	public String login() {
		
		return "admin/login"; 
	}
	
	@RequestMapping(value="login.do", method= RequestMethod.GET)
    public String loginDo() {
		
        return "redirect:/admin/login";
    }
	
	/** 로그인 기능 **/    
    @RequestMapping(value="login.do", method = RequestMethod.POST)
    public ModelAndView login_check(AdminDTO dto, HttpSession session, ModelAndView mav) {
        
    	AdminDTO aresult = adminService.loginCheck(dto, session);
        
        if(aresult != null) { 
        	session.setAttribute("aresult", aresult);
      	
            mav.setViewName("customer/main");      
            mav.addObject("message", "success"); 
            
            return mav; 
        }else {
            mav.setViewName("admin/login"); 
            mav.addObject("message", "error"); 
            
            return mav; 
        }
    }
        

    /** 로그아웃 **/
    @RequestMapping("logout.do")
    public String logout(HttpSession session, ModelAndView mav) {
    	
        adminService.logout(session);
        
        mav.addObject("message", "logout");
    
        return "redirect:/member/login";
    }
}
