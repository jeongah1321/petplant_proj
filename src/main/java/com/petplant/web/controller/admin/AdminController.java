package com.petplant.web.controller.admin;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.petplant.web.model.member.dto.MemberDTO;
import com.petplant.web.service.admin.AdminService;

@Controller
@RequestMapping("/admin/*") // 공통적인 url mapping 
public class AdminController {
	@Inject
	AdminService adminService; //service객체를 사용해야 하므로 inject로 의존성을 주입받아서 스프링에서 관리하게 한다.
    
	@RequestMapping("login.do")
	// view (menu.jsp)에서 RequestMapping된 메소드    
	public String login() {
		return "admin/login";  //admin디렉터리 하위에 있는 login.jsp 파일을 보여준다는 뜻
	}
    
    //view에서 login_check.do를 맵핑하면 이 메소드가 호출된다. 
    @RequestMapping("login_check.do")
    public ModelAndView login_check(MemberDTO dto, HttpSession session, ModelAndView mav) {
        // 이 메소드는 dto, session, mav를 매개값으로 받고, session안에 setAttribute 메소드를 사용해서 값들을 저장한다. 
        // ModelAndView는 데이터를 출력할 페이지와 전송을 동시에 함
        String login_name = adminService.loginCheck(dto, session);
        if(login_name != null) { //로그인 성공 (그러니까 loginCheck()메소드 안에 이름이 저장되어있다는 뜻)
            
            //dto에서 Userid()를 받아와 admin_userid라는 이름으로 session에 setAttribute()메소드를 사용해서 저장하고
            //name도 마찬가지로 session에 저장한다.
            session.setAttribute("admin_login_id", dto.getLogin_id());
            session.setAttribute("admin_login_name", login_name);
                        
            //dto에서 Userid()를 받아와 userid라는 이름으로 session에 setAttribute() 메소드를 사용해서 저장하고
            //name도 마찬가지로 session에 저장한다.
//            session.setAttribute("login_id", dto.getLogin_id());
//            session.setAttribute("login_name", login_name);
            
            mav.setViewName("customer/main");      // admin페이지를 보여준다는 뜻
            mav.addObject("message", "success"); // mav안에 있는 addObject()메소드를 사용해 message라는 키에 sucess라는 value를 담아 보낸다
        }else {
            
            mav.setViewName("admin/login");    //관리자 로그인이 실패했을때 보여주는 페이지
            mav.addObject("message", "error");   //error메시지를 출력한다.
        }
        return mav; 
    }
        

    /** 로그아웃 **/
    @RequestMapping("logout.do")
    public ModelAndView logout(HttpSession session, ModelAndView mav) {
        adminService.logout(session); //세션 초기화 작업 맡기기
        mav.setViewName("admin/login");
        mav.addObject("message", "logout");
    
        return mav; //로그인 페이지로 이동
    }
    //    //관리자 로그아웃 처리
//    //View에 있는 logout.do를 맵핑
//    @RequestMapping("logout.do")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        //로그아웃을 시키려면 session에 있는 데이터를 삭제시켜야 하기 때문에 invalidate()메소드를 사용해서
//        //안에 있는 데이터를 초기화 시킨다.
//        
//        return "redirect:/admin/login.do";
//        //데이터를 삭제시킨후에는 login페이지로 돌아가도록 한다.
//    }
}
