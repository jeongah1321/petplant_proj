<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>product_list</title>
<link rel="stylesheet" href="${path}/resources/css/prd_list_style.css">
<%@ include file="../include/header.jsp" %>
<script>
$(function(){
    $("#btnAdd").click(function(){
        location.href="${path}/product/write.do";
    });
}); /* Add버튼을 누르면 write.do 실행하는 함수 */
function list(page){ // 현재 페이지의 조건을 넘겨준다. +뒤에있는 것들은 검색 
    location.href="${path}/product/list.do?curPage="+page
        +"&search_option=${map.search_option}"
        +"&keyword=${map.keyword}";
}
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
    <!--  main 부분  -->
    <main class="main">
		<section class="wrapper">
	        <div class="mainContent">
			<h2>Plants List</h2>
				
			<!--    products-form    -->
				<div class="products-form">
				    <h3 class="hidden">제품 목록</h3>
				    
			    	<div class="search-form">
				            <h3 class="hidden">product 검색폼</h3>
				            <form class="table-form" method="post" action="${path}/product/list.do">       
								<select name="search_option">
									<c:choose>
										<c:when test="${map.search_option == 'all' }">
											<option value="all" selected>전체</option>
											<option value="product_name_ko">한글식물명</option>
											<option value="product_name_en">영문식물명</option>
											<option value="classification">분류</option>
											<option value="difficulty">난이도</option>
										</c:when>
										<c:when test="${map.search_option == 'product_name_ko' }">
											<option value="all">전체</option>
											<option value="product_name_ko" selected>한글식물명</option>
											<option value="product_name_en">영문식물명</option>
											<option value="classification">분류</option>
											<option value="difficulty">난이도</option>
										</c:when>
										<c:when test="${map.search_option == 'product_name_en' }">
											<option value="all">전체</option>
											<option value="product_name_ko">한글식물명</option>
											<option value="product_name_en" selected>영문식물명</option>
											<option value="classification">분류</option>
											<option value="difficulty">난이도</option>
										</c:when>
										<c:when test="${map.search_option == 'classification' }">
											<option value="all">전체</option>
											<option value="product_name_ko">한글식물명</option>
											<option value="product_name_en">영문식물명</option>
											<option value="classification" selected>분류</option>
											<option value="difficulty">난이도</option>
										</c:when>
										<c:when test="${map.search_option == 'difficulty' }">
											<option value="all">전체</option>
											<option value="product_name_ko">한글명</option>
											<option value="product_name_en">영문명</option>
											<option value="classification">분류</option>
											<option value="difficulty" selected>난이도</option>
										</c:when>
									</c:choose>	
									<%-- <option value="all"
									<c:out value="${map.search_option=='all'?'selected':"}" /> > 이름+내용+제목</option> // 삼항연산자 자용법 --%>
								</select>
								<input name="keyword" type="text" autocomplete="off" value="${map.keyword}"/>
								<input type="submit" value="조회"/>
				            </form>
			    	</div>
					
				<c:if test="${sessionScope.admin_login_id != null}">        
					<div class="write-form">
					<button type="button" id="btnAdd"> 식물 등록 </button>
					</div>
				</c:if>
				
					&nbsp;${map.count}개의 게시물이 있습니다.<!-- map에 저장된 개시물의 개수를 불러옴 -->
				
					<div class="product_table">
			            <h3 class="hidden">product 목록</h3>
				            <table class="table">
				            	<thead>
								    <tr>
								   	 	<th class="w40">번호</th>
								        <th class="w130">분류</th>
								        <th class="w100">이미지</th>
								        <th class="expand">식물명</th>
								        <th class="w60">난이도</th>
								    </tr>
							    </thead>
							    <tbody>
								    <!--반복문 태그 c:forEach에서 list(ProductDTO)의 원소 변수 row 선언-->
									<c:forEach var="row" items="${map.list}">
									    <tr>
									        <td>${row.product_id}</td>
									        <td>${row.classification}</td>
									        <td>
									        <img src="${path}/images/${row.picture_url}" width="100" height="100">
									        </td>
									        <td class="text-align-left">									            
									        	<a href="${path}/product/detail.do?product_id=${row.product_id}&curPage=${map.pager.curPage}&search_option=${map.search_option}&keyword=${map.keyword}">
									        	${row.product_name_ko}(${row.product_name_en})</a>
									            <!-- 관리자만 편집 버튼 표시 -->
									            <c:if test="${sessionScope.admin_login_id != null}">
										            <br>
										            <a href="${path}/product/edit/${row.product_id}">[편집]</a>
									            </c:if>
									        </td>
									        <td>${row.difficulty}</td>
									    </tr>
									</c:forEach>
								</tbody>
				            </table>
					</div>
				</div>
				
				<!-- 페이지 네비게이션 출력 -->
				<div class="pager">
					<div class="btn-prev">
						<a href="javascript:list('1')"><i class="fas fa-angle-double-left"></i></a>
						<a href="javascript:list('${map.pager.prevPage}')"><i class="fas fa-angle-left"></i></a>
					</div>	
					
					<ul class="pager_number">
						<c:forEach var="num" 
			                begin="${map.pager.blockBegin}"
			                end="${map.pager.blockEnd}">
			                <c:choose>
			                    <c:when test="${num == map.pager.curPage}">
			                    
			                    <!-- 현재 페이지인 경우 하이퍼링크 제거 -->
			                    <!-- 현재 페이지인 경우에는 링크를 빼고 빨간색으로 처리를 한다. -->
			                        <li><strong><span style="color: #8cba35; padding: 6px 12px;">${num}</span></strong></li>
			                    </c:when>
			                    <c:otherwise>
			                        <li><a href="javascript:list('${num}')">${num}</a></li>
			                    </c:otherwise>
			                </c:choose>
			            </c:forEach>
		            </ul>
		            
		            <div class="btn-next">
			            <a href="javascript:list('${map.pager.nextPage}')"><i class="fas fa-angle-right"></i></a>
			            <a href="javascript:list('${map.pager.totPage}')"><i class="fas fa-angle-double-right"></i></a>
		       		</div>
		        </div>  
		        
	        </div>
		</section>
    </main>
</body>
</html>