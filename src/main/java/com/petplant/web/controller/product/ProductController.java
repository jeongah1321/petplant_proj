package com.petplant.web.controller.product;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.petplant.web.model.product.dto.ProductDTO;
import com.petplant.web.service.product.ProductService;
import com.petplant.web.util.Pager;

@Controller // 현재 클래스를 스프링에서 관리하는 Controller Bean으로 설정 
@RequestMapping("product/*") // 공통적인 url mapping 
public class ProductController {
 
    @Inject // 의존관계 주입(DI)
    ProductService productService; // 스프링에서 만든 서비스 객체를 연결시킴
    
    /** product 목록을 리턴 **/
    @RequestMapping("list.do") // 세부적인 url mapping
    public ModelAndView list(//RequestParam으로 옵션, 키워드, 페이지의 기본값을 각각 설정해준다.
		    @RequestParam(defaultValue="1") int curPage, // defaultValue로 null값과 400에러 방지
		    @RequestParam(defaultValue="all") String search_option,
		    @RequestParam(defaultValue="") String keyword)
		    //defaultValue를 설정하지 않으면 null point 에러가 발생할수 있기 때문에 기본값을 설정해주어야 한다.
		                        throws Exception{
        
		// 레코드 개수 계산
        int count = productService.countArticle(search_option, keyword); // 검색옵션과 키워드를 고려
        
//        System.out.println("count" + count);
        
        // 페이지 관련 설정, 시작번호와 끝번호를 구해서 각각 변수에 저장함
        Pager pager = new Pager(count, curPage); //레코드 번호와 원하는 페이지의 번호를 주게 되면 
        int start = pager.getPageBegin();
        int end = pager.getPageEnd();//
        
        System.out.println("Board start : "+start + " / end : "+end);
        
        // 게시물 목록을 출력하기 위해 <BoardDTO>타입에 list변수에 게시물 목록관련 값들을 저장함.
        // 넣어야될 값들이 여러개 있으므로 haspmap.put 메소드를 사용해서 값들을 넣어서 list에 저장  
		List<ProductDTO> list = productService.listAll(search_option, keyword, start, end); //게시물 목록
		
//		System.out.println("list:"+list);
		
		ModelAndView mav = new ModelAndView();
		// 자료를 보낼 페이지를 지정해야하고, 자료를 지정해야 하기 때문에 ModelAndView 객체를 생성한다.
		
		Map<String,Object> map = new HashMap<String, Object>(); // 데이터가 많기 때문에 hashmap<>에 저장한다.
        map.put("list", list); //map에 자료 저장
        map.put("count", count);
        map.put("pager", pager); //페이지 네비게이션을 위한 변수
        map.put("search_option", search_option);
        map.put("keyword",keyword); 
        mav.setViewName("product/list"); //포워딩할 뷰의 이름
        mav.addObject("map", map); //ModelAndView에 map을 저장
        
        return mav; // board/list.jsp로 이동
	}
        
    /** 상세 페이지 **/
    @RequestMapping("/detail.do") // url에 사용된 변수는 PathVariable 사용
    public ModelAndView detail(@RequestParam int product_id, @RequestParam int curPage,
			@RequestParam String search_option,
			@RequestParam String keyword, 
			HttpSession session) throws Exception {
    	
		ModelAndView mav = new ModelAndView();
		//view로 자료를 넘기기 위해 mav에 값들을 저장해서 view.jsp로 리턴시킴
		mav.addObject("dto", productService.detailProduct(product_id)); // 게시물 내용 
		mav.addObject("curPage", curPage);
        mav.addObject("search_option", search_option);
        mav.addObject("keyword", keyword);
        mav.setViewName("product/detail"); // 포워딩할 뷰의 이름
        
        return mav;
        // http://localhost:8080/board/view.do?bno=55&curPage=1&search_option=all&keyword=
        // http://localhost:8080/product/detail/1&curPage=1&search_option=all&keyword=
    }
    
    /** product 글쓰기 **/
    @RequestMapping("write.do")
    public String write() {
        return "product/write";
    }//데이터처리 없이 글쓰기 페이지로 포워딩
    
