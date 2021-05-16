package com.petplant.web.controller.product;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.petplant.web.controller.member.MemberController;
import com.petplant.web.model.product.dto.ProductDTO;
import com.petplant.web.service.product.ProductService;
import com.petplant.web.util.Pager;

@Controller 
@RequestMapping("product/*")
public class ProductController {
	
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
 
    @Inject // 의존관계 주입(DI)
    ProductService productService;
    
    /** product 목록을 리턴 **/
    @RequestMapping("list.do")
    public ModelAndView list(
		    @RequestParam(defaultValue="1") int curPage,
		    @RequestParam(defaultValue="all") String search_option,
		    @RequestParam(defaultValue="") String keyword)
		                        throws Exception{
        
        int count = productService.countArticle(search_option, keyword);
        
        logger.info("count" + count);
        
        Pager pager = new Pager(count, curPage);
        
        int start = pager.getPageBegin();
        int end = pager.getPageEnd();
        
        logger.info("Board start : "+start + " / end : "+end);
        
		List<ProductDTO> list = productService.listAll(search_option, keyword, start, end);
		
		logger.info("list:"+list);
		
		Map<String,Object> map = new HashMap<String, Object>(); 
        map.put("list", list);
        map.put("count", count);
        map.put("pager", pager);
        map.put("search_option", search_option);
        map.put("keyword",keyword); 
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("product/list"); 
        mav.addObject("map", map); 
        
        return mav;
	}
        
    /** 상세 페이지 **/
    @RequestMapping("/detail.do") 
    public ModelAndView detail(@RequestParam int product_id, @RequestParam int curPage,
			@RequestParam String search_option,
			@RequestParam String keyword, 
			HttpSession session) throws Exception {
    	
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", productService.detailProduct(product_id)); 
		mav.addObject("curPage", curPage);
        mav.addObject("search_option", search_option);
        mav.addObject("keyword", keyword);
        mav.setViewName("product/detail");
        
        logger.info("product_dto:" + productService.detailProduct(product_id));
        return mav;
    }
    
    /** product 글쓰기 **/
    @RequestMapping("write.do")
    public String write() {
    	
        return "product/write";
    }
    
    @RequestMapping("insert.do")
    public String insert(@ModelAttribute ProductDTO dto) throws Exception {
        String filename = "-";
        if(!dto.getFile1().isEmpty()) { 
        	filename = dto.getFile1().getOriginalFilename(); 
        	
        	String path="/Users/jeongahkim/Desktop/java_ex/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PetPlant/resources/images/"; 
              
            try {
                new File(path).mkdir(); 
                dto.getFile1().transferTo(new File(path+filename));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        dto.setPicture_url(filename);
        productService.insertProduct(dto);
        return "redirect:/product/list.do";
    }
    
    /** product 내용 수정 **/ 
	@RequestMapping("edit/{product_id}")
	public ModelAndView edit(@PathVariable("product_id") int product_id, 
			ModelAndView mav) throws Exception {    
        
		mav.setViewName("/product/edit"); 
		mav.addObject("dto", productService.detailProduct(product_id));
		
		return mav;
    }
	
	//상품의 정보를 수정할때 View에서 맵핑되는 메소드
    @RequestMapping("update.do")
    public String update(ProductDTO dto) throws Exception {
    	
        String filename = "-";
        
        if(!dto.getFile1().isEmpty()) {
            filename = dto.getFile1().getOriginalFilename();
        	String path = "/Users/jeongahkim/Desktop/java_ex/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PetPlant/resources/images/"; 

            try {
                new File(path).mkdir();
               
                dto.getFile1().transferTo(new File(path+filename)); 
            } catch (Exception e) {
                e.printStackTrace();
            }
            dto.setPicture_url(filename);
        }else {
            ProductDTO dto2 = productService.detailProduct(dto.getProduct_id());
            dto.setPicture_url(dto2.getPicture_url());
        }
        
        productService.updateProduct(dto);
        
        return "redirect:/product/list.do";
    }

    /** product 삭제 **/
    @RequestMapping("delete.do")
    public String delete(@RequestParam int product_id) throws Exception {
    	
        String filename = productService.fileInfo(product_id); 
        
        if(filename != null && !filename.equals("-")) { 
        	String path = "/Users/jeongahkim/Desktop/java_ex/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PetPlant/resources/images/"; 
            
            File f = new File(path+filename); 
            
            if(f.exists()) { 
                f.delete();
            }
        }
        productService.deleteProduct(product_id);
        
        return "redirect:/product/list.do";       
    }
}