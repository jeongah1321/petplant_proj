<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>board_write</title>
<link rel="stylesheet" href="${path}/resources/css/board_write_style.css">
<%@ include file="../include/header.jsp" %>
<script src="${path}/resources/js/common.js"></script>

<!-- ckeditor 사용을 위해 js 파일 연결 -->
<script src="${path}/ckeditor/ckeditor.js"></script>

<script>

//게시판 글쓰기 첨부파일 등록
$(document).ready(function(){	
	// 2. 첨부파일 추가 ajax 요청 
	// 파일 업로드 영역에서 기본효과를 제한 
    $(".fileDrop").on("dragenter dragover",function(e){
        e.preventDefault(); // 기본 효과 막음, 기본효과가 있으면 이미지 파일이 실행되어 버림
    });
	// 드래그해서 드롭한 파일들 ajax 업로드 요청 
    $(".fileDrop").on("drop",function(e){
        e.preventDefault(); // 기본 효과 막음  
        //드롭한 파일을 폼 데이터에 추가함
        var files = e.originalEvent.dataTransfer.files; // 드래그한 파일들 
        var file = files[0]; // 첫 번째 첨부파일
        //폼 데이터에 첨부파일 추가 (0번 인덱스이므로 첫번째 파일만 붙였다.)
        var formData = new FormData(); // 폼데이터 객체 
        formData.append("file",file); // 첨부파일 추가 
        $.ajax({ //비동기 방식으로 호출
            url: "${path}/upload/uploadAjax", // uploadAjax를 호출함
            data: formData,                   // formData를 보내고                   
            dataType: "text",                 // text 타입으로 받는다.
            processData: false,    // 요청으로 보낸 데이터를 query string 형태로 변환할지 여부
            // processType: false - header가 아닌 body로 전달 
            contentType: false,    // 서버에 데이터를 보낼 때 사용하는 content-type 헤더의 값(기본값은 대부분 "application / x - www - form - urlencoded")
            type: "post",
             
			//호출처리가 완료된 다음 (성공한 후)에 실행되는 구문
			//컨트롤러에서 uploadedFileName와 상태코드(200)을 리턴해서 이쪽으로 보낸다.
			success: function(data){   //콜백 함수
                console.log(data);
                //data : 업로드한 파일 정보와 Http 상태 코드
                var fileInfo = getFileInfo(data);  //첨부파일의 정보 Common.js에서 정의한 메소드
                console.log("fileInfo"+fileInfo);  // fileInfo[object Object]
                var html="<a href='"+fileInfo.getLink+"' style='color: gray; forn-size: 13px;'>"+fileInfo.fileName+"</a><br>"; // 하이퍼링크 
                html += "<input type='hidden' class='file' value='"+fileInfo.fullName+"'>"; //hidden 태그를 추가 class='file'인 것이 생김
                $("#uploadedList").append(html); //div에 추가
                }
        });
    });
	
    $("#btnSave").click(function(){
 		//첨부파일 이름들을 폼에 추가
        var str="";
        //id가 uploadList인것들 하위에 file이라는 클래스명을 가진 태그 각각 (each) 에 함수 적용
        $("#uploadedList .file").each(function(){
            str+="<input type='hidden' name='files' value='"+$(this).val()+"'>";
                //파일이름에다 인덱스 번호를 하나씩 붙인다.
        });
        //폼에 hidden 태그들을 추가함
        $("#write_form").append(str);
        
        document.write_form.action="${path}/board/insert.do"
        document.write_form.submit();
    });
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
    <!--  main 부분  -->
    <main class="main">
		<section class="wrapper">
	        <div class="mainContent">
		        <h2 class="main_title">글쓰기</h2>
		        
				<form id="write_form" name="write_form" method="POST" enctype = "multipart/form-data">
					<!-- 제목 입력 -->
				    <div>제목 <input name="title" id="title" style="width:100%;" placeholder="제목을 입력하세요"> 
				    </div>
				    <!-- 내용 입력 -->
				    <div style="width:100%;">
				        내용 <textarea id="content" name="content" rows="3" cols="80" placeholder="내용을 입력하세요"></textarea>
				    </div>
				    
				   	<script>
				         //id가 description인 태그를 ckeditor로 바꿔주세요 라는 뜻입니다.
						CKEDITOR.replace("content",{
							filebrowserUploadUrl: "${path}/imageUpload.do"
						});
				    </script> 
					
				    <!-- 첨부파일 등록 -->
				    <div class="attach-form"> 첨부파일을 등록하세요. 
				    	<!-- 첨부파일 등록영역 -->
				        <div class="fileDrop"></div>
				        <!-- 첨부파일의 목록 출력영역 -->
				        <div class="uploadedList" id="uploadedList"></div>
				    </div>
				    
				    <div style="width:100%; text-align:center;">
				        <button type="button" id="btnSave">확인</button>
				    </div>
				</form>
			</div>
		</section>
	</main>
</body>
</html>