package com.petplant.web.controller.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.petplant.web.service.board.BoardService;
import com.petplant.web.util.MediaUtils;
import com.petplant.web.util.UploadFileUtils;
 
@Controller
public class AjaxUploadController {

	private static final Logger logger = LoggerFactory.getLogger(AjaxUploadController.class);
    
	@Inject
	BoardService boardService;
 
	@Resource(name = "uploadPath")
		String uploadPath;
	 

    /************************ ajax 방식의 업로드 처리 ************************/
    
    /** ajax 업로드 매핑 **/
	@RequestMapping(value = "/upload/uploadAjax", method = RequestMethod.GET)
	public String uploadAjax() {
		return "/upload/uploadAjax";
	}
	 
	/** ajax 업로드 처리 매핑 **/
	@ResponseBody
	@RequestMapping(value="/upload/uploadAjax", method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		
		logger.info("originalName :" + file.getOriginalFilename());
		logger.info("size :" + file.getSize());
		logger.info("contentType :" + file.getContentType());

		return new ResponseEntity<String>(
				UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.OK);
	}
	 
	/** 이미지 표시 매핑 **/
	@ResponseBody 
	@RequestMapping("/upload/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();
			
			in = new FileInputStream(uploadPath + fileName);
			
			if (mType != null) {
			    headers.setContentType(mType);
			    
			} else {
				fileName = fileName.substring(fileName.indexOf("_") + 1);

				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

				headers.add("Content-Disposition", "attachment; filename=\"" 
				+ new String(fileName.getBytes("utf-8"), "iso-8859-1") + "\"");
			}
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (Exception e) {
		    e.printStackTrace();
		    
		    entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST); 
		} finally {
		    if (in != null)
		        in.close();
		}
		return entity;
	}
	
	/** 파일 삭제 매핑 **/
	@ResponseBody
	@RequestMapping(value="/upload/deleteFile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName){
		
		logger.info("fileName:"+fileName); 
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		if(mType != null) { 
		    String front = fileName.substring(0, 12);
		    String end = fileName.substring(14);
		    
		    new File(uploadPath+(front+end).replace('/',File.separatorChar)).delete();

		}
		
		new File(uploadPath+fileName.replace('/',File.separatorChar)).delete();
		
		boardService.deleteFile(fileName); 
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
