<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>product_detail</title>
<link rel="stylesheet" href="${path}/resources/css/prd_detail_style.css">
<%@ include file="../include/header.jsp" %>

<!-- ckeditor 사용을 위해 js 파일 연결 -->
<script type="text/javascript" src="${path}/ckeditor/ckeditor.js"></script>
<script>

</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<!--  main 부분  -->
<main class="main">
	<section class="wrapper">
        <div class="mainContent">
	        <h3 class="hidden">식물 정보</h3>
	        <div class="prd-ko_title text-align-center">${dto.product_name_ko}</div>
            <div class="prd-en_title text-align-center">${dto.product_name_en}</div>
            
            <div id="like_form">
	            <c:if test="${sessionScope.resultDTO.login_id != null}">
	              	<form id="form" name="form" method="post" action="${path}/like/insert.do" style="text-align: right;">
		                <input type="hidden" name="product_id" value="${dto.product_id}">
		                <input type="hidden" name="login_id" value="${sessionScope.resultDTO.login_id}">
		                <%-- <input type="hidden" name="curPage" value="${map.pager.curPage}">
		                <input type="hidden" name="search_option" value="${map.search_option}">
		                <input type="hidden" name="keyword" value="${map.keyword}"> --%>
		                <input id="btnLike" type="submit" value="♥Like(관심식물 등록)">
	            	</form>
	            </c:if>
            </div>	
            	    
		        <!-- 파일업로드를 위해 추가하는 타입 -->
		        <table class="table">
                  <tr>
                    <td class="prd-img" colspan="2" rowspan="8"><img src="${path}/images/${dto.picture_url}" width=300px height="300px"></td>
                     <th>분류</th>
                     <td class="text-align-center" colspan="2">${dto.classification}</td>
                  </tr>
                  <tr>
                    
                     <th>원산지</th>
                     <td class="text-align-center" colspan="2">${dto.origin}</td>
                  </tr>
                  <tr>
                      <th>난이도</th>
                      <td class="text-align-center" colspan="2">${dto.difficulty}</td>
                  </tr>
                  <tr>
                      <th class="description">설명</th>
                      <td class="description text-align-left" colspan="2">${dto.description}</td>
                  </tr>
				</table>

		    	<div class="btn">
                	<a id="btnList" href="${path}/product/list.do">목록</a>
           		</div>
        </div>
	</section>
</main>
</body>
</html>