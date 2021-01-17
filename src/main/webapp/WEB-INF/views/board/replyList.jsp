<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>replyList</title>
</head>
<body>
<table style="width: 100%" method= "POST" >
	<c:forEach var="row" items="${list}">
		<tr style="font-size: 13px; color:gray;">
			<td>
				${row.replyer}
				(<fmt:formatDate value="${row.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/>)
				<br>
				${row.replytext}
				<!-- 본인의 댓글만 수정, 삭제가 가능하도록 처리 -->
				<c:if test="${sessionScope.login_id == row.replyer || sessionScope.admin_login_id == row.replyer}">
					<input type="button" id="btnModify" value="수정" onclick="showReplyModify('${row.rno}')">
				</c:if>
				<hr>
			</td>
		</tr>
	</c:forEach>
</table>

<!-- 페이지 네비게이션 출력 -->
<div class="pager">
	<div class="btn-prev">
		<c:if test="${pager.curBlock > 1}">
			<a href="javascript:listReply('${pager.prevPage}')"><i class="fas fa-angle-left"></i></a>
		</c:if>
	</div>	

	<ul class="pager_number">
		<c:forEach var="num" 
	        begin="${pager.blockBegin}"
	        end="${pager.blockEnd}">
	        <c:choose>
	            <c:when test="${num == pager.curPage}">
	            
	            <!-- 현재 페이지인 경우 하이퍼링크 제거 -->
	            <!-- 현재 페이지인 경우에는 링크를 빼고 빨간색으로 처리를 한다. -->
	                <li><span style="color: orange; padding: 6px 12px;">${num}</span></li>
	            </c:when>
	            <c:otherwise>
	                <li><a href="javascript:listReply('${num}')">${num}</a></li>
	            </c:otherwise>
	        </c:choose>
		</c:forEach>
   </ul>
	<div class="btn-next">
		<c:if test="${pager.curBlock <= pager.totBlock}">
			<a href="javascript:listReply('${pager.nextPage}')"><i class="fas fa-angle-right"></i></a>
		</c:if>
	</div>

</div>

<!-- 댓글 수정 영역 -->
<div id="modifyReply"></div>
</body>
</html>