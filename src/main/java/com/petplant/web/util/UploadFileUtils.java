package com.petplant.web.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
 
public class UploadFileUtils {
	// 로깅을 위한 변수
	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	// ㄴ Logger 객체 선언 : 로그를 남기려는 클래스에 로거 객체를 필드로 선언한다.
	// ㄴ org.slf4j 패키지의 LoggerFactory를 이용하여 Logger 객체를 생성하는 것이다.
	
	// 업로드를 하면 이 메소드가 호출된다.
	// File name를 똑같이 쓰면 덮어쓰기가 될 수 있으므로 uuid를 생성해서 파일이름 앞쪽에 붙여주어서 파일이름이 서로 중복되지 않게끔 한다.
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
		
		UUID uid = UUID.randomUUID();// UUID 발급, 랜덤한 UUID를 만들어 uid에 저장
		
		String savedName = uid.toString() + "_" + originalName;// uuid를 추가한 파일 이름(이 이름으로 저장됨) 
		
		String savedPath = calcPath(uploadPath); // calcPath에 업로드 경로를 매개값으로 줘서, 업로드한 날짜를 savedPath에 저장하고, 
		File target = new File(uploadPath + savedPath, savedName); 
		// target변수에 File 업로드경로와 저장경로에 저장한파일의 이름에 대한 File 객체를 생성한다.
		
		// 임시 디렉토리에 업로드된 파일을 지정된 디렉토리로 복사
		FileCopyUtils.copy(fileData, target); //target에 저장된 정보와 fileData(파일용량)을 복사
		
		// 파일의 확장자 검사  ex) a.jpg / aaa.bbb.ccc.jpg
		String formatName = originalName.substring(originalName.lastIndexOf(".") + 1);
		//lastIndexOf() 메소드는 String오브젝트에서 "." 문자열부터 끝쪽 방향으로 문자열을 찾는다.
		//"."이 여러개 있을 수도 있으므로 마지막 "." 뒤쪽부터 확장자이다.
		//그러니까 파일의 확장자가 뭔지 찾기 위해서 "." 뒷부분의 문자를 검색한다는 뜻이다.
		
		String uploadedFileName = null; // uploadedFileName의 초기값을 지정
		
		// 이미지 파일은 섬네일 사용
		// 타입을 집어넣으면 이미지인지 아닌지 확인이 가능하다.
		if (MediaUtils.getMediaType(formatName) != null) {
			// 만약 이미지 이면 섬네일 생성 (해당 그림이 드래그하면 작게보임)
			uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
		} else { //이 미지 파일이 아니면 아이콘을 생성한다.
			uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
		}
		System.out.println("uploadPath:"+uploadPath+"\n"+"savedPath:"+savedPath+"\n"+"savedName:"+savedName
				+"\n"+"uploadedFileName:"+uploadedFileName);
		return uploadedFileName;
	}
	
	/** 아이콘 생성 **/
	private static String makeIcon(String uploadPath, String path, String fileName) throws Exception {
		// 아이콘의 이름
		String iconName = uploadPath + path + File.separator + fileName;
		
		System.out.println("아이콘 생성");
		// File.separatorChar : 디렉토리 구분자(윈도우 \ , 유닉스(리눅스) /)
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/'); // 아이콘 이름을 리턴
	}
	
	/** 섬네일 생성 **/
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {
		// 이미지를 읽기 위한 버퍼
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
		// 100픽셀 단위의 섬네일 생성, 작은 섬네일이기 때문에 이미지의 크기를 줄여야 한다.
		// 높이가 100픽셀이면 가로 사이즈는 자동으로 지정된다.
		BufferedImage destImg = Scalr.resize(sourceImg, 
				Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		
		// 섬네일의 이름 : 섬네일에는 "s_" 를 붙인다.
		String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;
		
		File newFile = new File(thumbnailName); // 섬네일의 경로를 newFile 변수에 저장
		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		//lastIndexOf() 메소드는 String오브젝트에서 "." 문자열부터 끝쪽 방향으로 문자열을 찾는다.
		//"."이 여러개 있을 수도 있으므로 마지막 "." 뒤쪽부터 확장자이다.
		//그러니까 파일의 확장자가 뭔지 찾기 위해서 "." 뒷부분의 문자를 검색한다는 뜻이다.
		
		// 섬네일 생성
		// 아까 사이즈를 조정한 이미지파일형식, 파일이름(대문자로바꿔서), 아까 섬네일의 경로를 저장한 newFile변수를 넣어 
		// write 메소드를 사용해 섬네일을 생성
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		System.out.println("이미지 섬네일 생성");
		// 섬네일의 이름을 리턴함
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	/** calcPath **/
	// 년, 월, 일이 출력되게하는 메소드이고, static으로 선언되었으므로 메모리에 제일 처음 올려져 있다.
	// 업로드할 디렉토리를 (날짜별(년>월>일))로 생성한다.
	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		// 자릿수를 맞춰주기 위해서 DecimalFormat를 사용 : "월"이랑 "일"은 10보다 작을때가 있으므로 (1월,2월 -> 01월, 02월)	
		
		//디렉토리를 생성
		makeDir(uploadPath, yearPath, monthPath, datePath);
		logger.info(datePath);
		return datePath;
	}
	
	/** 디렉토리 생성 **/
	private static void makeDir(
	// 위에서 makeDir을 호출할때는 매개변수가 4개 이지만, String뒤에 있는 ...이 가변사이즈 매개변수이기 때문에 
	// 호출시에 매개변수의 숫자가 많아도 호출시에 매개변수가 배열로 만들어져 paths로 다 들어가기 때문에 쌓일수 있다.
	// ex) paths 0번이 yearPath, 1번이 monthPath가 되고, 2번이 datePath가 된다.
	String uploadPath, String... paths) {
	// 디렉토리가 존재하면 skip, (디렉토리가 기존에 존재하면 만들지 않는다는 뜻)
		if (new File(paths[paths.length - 1]).exists()) {
		    return;
		}
		// 디렉토리가 없을시에 디렉토리 생성 코드
		// paths 배열에 저장된 값들을 하나씩 path에 저장하고, 업로드
		for (String path : paths) {
			File dirPath = new File(uploadPath + path);
			if (!dirPath.exists()) {// 디렉토리가 존재하지 않으면
				dirPath.mkdir(); // 디렉토리 생성, mkdir()메소드는 패스명이 나타내는 디렉토리를 생성하는 메소드.
	        }
	    }
	}
}
