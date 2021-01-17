<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="../include/header.jsp" %>
<title>board</title>
<link rel="stylesheet" href="${path}/resources/css/member_list_style.css">
</head>
<body>
<%@ include file="../include/menu.jsp" %>
    <!--  main 부분  -->
    <main class="main">
        <h2 class="main title">회원관리</h2>
        
        <div class="search-form">
            <h3 class="hidden">회원관리 검색폼</h3>
            <form class="table-form">
                <fieldset>
                    <legend class="hidden">회원관리 검색 필드</legend>
                    <label class="hidden">검색분류</label>
                    <select name="f">
                        <option  value="login_name">이름</option>
                        <option  value="login_id">아이디</option>
                    </select> 
                    <label class="hidden">검색어</label>
                    <input type="text" name="q" value=""/>
                    <input class="btn btn-search" type="submit" value="검색" />
                </fieldset>
            </form>
        </div>
        
        <div class="notice">
            <h3 class="hidden">회원 목록</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th class="w60">번호</th>
                        <th class="w110">아이디</th>
                        <th class="w110">이름</th>
                        <th class="expand">이메일</th>
                        <th class="w150">가입일자</th>
                    </tr>
                </thead>
                <tbody>
		        <!-- MemberController의 model.addAttribute("items", list);와 변수명 같게 하기 -->
				<c:forEach var="row" items="${items}"> 
					<tr>
						<td>${row.login_id}</td>
						<td>${row.login_id}</td>
						<td><a href="/member/view.do?userid=${row.login_id}">${row.login_name}</a></td>
						<td>${row.email}</td>
						<td><fmt:formatDate value="${row.join_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
				</c:forEach>
                
                </tbody>
            </table>
        </div>
        
        <div class="pager">	
        <!-- 페이지 네비게이션 출력 -->
			<c:if test="${map.pager.curBlock > 1}">
				<a href="javascript:list('1')">[처음]</a>
			</c:if>
			<c:if test="${map.pager.curBlock > 1}">
				<a href="javascript:list('${map.pager.prevPage}')">[이전]</a>
			</c:if>
			
			<ul class="page_list">	
				 <c:forEach var="num" 
	                begin="${map.pager.blockBegin}"
	                end="${map.pager.blockEnd}">
	                <c:choose>
	                    <c:when test="${num == map.pager.curPage}">
	                    
	                    <!-- 현재 페이지인 경우 하이퍼링크 제거 -->
	                    <!-- 현재 페이지인 경우에는 링크를 빼고 빨간색으로 처리를 한다. -->
	                        <span style="color:red;">${num}</span>&nbsp;
	                    </c:when>
	                    <c:otherwise>
	                        <a href="javascript:list('${num}')">${num}</a>&nbsp;
	                    </c:otherwise>
	                    
	                </c:choose>
	            </c:forEach>
            </ul>
            
            <c:if test="${map.pager.curBlock <= map.pager.totBlock}">
                <a href="javascript:list('${map.pager.nextPage}')" >다음</a>
            </c:if> <!-- 현재 페이지블록이 총 페이지블록보다 작으면 다음으로 갈 수있도록 링크를 추가 -->
            
            <c:if test="${map.pager.curPage <= map.pager.totPage}">
                <a href="javascript:list('${map.pager.totPage}')" >끝</a>
            </c:if> <!-- 현재 페이지블록이 총 페이지블록보다 작거나 같으면 끝으로 갈 수 있도록 링크를 추가함-->
        </div>

    </main>
</body>
</html>