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
	// 로깅을 위한 변수
	private static final Logger logger = LoggerFactory.getLogger(AjaxUploadController.class);
    
	@Inject
	BoardService boardService;
 
	// 업로드 디렉토리 servlet-context.xml에 설정되어 있음(bean의 id가 uploadPath인 태그 참조) 
	@Resource(name = "uploadPath")
		String uploadPath;
	 
	// 업로드 흐름 : 업로드 버튼 클릭 => 임시디렉토리에 업로드 => 지정된 디렉토리에 저장 => 파일정보가 file에 저장 

    /************************ ajax 방식의 업로드 처리 ************************/
    
    /** 1. ajax 업로드 매핑 **/
	// 파일첨부 페이지로 이동
	@RequestMapping(value = "/upload/uploadAjax", method = RequestMethod.GET)
	public String uploadAjax() {
		return "/upload/uploadAjax";
	}
	 
	/** 2. ajax 업로드 처리 매핑 **/
	// 업로드한 파일은 MultipartFile 변수에 저장됨
	// 파일의 한글처리 : produces="text/plain;charset=utf-8"
	@ResponseBody // json 형식으로 리턴(view가 아닌 data 리턴) 
	@RequestMapping(value="/upload/uploadAjax", method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		logger.info("originalName :" + file.getOriginalFilename());
		logger.info("size :" + file.getSize());
		logger.info("contentType :" + file.getContentType());
		// 업로드한 파일 정보와 Http 상태 코드를 함께 리턴
		return new ResponseEntity<String>(
				UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.OK);
		// getBytes()의 인자로 캐릭터셋을 넘기지 않으면 사용자 플랫폼의 기본 charset으로 인코딩 된다. 
	}
	 
	/** 3. 이미지 표시 매핑 **/
	@ResponseBody // json 형식으로 리턴(view가 아닌 data 리턴) 
	@RequestMapping("/upload/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		// 서버의 파일을 다운로드하기 위한 스트림
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		try {
			// 확장자를 추출하여 formatName에 저장 
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			// 추출한 확장자를 MediaUtils클래스에서 이미지파일 여부를 검사하고 리턴받아 mType에 저장 
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// 헤더 구성 객체(외부에서 데이터를 주고받을 때는 header와 body를 구성해야하기 때문에)
			HttpHeaders headers = new HttpHeaders();
			
			// InputStream 생성
			in = new FileInputStream(uploadPath + fileName);
			if (mType != null) { // 이미지 파일이면
			    headers.setContentType(mType);
			} else { // 이미지가 아니면
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				// 다운로드용 컨텐트 타입
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

				// headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
				headers.add("Content-Disposition", "attachment; filename=\"" 
				+ new String(fileName.getBytes("utf-8"), "iso-8859-1") + "\"");
				// 바이트배열의 스트링으로 한글 깨짐 방지
				// ㄴ new String(fileName.getBytes("utf-8"),"iso-8859-1")
				//    *iso-8859-1 서유럽언어, 큰 따옴표 내부에 " \" 내용 \""
			}
			// 바이트배열, 헤더, HTTP상태코드
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (Exception e) {
		    e.printStackTrace();
		    entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST); // HTTP상태코드()
		} finally {
		    if (in != null)
		        in.close(); // 스트림 닫기
		}
		return entity;
	}
	
	/** 4. 파일 삭제 매핑 **/
	@ResponseBody // json 형식으로 리턴(view가 아닌 data 리턴)
	@RequestMapping(value="/upload/deleteFile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName){
		logger.info("fileName:"+fileName); 
		// 확장자를 추출하여 formatName에 저장
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		// 추출한 확장자를 MediaUtils클래스에서 이미지파일 여부를 검사하고 리턴받아 mType에 저장 
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		// 이미지의 경우(섬네일 + 원본파일 삭제), 이미지가 아니면 원본파일만 삭제
		if(mType != null) { // 이미지 파일이면
			// 섬네일 이미지 파일 추출
		    String front = fileName.substring(0, 12);
		    String end = fileName.substring(14);
		    
		    // 섬네일 이미지 삭제 
		    new File(uploadPath+(front+end).replace('/',File.separatorChar)).delete();
		    // File.separatorChar : 유닉스 / 윈도우즈\    
		}
		// 원본 파일 삭제
		new File(uploadPath+fileName.replace('/',File.separatorChar)).delete();
		
		// 레코드 삭제
		boardService.deleteFile(fileName); 
		
		// 데이터와 http 상태 코드 전송
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
