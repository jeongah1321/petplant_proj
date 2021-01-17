<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>board_view</title>
<link rel="stylesheet" href="${path}/resources/css/board_view_style.css">
<%@ include file="../include/header.jsp" %>
<script src="${path}/resources/js/common.js"></script>

<!-- ckeditor 사용을 위해 js 파일 연결 -->
<script src="${path}/ckeditor/ckeditor.js"></script>

<script>
$(document).ready(function(){ 
	/* 게시글 관련 */
	// 1. 게시글 수정 버튼 클릭 이벤트 
	$("#btnUpdate").click(function(){ 
		var title = $("#title").val();
		if(title == ""){
			alert("제목을 입력하세요");
			document.writeView_form.title.focus();
			return;
		}
 		//첨부파일 이름들을 폼에 추가
		var that = $("writeView_form");
        var str = "";
        //id가 uploadList인것들 하위에 file이라는 클래스명을 가진 태그 각각 (each) 에 함수 적용
        $("#uploadedList .file").each(function(){
            str += "<input type='hidden' name='files' value='"+$(this).val()+"'>";
                //파일이름에다 인덱스 번호를 하나씩 붙인다.
        });
        //폼에 hidden 태그들을 추가함
        $("#writeView_form").append(str);

		document.writeView_form.action="${path}/board/update.do" // document.전송할 form의 id.action= 보내고 싶은 url; writeView_form은 키워드가 아니다 주의할것!!!
		document.writeView_form.submit(); // 폼에 입력한 데이터를 서버로 전송, document.전송할 form의 id.submit();
	});

	
	// 2. 게시글 삭제 버튼 클릭 이벤트
	$("#btnDelete").click(function(){
		if(confirm("삭제하시겠습니까?")){
			document.writeView_form.action = "${path}/board/delete.do";
			document.writeView_form.submit(); // 폼에 입력한 데이터를 서버로 전송
		}
	});
	
	// 3. 게시글 목록 버튼 클릭 이벤트 : 버튼 클릭시 상세보기 화면에 있던 페이지, 검색옵션, 키워드 값을 가지로 목록으로 이동
	$("#btnList").click(function(){ // 목록 페이지로 이동
		location.href = "${path}/board/list.do?curPage=${curPage}&search_option=${search_option}&keyword=${keyword}";
	});
	
	/* 댓글 관련 */
	// 1. 댓글 쓰기 버튼 클릭 이벤트 (ajax로 처리)
	$("#btnReply").click(function(){
		replyJson(); // json으로 입력 
	});
	
	// 2. 댓글 목록 
	listReply("1"); // 첫 번째 페이지에 댓글이 나오게 함, rest방식
});

// 1. 댓글 쓰기 함수 (json방식)
function replyJson(){
	var replytext = $("#replytext").val(); // 댓글 내용
    var bno = "${dto.bno}"; // 게시물 번호
    var secretReply = "n"; // 비밀댓글 체크여부 
    if($("#secretReply").is(":checked")){
    	secretReply = "y";
    }
    $.ajax({
        type: "post",
        url: "${path}/reply/insertRest.do",
        headers: {"Content-Type" : "application/json"},
        dataType: "text",
        data: JSON.stringify({
        	bno : bno,
        	replytext : replytext,
        	secretReply : secretReply
        }),
        success: function(){
            alert("댓글이 등록되었습니다.");
            listReply("1");
        }
	});
}

// 2_1. 댓글 목록 - Rest방식
function listReply(num){
	$.ajax({
		type: "post",
		url: "${path}/reply/list/${dto.bno}/"+num,
		success: function(result){
			// responseText가 result에 저장
			$("#listReply").html(result);
		}
	});
}
// 2_2. 댓글 목록 - 날짜 형식 변환 함수 작성
//타임스탬프값(숫자형)을 문자열 형식으로 변환
function changeDate(date){
    date = new Date(parseInt(date));
    year = date.getFullYear();
    month = date.getMonth();
    day = date.getDate();
    hour = date.getHours();
    minute = date.getMinutes();
    second = date.getSeconds();
    strDate = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
    return strDate;
}

// 2_3. 댓글 수정화면 생성 함수
function showReplyModify(rno){
	$.ajax({
		type: "post",
		url: "${path}/reply/detail/"+rno,
		success: function(result){
			$("#modifyReply").html(result);
			// 태그.css("속성", "값")
			$("#modifyReply").css("visibility", "visible");
			// 댓글 상세화면의 textarea 태그의 아이디를 선택해서 focus를 지정합니다. 
            $("#detailReplytext").focus();
		}
	});
}

// 게시판 글쓰기 첨부파일 등록
$(document).ready(function(){
	// 1. 첨부파일 목록 불러오기 함수 호출
	listAttach();
	
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
            dataType: "text",                 // text 타입으로 보낸다.
            processData: false,    // 요청으로 보낸 데이터를 query string 형태로 변환할지 여부
            // processType: false - header가 아닌 body로 전달 
            contentType: false,    // 서버로 보내지는 데이터의 기본값
            type: "post",
			//호출처리가 완료된 다음 (성공한 후)에 실행되는 구문
			//컨트롤러에서 업로드 경로와 상태코드 (200)을 리턴해서 이쪽으로 보낸다.
			success: function(data){   //콜백 함수
                console.log(data);
                //data : 업로드한 파일 정보와 Http 상태 코드
                var fileInfo = getFileInfo(data);  //첨부파일의 정보 Common.js에서 정의한 메소드
                console.log(fileInfo);
                var html = "<a href='"+fileInfo.getLink+"' style='color: gray; forn-size: 13px;'>"+fileInfo.fileName+"</a><br>"; // 하이퍼링크 
                html += "<input type='hidden' class='file' value='"+fileInfo.fullName+"'>"; //hidden 태그를 추가 class='file'인 것이 생김
                $("#uploadedList").append(html); //div에 추가
                }
        });
    });
	
	// 3. 첨부파일 삭제 ajax 요청
	// 태그.on("이벤", "자손태그", 이벤트 핸들러)
	$("#uploadedList").on("click", ".fileDel", function(e){
		var that = $(this); // 클릭한 a태그
		$.ajax({
			type: "post",
			url: "${path}/upload/deleteFile",
			data: {fileName: $(this).attr("data-src")},
			dataType: "text",
			success: function(result){
				if(result == "deleted"){
					that.parent("div").remove();
				}
			}
		});
	});
});

