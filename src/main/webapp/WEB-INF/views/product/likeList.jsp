<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>like_list</title>
<link rel="stylesheet" href="${path}/resources/css/like_list_style.css">
<%@ include file="../include/header.jsp" %>
<script>
$(function(){
    $("#btnList").click(function(){
        location.href="${path}/product/list.do";
    });
    $("#btnDelete").click(function(){
        if(confirm("관심식물 목록을 비우겠습니까?")){
            location.href="${path}/like/deleteAll.do";
        }
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
			<h2>관심 식물</h2>
				<!--    products-form    -->
				<div class="products-form">
					<c:choose>
					    <c:when test="${map.count == 0}">
					        관심식물이 없습니다.
					    <!-- when은 ~~일때 라는 뜻 그러니까 map의 count가 0일때... -->
					    <!-- xml파일에서 hashmap에 list를 넣어놓았기 때문에 현재 map에 자료가 들어있다.  -->
					    <!-- map에 자료가 아무것도 없다면 -->
					    </c:when>
					    
					    <c:otherwise>
					    <!-- map.count가 0이 아닐때, 즉 자료가 있을때 -->
					    <!-- form을 실행한다.  -->
					    <!-- form의 id를 form1로 하고, method 방식을 post로 한다. 그리고 update.do페이지로 이동시킨다. -->
					        ${map.count}개의 관심식물이 있습니다.
					        <form id="likeList_form" name="likeList_form" method="post" action="${path}/like/update.do">
					            <table class="table">
				            		<thead>		                
					            		<tr>
						                    <th class="w40">번호</th>
						                    <th class="w130">분류</th>
						                    <th class="w100">이미지</th>
						                    <th class="expand">식물명</th>
						                    <th class="w100">난이도</th>
						                    <th class="w60">취소</th>
						                </tr>
					                </thead>
					                <tbody>
				                	<!-- map에 있는 list출력하기 위해 forEach문을 사용해 row라는 변수에 넣는다. -->
						            <!-- forEach var="개별변수" items="집합변수" -->
						            <c:forEach var="row" items="${map.list}">
						                <tr align="center">
						                	<td>${row.product_id}</td>
						                	<td>${row.classification}</td>
						                    <td>
									        <img src="${path}/images/${row.picture_url}" width="100" height="100">
									        </td>
									        <td>${row.product_name_ko}(${row.product_name_en})</td>
									        <td>${row.difficulty}</td>
						                   
						                    <td><a href="${path}/like/delete.do?like_id=${row.like_id}">[삭제]</a>
											<!-- 삭제 버튼을 누르면 delete.do로 장바구니 개별 id (삭제하길원하는 장바구니 id)를 보내서 삭제한다. -->
						                    </td>
						                </tr>
						            </c:forEach>
					                </tbody>
					            </table>
								<button type="button" id="btnDelete">비우기</button>
					        </form>
					    </c:otherwise>
					</c:choose>
					
					<div class="btn">
						<!-- btnUpdate와 btnDelete id는 위쪽에 있는 자바스크립트가 처리한다. -->
						<button type="button" id="btnList">식물 목록</button>
					</div>
				</div>
			</div>
		</section>
    </main>
</body>
</html>
