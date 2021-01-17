<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>product_edit</title>
<link rel="stylesheet" href="${path}/resources/css/prd_edit_style.css">
<%@ include file="../include/header.jsp" %>

<!-- ckeditor 사용을 위해 js 파일 연결 -->
<script type="text/javascript" src="${path}/ckeditor/ckeditor.js"></script>

<script>
$(function(){
	$("#btnUpdate").click(function(){ 
	    document.productEdit_form.action = "${path}/product/update.do";
	    document.productEdit_form.submit();
	});
	$("#btnDelete").click(function(){ 
		if (confirm("삭제하시겠습니까?")) {
	        document.productEdit_form.action = "${path}/product/delete.do";
	        document.productEdit_form.submit();
	    }
	});
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp"%>
<main class="main">
	<section class="wrapper">
        <div class="mainContent">
	        <h3>식물 정보 편집</h3>    
		    <form id="productEdit_form" name="productEdit_form" method="post" enctype="multipart/form-data">
        	<!-- 파일 업로드를 하기위한 타입, 필수로 작성해야 한다.-->
		        <table class="table">
		            <!-- 관리자로그인을 한 후에 들어갈 수 있는 상품정보 편집정보 -->
		            <!-- 해당되는 자료들은 dto에서 가져와서 보여준다. -->
		            <tr>
		                <td class="w100">분류</td>
		                <td><input name="classification" value="${dto.classification}" style="width:100%"></td>
		            </tr>
		            <tr>
		                <td class="w100">한글식물명</td>
		                <td><input name="product_name_ko" value="${dto.product_name_ko}" style="width:100%"></td>
		            </tr>
		            <tr>
		                <td class="w100">영문식물명</td>
		                <td><input name="product_name_en" value="${dto.product_name_en}" style="width:100%"></td>
		            </tr>
		            <tr>
		                <td class="w100">원산지</td>
		                <td><input name="origin" value="${dto.origin}" style="width:100%"></td>
		            </tr>
		 			<tr>
		                <td class="w100">난이도</td>
		                <td><input name="difficulty" value="${dto.difficulty}" style="width:100%"></td>
		            </tr>
		            <tr>
		                <td class="w100">설명</td>
		                <td><textarea style="width:100%" rows="5" name="description" id="description">${dto.description}</textarea></td>
			            <script>
			            	//id가 description인 태그를 ckeditor로 바꿔주세요
							CKEDITOR.replace("description",{
								filebrowserUploadUrl: "${path}/imageUpload.do"
							});
			            </script>
		            </tr>
		            
		            <tr>
		            	<td class="w100">상품 이미지</td>
		            	<td><img src="${path}/resources/images/${dto.picture_url}" width=100px height="100px">
		            	<br>
		            	<input type="file" name="file1">
		            	</td>
		            </tr>
		        </table>
		        <div class="btn">
	           		<input type="hidden" name="product_id" value="${dto.product_id}">
	           		<input type="button" id="btnUpdate" value="수정">
	           		<input type="button" id="btnDelete" value="삭제">
	           		<input type="button" id="btnList" value="목록" onclick="location.href='${path}/product/list.do'">            		
		        </div>
    		</form>
        </div>
	</section>
</main>
</body>
</html>
