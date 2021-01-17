package com.petplant.web.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.petplant.web.model.product.dto.LikeDTO;
import com.petplant.web.service.product.LikeService;

@Controller
@RequestMapping("like/*") // 공통적인 url mapping 
public class LikeController {
	
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

	@Inject
    LikeService likeService;
	
    /** 개별 목록 출력 기능 **/
    // 장바구니 리스트 :cartService의 listCart, sumMoney 메서드 사용
    // 장바구니 목록과 금액합계는 로그인 아이디 정보가 있어야하므로 세션에서 아이디 정보를 받아낸다.
    @RequestMapping("list.do")
    public ModelAndView list(ModelAndView mav, HttpSession session) {
 
        // 장바구니 목록, 금액 합계, 배송료, 리스트의 사이즈(주문 아이템 갯수) 등
        // dto로 표현되지 않는 여러가지 정보를 담아 뷰로 넘겨야하므로 HashMap 사용
        Map<String, Object> map = new HashMap<String, Object>();
 
        //hashmap은 map(key,value)로 이뤄져 있고,
        //key값은 중복이 불가능 하고 value는 중복이 가능하다.
        //value에 null값도 사용이 가능하다.
        //전달할 정보가 많을 경우에는 HashMap<>을 사용하는 것이 좋다.
        //장바구니에 담을 값들이 많기 때문에 여기선 HashMap<>를 사용한다.
        
        String login_id = (String)session.getAttribute("login_id");
        //session에 저장된 userid를 getAttribute()메소드를 사용해서 얻어오고 오브젝트 타입이기 때문에
        //String 타입으로 타입변환한다.
        
        if (login_id != null) {// 로그인한 상태이면
            List<LikeDTO> list = likeService.listAll(login_id);// 서비스단에서 목록을 가져오고
            map.put("count", list.size()); // 레코드 개수 
            map.put("list", list); // 목록 
            mav.setViewName("product/likeList"); //이동할 페이지의 이름
            mav.addObject("map", map); //데이터 저장
 
            return mav; // ModelAndView 객체에 map을 담고 리스트 뷰를 설정해준 뒤 cart_list.jsp로 포워딩.
        } else { //로그인하지 않은 상태

            mav.setViewName("member/login.do");
            return mav;
//            return new ModelAndView("member/login", "", null);
            //로그인을 하지 않았으면 로그인 페이지로 이동시킨다.
        }
    } 
    
	/** 추가 기능 : DB에 데이터 생성됨 **/
    @RequestMapping("insert.do") // 세부적인 url mapping 
    // 페이지 이동시 list로 URL 변경을 원하므로 리다이렉트 사용
    // 로그인 시에만 장바구니 기능 이용할 수 있도록 로그인 정보를 담고있는 세션 객체 필요
    public String insert(@ModelAttribute LikeDTO dto, HttpSession session) {
    	// @ModelAttribute는 sumit된 form의 내용을 저장해서 전달받거나, 다시 뷰로 넘겨서 출력하기 위해 사용되는 오브젝트 이다.
        // 도메인 오브젝트나 DTO의 프로퍼티에 요청 파라미터를 바인딩해서 한번에 받으면 @ModelAttribute 라고 볼 수 있다.
        // @ModelAttribute는 컨트롤러가 리턴하는 모델에 파라미터로 전달한 오브젝트를 자동으로 추가해준다.
 
    	//로그인 여부를 체크하기 위해 세션에 저장된 아이디 확인
        // session객체의 attribute는 Object타입
        String login_id = (String)session.getAttribute("login_id");
 
        if (login_id == null) { // 로그인하지 않은 상태이면 로그인 화면으로 이동
            return "redirect:/member/login.do";
        }
        dto.setLogin_id(login_id);
//        System.out.println("login_id:"+login_id);
        
        // 장바구니 동일한 상품 레코드 확인
        int count = likeService.countLike(login_id, dto.getProduct_id());
//        System.out.println("count:"+count);
        if(count == 0) {
        	System.out.println("dto.getProduct_id:"+dto.getProduct_id());
        	likeService.insert(dto); // 장바구니 테이블에 저장됨
        	logger.info(login_id);
        } 
//        else {
//        	System.out.println("dto.getProduct_id:2"+dto.getProduct_id());
//        	likeService.update(dto);
//        	logger.error("이미 추가한 식물입니다.");
//        }
 
        return "redirect:/product/list.do"; // 장바구니 목록으로 이동
    }
 

    /** 개별 삭제 기능 **/ 
    @RequestMapping("delete.do")
    public String delete(@RequestParam int like_id) {
 
        likeService.delete(like_id);
 
        return "redirect:/like/list.do";
    }
 
    /** 전체 삭제 기능 **/
    @RequestMapping("deleteAll.do")
    public String deleteAll(HttpSession session) {
 
        // ServiceImpl에서 진행해도 되는 처리과정
        String login_id = (String) session.getAttribute("login_id");
        if (login_id != null) {
            likeService.deleteAll(login_id);
        }
 
        return "redirect:/like/list.do";
    }
}