    // 관리자 페이지에서 맵핑된 메소드
    // 첨부파일을 추가하는 메소드 (그러니까 과일의 사진을 추가)
    @RequestMapping("insert.do")
    public String insert(@ModelAttribute ProductDTO dto) throws Exception {
        String filename = "-";
        if(!dto.getFile1().isEmpty()) { //첨부파일이 존재하면 (isEmpty()는 빈공간이라는 뜻인데 !가 붙었으므로 첨부파일이 존재하면..이라는 뜻)
        	filename = dto.getFile1().getOriginalFilename(); //dto에 저장된 서버상에 업로드된 파일명을 반환해서 filename변수에 저장한다.
        	
        	// 이미지 파일이 저장된 경로를 지정(배포 디렉토리 경로)
        	String path="/Users/jeongahkim/Desktop/java_ex/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PetPlant/resources/images/"; 
              // 이렇게 하지 않으면 이미지를 추가했을때 개발 디렉토리와 배포 디렉토리가 다르기 때문에 일일히 새로고침을 해주어야 한다.
              // 파일업로드시에는 예외처리를 해주어야 한다.
            try {
                new File(path).mkdir(); //새로운 파일의 경로를 생성하고 해당 경로에 폴더를 만든다.
                dto.getFile1().transferTo(new File(path+filename));  //생성한 디렉터리에 파일을 저장한다. (dto에 저장한다는 뜻)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        dto.setPicture_url(filename); //첨부파일을 dto에 저장
        productService.insertProduct(dto); //서비스에 dto에 있는 이미지 파일을 저장함
        return "redirect:/product/list.do"; //파일이 첨부되면 list페이지로 되돌아간다.
    }
    
    /** product 내용 수정 **/ 
	//상품정보 편집 페이지로 이동하고 데이터를 보내주도록하는 메소드
	//상품의 아이디를 맵핑해서..
	@RequestMapping("edit/{product_id}")
	public ModelAndView edit(@PathVariable("product_id") int product_id, ModelAndView mav) throws Exception {    
		//다른페이지에서  pathvariable로 받은 product_id를 product_id에 저장하고,
		//데이터를 보내고, 페이지를 이동하기위해서 ModelAndView 타입을 사용한다.
        
		mav.setViewName("/product/edit"); //이동할 페이지의 이름
		//전달할 데이터를 저장
		mav.addObject("dto", productService.detailProduct(product_id)); //서비스로부터 상품 id(번호)를 받아와서 mav에 저장
		return mav; //페이지 이동  
    }
	
	//상품의 정보를 수정 (갱신) 할때 View에서 맵핑되는 메소드
    @RequestMapping("update.do")
    public String update(ProductDTO dto) throws Exception {
        String filename = "-";
        
        if(!dto.getFile1().isEmpty()) { //첨부파일이 존재하면
            filename = dto.getFile1().getOriginalFilename();//dto에 저장된 서버상에 업로드된 파일명을 반환해서 filename변수에 저장한다.
        	String path = "/Users/jeongahkim/Desktop/java_ex/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PetPlant/resources/images/"; 
            //이미지 파일이 저장된 경로를 지정
            
            try {
                new File(path).mkdir(); //새로운 파일의 경로를 생성하고 해당 경로에 폴더를 만든다.
                dto.getFile1().transferTo(new File(path+filename)); //지정된 디렉토리로 카피하는 명령어
            } catch (Exception e) {     //생성한 디렉터리에 파일을 저장한다. (dto에 저장한다는 뜻)
                e.printStackTrace();
            }
            dto.setPicture_url(filename); //이미지의 경로
        }else {
            //새로운 첨부파일이 올라가지않고 기존에 있는 정보를 그대로 써야하는 경우
            //기존의 정보를 가져와서 dto2변수에 넣고, dto
            ProductDTO dto2 = productService.detailProduct(dto.getProduct_id());
            dto.setPicture_url(dto2.getPicture_url());
        }
        
        productService.updateProduct(dto);
        
        return "redirect:/product/list.do";
    }

    /** product 삭제 **/
    //상품 정보를 삭제할때 뷰 (product_edit.jsp)페이지와 매핑되는 메소드
    //상품 정보를 삭제할때는 그 정보 안에 담긴 이미지도 같이 삭제 해야 한다.
    @RequestMapping("delete.do")
    public String delete(@RequestParam int product_id) throws Exception {     //RequestParam 어노테이션을 사용해서 상품의 id를 받아온다.
        String filename = productService.fileInfo(product_id); //fileaname변수에 파일을 삭제하기위해 서비스에서 상품의 id를 매개변수로 삼아 서비스에 저장한후 filename 변수에 저장
        if(filename != null && !filename.equals("-")) { //filename이 null값이 아니거나, filename안에 들어있는것이 아닌 것이 "-"와 같으면, (그러니까 filename에 값이 담겨있을 경우!!!!!)
        	String path = "/Users/jeongahkim/Desktop/java_ex/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PetPlant/resources/images/"; 
            //상품에 담겨있는 이미지도 같이 삭제해야하기 때문에 이미지 경로를 지정
            
            File f = new File(path+filename); //이미지 파일을 삭제하기 위해 (파일의 객체 (경로와 파일이 담겨있음)을 생성하고 변수 f에 저장)
            
            if(f.exists()) { 
                //exists()메소드는 유효성을 검사하는 메소드, 그러니까 값이 존재하는지 안하는지 판단하는 메소드이다.
                //f안에 이미지 파일과 경로가 존재한다면, delete()메소드로 변수 f안에 있는 이미지파일과 경로를 제거 (즉 파일을 제거)
                f.delete();
            }
        }
        productService.deleteProduct(product_id); //이미지를 삭제한 후에 나머지 (가격 등등) 자료들도 삭제하기 위해 서비스에 id를 매개변수로 줘서 저장(삭제함)
        
        return "redirect:/product/list.do"; //최신화 한 후에 list.jsp로 redirect한다.        
    }
}