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

import com.petplant.web.model.member.dto.MemberDTO;
import com.petplant.web.model.product.dto.LikeDTO;
import com.petplant.web.service.product.LikeService;

@Controller
@RequestMapping("like/*")
public class LikeController {
	
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

	@Inject
    LikeService likeService;
	
    /** 개별 목록 출력 기능 **/
    @RequestMapping("list.do")
    public ModelAndView list(ModelAndView mav, HttpSession session) {
 
        Map<String, Object> map = new HashMap<String, Object>();
        
        MemberDTO resultDTO = (MemberDTO)session.getAttribute("resultDTO");

        if (resultDTO != null) {
            List<LikeDTO> list = likeService.listAll(resultDTO.getLogin_id());
            map.put("count", list.size()); 
            map.put("list", list); 
            mav.setViewName("product/likeList");
            mav.addObject("map", map);
 
            return mav;
        } else { 
            mav.setViewName("member/login.do");
            
            return mav;
        }
    } 
    
	/** 추가 기능 **/
    @RequestMapping("insert.do")
    public String insert(@ModelAttribute LikeDTO dto, HttpSession session) {
    	        
        int count = likeService.countLike(dto.getLogin_id(), dto.getProduct_id()); // 좋아요 중복 방지 

        if(count == 0) {
        	logger.info("dto.getProduct_id:"+dto.getProduct_id());
        	logger.info("dto.getLogin_id:"+dto.getLogin_id());
        	
        	likeService.insert(dto);
        } 

        return "redirect:/product/list.do";
    }
 
    /** 개별 삭제 기능 **/ 
    @RequestMapping("delete.do")
    public String delete(@RequestParam int like_id) {
 
        likeService.delete(like_id);
 
        return "redirect:/like/list.do";
    }
 
    /** 전체 삭제 기능 **/
    @RequestMapping("deleteAll.do")
    public String deleteAll(@ModelAttribute LikeDTO dto) {
    	
        likeService.deleteAll(dto.getLogin_id());

        System.out.println("deleteAll" + dto.getLogin_id());
 
        return "redirect:/like/list.do";
    }
}