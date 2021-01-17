<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>board_list</title>
<link rel="stylesheet" href="${path}/resources/css/board_list_style.css">
<%@ include file="../include/header.jsp" %>
<script>
$(function(){ // 아이디가 btnWrite인 버튼을 누르게 되면 write.do 컨트롤러로 맵핑
    $("#btnWrite").click(function(){
        location.href="${path}/board/write.do";
    });
});
 
function list(page){ // 현재 페이지의 조건을 넘겨준다. +뒤에있는 것들은 검색 
    location.href="${path}/board/list.do?curPage="+page
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
	       
	        <h2 class="main_title">게시판</h2>
	        
		        <div class="search-form">
		            <h3 class="hidden">게시판 검색폼</h3>
		            <form class="table-form" method="post" action="${path}/board/list.do">       
						<select name="search_option">
							<c:choose>
								<c:when test="${map.search_option == 'all' }">
									<option value="all" selected>전체</option>
									<option value="writer">작성자</option>
									<option value="content">내용</option>
									<option value="title" >제목</option>
								</c:when>
								<c:when test="${map.search_option == 'writer' }">
									<option value="all" >전체</option>
									<option value="writer" selected>작성자</option>
									<option value="content">내용</option>
									<option value="title" >제목</option>
								</c:when>
								<c:when test="${map.search_option == 'content' }">
									<option value="all" >전체</option>
									<option value="writer">작성자</option>
									<option value="content" selected>내용</option>
									<option value="title" >제목</option>
								</c:when>
								<c:when test="${map.search_option == 'title' }">
									<option value="all" >전체</option>
									<option value="writer">작성자</option>
									<option value="content">내용</option>
									<option value="title" selected>제목</option>
								</c:when>
							</c:choose>	
							<%-- <option value="all"
							<c:out value="${map.search_option=='all'?'selected':"}" /> > 이름+내용+제목</option> // 삼항연산자 자용법 --%>
						</select>
						<input name="keyword" type="text" autocomplete="off" value="${map.keyword}"/>
						<input type="submit" id="btnSearch" value="조회"/>
					</form>
		        </div>
		        
		        <div class="write-form">
				<button type="button" id="btnWrite">글쓰기</button> &nbsp;
				${map.count}개의 게시물이 있습니다.<!-- map에 저장된 개시물의 개수를 불러옴 -->
		        </div>
		        
		        <div class="board">
		            <h3 class="hidden">게시판 목록</h3>
		            <table class="table">
		                <thead>
		                    <tr>
		                        <th class="w60">번호</th>
		                        <th class="expand">제목</th>
		                        <th class="w100">작성자</th>
		                        <th class="expand">날짜</th>
		                        <th class="w60">조회수</th>
		                    </tr>
		                </thead>
		                <tbody>
			                <!-- forEach var="개별데이터" items="집합데이터" -->
							<c:forEach var="row" items="${map.list}"> <!-- 컨트롤러에서 map안에 list를 넣었기 때문에 이렇게 받는다. -->
							    <c:choose>
							    	<c:when test="${row.board_show == 'y'}">
							    		<!-- board_show 컬럼이 y일때(삭제상태가 아닐때) -->
							    		    <tr>
												<td>${row.bno}</td>
											    <td style="text-align: left; padding-left: 15px;"><a href="${path}/board/view.do?bno=${row.bno}&curPage=${map.pager.curPage}&search_option=${map.search_option}&keyword=${map.keyword}">${row.title}</a>
											        <!-- 댓글의 개수 -->
											        <c:if test="${row.cnt > 0}">
											        	<span style="color: orange;">(${row.cnt})</span>
											        </c:if>
											    </td>
										        <td>${row.writer}</td>
										        <td><fmt:formatDate value="${row.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
										        <td>${row.viewcnt}</td>
										    </tr>
							    	</c:when>
							    	<c:otherwise>
							    		<!-- board_show 컬럼이 n일때(삭제된 상태일때) -->
							    		<tr>
								    		<td colspan="5" align="left">
								    			<c:if test="${row.cnt > 0}">
								    				<a href="${path}/board/view.do?bno=${row.bno}&curPage=${map.pager.curPage}&search_option=${map.search_option}&keyword=${map.keyword}">
								    				삭제된 게시물입니다.
								    				<!-- ** 댓글이 있으면 게시글 이름 옆에 출력하기 -->
								    				<span style="color: orange;">(${row.cnt})</span>
								    				</a>
								    			</c:if>
								    			<c:if test="${row.cnt == 0}">
								    				삭제된 게시물입니다. 
								    			</c:if>
								    		</td>
								    	</tr>
							    	</c:otherwise>
							    </c:choose>
							</c:forEach>       
		                </tbody>
		            </table>
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
			                        <li><strong><span style="color: orange; padding: 6px 12px;">${num}</span></strong></li>
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