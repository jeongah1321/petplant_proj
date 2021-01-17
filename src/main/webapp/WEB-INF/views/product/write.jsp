<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>product_write</title>
<link rel="stylesheet" href="${path}/resources/css/prd_write_style.css">
<%@ include file="../include/header.jsp" %>

<!-- ckeditor 사용을 위해 js 파일 연결 -->
<script type="text/javascript" src="${path}/ckeditor/ckeditor.js"></script>

<c:if test = "${sessionScope.admin_login_id == null}" > <!-- 세션을 체크했을때 null값과 같으면 (로그인을 안했다면) -->
	<script>
		alert("로그인 하신 후 사용하세요.");                  // 다음 메시지를 출력함..
		location.href = "${path}/admin/login.do";          // 로그인 페이지로 이동시켜주는 코드
	</script>
</c:if>

<script>

//상품을 추가하기위한 정보를 담아 insert.do로 보내는 자바스크립트 함수
$(function(){
	$("#btnSave").click(function(){ 
		/* var classification = $('input[name="classification"]:checked').val(); */
		var product_name_ko = $("#product_name_ko").val();
		var product_name_en = $("#product_name_en").val(); 
		var origin = $("#difficulty").val();
		var difficulty = $("#difficulty").val(); // document는 웹페이지에 접근하기위한 객체.. form1에 있는 상품의 값을 반환해서 price에 저장함
		
		/* if(classification == "") { //상품가격이 입력되어 있지 않으면
		    alert("분류를 선택하세요");
		    $("#classification").focus(); //form1페이지에 있는 "가격을 입력하세요" 에 커서를 올려둔다.
		    return;
		} */
		if(product_name_ko == "") { //상품 이름이 입력되어 있지 않으면
		    alert("한글식물명을 입력하세요");
		    $("#product_name_ko").focus(); //form1페이지에 있는 "상품명을 입력하세요" 에 커서를 올려둔다.
		    return;
		}
		if(product_name_en == "") { //상품 이름이 입력되어 있지 않으면
		    alert("영문식물명을 입력하세요");
		    $("#product_name_en").focus(); //form1페이지에 있는 "상품명을 입력하세요" 에 커서를 올려둔다.
		    return;
		}
		if(origin == "") { //상품 이름이 입력되어 있지 않으면
		    alert("원산지를 입력하세요");
		    $("#oring").focus(); //form1페이지에 있는 "상품명을 입력하세요" 에 커서를 올려둔다.
		    return;
		}
		if(difficulty == "") { //상품 이름이 입력되어 있지 않으면
		    alert("난이도를 입력하세요");
		    $("#difficulty").focus(); //form1페이지에 있는 "상품명을 입력하세요" 에 커서를 올려둔다.
		    return;
		}
		
		document.product_form.action = "${path}/product/insert.do"; //insert.do 페이지로 form1에 저장된 자료를 전송함
		document.product_form.submit();
    });
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp"%>
<!--  main 부분  -->
<main class="main">
	<section class="wrapper">
        <div class="mainContent">
	        <h2 class="main_title">식물 등록</h2>
	        
		    <form id="product_form" name="product_form" method="post" enctype="multipart/form-data">
		        <!-- 파일업로드를 위해 추가하는 타입 -->
		        <table class="table">
		        	<tr>
		                <td class="w100">분류</td>
		                <td id="classification">

			                <ul>
						      <li>
						        <input type="radio" name="classification" value="공기정화식물" checked>공기정화식물
						      </li>
						      <li>
						        <input type="radio" name="classification" value="허브">허브
						      </li>
						      <li>
						        <input type="radio" name="classification" value="다육식물">다육식물
						      </li>
						      <li>
						        <input type="radio" name="classification" value="꽃식물">꽃식물
						      </li>
						    </ul>

		                </td>
		            </tr>
		            <tr>
		                <td class="w100">한글식물명</td>
		                <td><input type="text" id="product_name_ko" name="product_name_ko" style="width:100%"></td>
		            </tr>
		            <tr>
		                <td class="w100">영문식물명</td>
		                <td><input type="text" id="product_name_en" name="product_name_en"  style="width:100%"></td>
		            </tr>
		            <tr>
		                <td class="w100">원산지</td>
		                <td><input type="text" id="origin" name="origin"  style="width:100%"></td>
		            </tr>
		            <tr>
		                <td class="w100">난이도</td>
		                <td><input type="text" id="difficulty" name="difficulty"  style="width:100%"></td>
		            </tr>
		            <tr>
		                <td class="w100">설명</td>
		                <td><textarea style="width:100%;" rows="5" id="description" name="description" ></textarea>
		                
			                <script type="text/javascript">
				            	//id가 description인 태그를 ckeditor로 바꿔주세요 라는 뜻입니다.
							CKEDITOR.replace("description",{
								filebrowserUploadUrl: "${path}/imageUpload.do"
							});
			            	</script>
		                </td>
		            </tr>
		
		            <tr>
		                <td class="w100">식물이미지</td>
		                <td style="text-align: left;"><input type="file" name="file1"></td>
		            </tr>
		        </table>
		 
                <div class="btn">
                	<input type="button" id="btnSave" value="등록" >
                    <input type="button" id="btnList" value="목록" onclick="location.href='${path}/product/list.do'"> <!-- "목록 버튼을 누르면 list.do페이지로 이동" -->
                </div>

		    </form>
		</div>
	</section>
</main>
</body>
</html>
