<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">

	$(function(){
		//수정버튼 클릭
		$("#btnUpdate").click(function(){
			document.form1.action = "/member/update.do";
			document.form1.submit();
		});
		
		//삭제 버튼 클릭
		$("#btnDelete").click(function(){
			if(confirm("삭제하시겠습니까?")){
				document.form1.action = "/member/delete.do";
				document.form1.submit();
										}
	 	});
	});	
	
</script>	
	

</head>
<body>
	<h2>회원정보</h2>
	<form name="form1" method="post">
		<table border="1" width="400px">
			<tr>
				<td>아이디</td>
				<td><input name="userid" value="${dto.userid}" readonly/></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="passwd"/></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input name="name" value="${dto.name}"/></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input name="email" value="${dto.email}"/></td>
			</tr>
			<tr>
				<td>회원가입일자</td>
				<td><fmt:formatDate value="${dto.join_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="수정" id="btnUpdate">
					<input type="button" value="삭제" id="btnDelete">
					<div style="color: red">${message}</div>
				</td>
			</tr>	
		</table>
	</form>
</body>

</html>