// 4. 첨부파일 목록 ajax요청 처리
function listAttach(){
	$.ajax({
		type: "post",
		url: "${path}/board/getAttach/${dto.bno}",
		success: function(list){
			$(list).each(function(){
				// each문 내부의 this : 각 step에 해당되는 값을 의미
				var fileInfo = getFileInfo(this);
				// a태그안에는 파일의 링크를 걸어주고, 목록에는 파일의 이름 출력
				var html = "<div class='uploadedList'><a href='"+fileInfo.getLink+"'>"+fileInfo.fileName+"</a>&nbsp;&nbsp;";
				// 삭제 버튼
				html += "<a href='#' class='fileDel' data-src='"+this+"'>[삭제]</a></div>"
				$("#uploadedList").append(html);
			});
		}
	});
}
</script>

</head>
<body>
<%@ include file="../include/menu.jsp" %>
    <!--  main 부분  -->
    <main class="main">
		<section class="wrapper">
	        <div class="mainContent">
		        <h2 class="main_title">게시글 보기</h2>
					<!-- 게시물을 작성하기 위해 컨트롤러의 insert.do로 맵핑 -->
					<c:choose>
						<c:when test="${dto.board_show == 'y'}">
							<!-- show가 y면(삭제상태가 아닐 때) -->
							<!-- 게시물 상세보기 영역 -->
							<form id="writeView_form" name="writeView_form" method="post" action="">
								<table class="table">
						            <tr>
						                <td class="w100">제목</td>
						                <td colspan="2">
						                	<input name="title" id="title" style="width:100%;" value="${dto.title}" placeholder="제목을 입력하세요">
						                </td>
						                <td class="w100">조회수</td>
						                <td>${dto.viewcnt}</td>
						            </tr>
						            <tr>
						                <td class="w100">작성일자</td>
						                <td colspan="2"><fmt:formatDate value="${dto.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						                <td class="w100">작성자</td>
						                <td>${dto.writer}</td>
						            </tr>
						            <tr>
						            	<td class="w100">내용</td>
						            	<td colspan="4">
						            		<textarea id="content" name="content" style="width:100%" rows="2" placeholder="내용을 입력하세요">${dto.content}</textarea>
						            	</td>
						            </tr>
						        </table>

								<script>
								// ckeditor 적용
								//id가 content인 태그 (글의 내용을 입력하는 태그)를 ck에디터를 적용한다는 의미
								CKEDITOR.replace("content",{
								    filebrowserUploadUrl: "${path}/imageUpload.do",
								    height: "400px"
								});
								</script>
							    
							    <!-- 첨부파일 등록 -->
							    <div class="attach-form"> 첨부파일을 등록하세요
							    	<!-- 첨부파일 등록영역 -->
							        <div class="fileDrop"></div>
							        <!-- 첨부파일의 목록 출력영역 -->
							        <div id="uploadedList"></div>
							    </div>
							
							    <div style="width: 100%; text-align: center;">
									<!-- 수정,삭제에 필요한 글번호를 hidden 태그에 저장 -->    
							        <input type="hidden" name="bno" value="${dto.bno}">
							        
							        <!-- 본인만 수정,삭제 버튼 표시 -->
							        <c:if test="${sessionScope.login_id == dto.writer || sessionScope.admin_login_id == dto.writer}">
							            <button type="button" id="btnUpdate">수정</button>
							            <button type="button" id="btnDelete">삭제</button>
							        </c:if>

							        
							        <!-- 목록은 본인이 아니어도 확인이 가능하게 할 예정 -->
							        <button type="button" id="btnList">목록</button>
							    </div>
							</form>
						</c:when>
						<c:otherwise>
							<!-- show가 n이면(삭제상태일 때) -->
							삭제된 게시글 입니다. 
							<form id="writeView_form" name="writeView_form" method="post">
								<div style="width: 100%; text-align: center;">
							        <!-- 목록은 본인이 아니어도 확인이 가능하게 할 예정 -->
							        <button type="button" id="btnList">목록</button>
							    </div>
							</form>
						</c:otherwise>
					</c:choose>
					
					<!-- 댓글 작성 -->
					<div class="reply-form" style="width: 100%; text-align: center;">
						<!-- 로그인 한 회원에게만 댓글 작성폼이 보이게 처리 -->
						<c:if test="${sessionScope.login_id != null || sessionScope.admin_login_id != null}">
							<c:if test="${dto.board_show == 'y'}">
								<textarea style="width:100%; height:100px;" id="replytext" placeholder="댓글을 작성하세요"></textarea>
								<br>
								<input type="checkbox" id="secretReply">&nbsp;비밀 댓글
								<button type="button" id="btnReply">댓글쓰기</button>
							</c:if>
						</c:if>
					<hr>
					</div>
					<!-- 댓글 목록 출력 위치 -->
					<div id="listReply"></div>
	        </div>
		</section>
	</main>
</body>
</html>