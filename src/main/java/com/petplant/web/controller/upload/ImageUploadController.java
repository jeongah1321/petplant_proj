package com.petplant.web.controller.upload;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.petplant.web.util.UploadFileUtils;

@Controller // CKEDITOR를 이용한 이미지 업로드를 위한 컨트롤러
public class ImageUploadController {
	
	// 컨트롤러클래스의 로그를 출력
	private static final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
	
	@ResponseBody
	@RequestMapping("imageUpload.do")
	public void imageUpload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile upload) //C KEditor에서 데이터를 upload라는 이름으로 가져옴 
				throws Exception {
        
		/* http header 설정 : 브라우저단의 인코딩 지정 */
		response.setCharacterEncoding("UTF-8"); // 한글깨짐을 방지하기위해 캐릭터셋을 설정
		response.setContentType("text/html; charset=UTF-8"); // 마찬가지로 파라미터로 전달되는 response 객체의 한글 설정
		
		/* http body */
		String fileName = upload.getOriginalFilename(); // 업로드한 파일 이름
		byte[] bytes = upload.getBytes(); // 파일을 바이트 배열로 변환

		System.out.println("bytes"+bytes); // [B@15dda91b
		
		// 이미지를 업로드할 디렉토리(배포 경로로 설정)
		String uploadPath = "/Users/jeongahkim/Desktop/java_ex/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PetPlant/resources/images/";
		
		// 폴더를 생성하고 /2020/12/05/file명으로 구성
		String uploadedFileName = UploadFileUtils.uploadFile(uploadPath, fileName, bytes);
		String callback = request.getParameter("CKEditorFuncNum"); // CKEditor에서 사용하는 이름이니 그대로 씀 
		String fileUrl = request.getContextPath()+"/resources/images"+uploadedFileName;
		
		PrintWriter printWriter = response.getWriter();
		
		System.out.println("uploadedFileName: "+uploadedFileName);
		System.out.println("callback: "+callback);
		System.out.println("fileUrl: "+fileUrl);
		
		printWriter.println(
				"<script>window.parent.CKEDITOR.tools.callFunction("
				+callback+",'" + fileUrl +"','이미지가 업로드되었습니다.')"
				+"</script>");
		//스트림 닫기
		printWriter.flush();	
	}
}