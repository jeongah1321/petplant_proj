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

@Controller 
public class ImageUploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
	
	@ResponseBody
	@RequestMapping("imageUpload.do")
	public void imageUpload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam MultipartFile upload)
				throws Exception {
        
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		
		String fileName = upload.getOriginalFilename(); 
		
		byte[] bytes = upload.getBytes(); 

		logger.info("bytes : " + bytes);// [B@15dda91b
		
		String uploadPath = "/Users/jeongahkim/Desktop/java_ex/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PetPlant/resources/images/";
		String uploadedFileName = UploadFileUtils.uploadFile(uploadPath, fileName, bytes);
		String callback = request.getParameter("CKEditorFuncNum");
		String fileUrl = request.getContextPath()+"/resources/images"+uploadedFileName;
		
		PrintWriter printWriter = response.getWriter();
		
		logger.info("uploadedFileName : " + uploadedFileName);
		logger.info("callback : " + callback);
		logger.info("fileUrl : " + fileUrl);
		
		printWriter.println(
				"<script>window.parent.CKEDITOR.tools.callFunction("
				+callback+",'" + fileUrl +"','이미지가 업로드되었습니다.')"
				+"</script>");
		
		printWriter.flush();//스트림 닫기
	}
}