<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <% response.setHeader("Cache-Control", "no-store"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setHeader("Expires", "0"); 
%>

<!-- path 변수 선언, request객체가 가진 쿼리 문자열 반환된 값 저장-->
<c:set var="path" value="${pageContext.request.contextPath}" />
 
<!--자바스크립트 라이브러리인 jquery는 소스로 등록해놔야 사용 가능 -->
<script src="http://code.jquery.com/jquery-3.3.1.js"></script>

<script src="${path}/resources/js/main.js" defer></script>

<!-- link : 외부 문서와 연결해주는 태그, 주로 css파일이나 폰트 관련 코드 연결해줌 -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">

<link rel="stylesheet" href="${path}/resources/css/style.css">