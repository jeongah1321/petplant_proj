<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="../include/header.jsp" %>
<title>findLoginId_result</title>
<link rel="stylesheet" href="${path}/resources/css/findLoginId_style.css">
<script>
$(function(){
	$("#btnLogin_page").click(function(){
        document.findLoginId_resultForm.action=" ${path}/member/login.do";
        document.findLoginId_resultForm.submit();
    });
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
    <!--  main 부분  -->
    <main>
        <section class="wrapper">
            <div class="content">
               <header>
                   <h1>Pet Plant</h1>
               </header>
               
               <section>
                    <form name="findLoginId_resultForm" class="findLoginId_resultForm" method="POST">
                        <div class="text-group">
                        	<p>
                        		<strong>${map.login_name}</strong>님의 아이디는 <br>
                        		<big><strong>${map.login_id}</strong></big> 입니다.
                        	</p>
	                    </div>
						
                        <div class="input-group">
                            <button type="button" name="btnLogin_page" id="btnLogin_page" value="로그인 페이지이동" ><strong>Login 페이지로 이동</strong></button>
                        </div>
                    </form>
                </section>
            </div>
        </section>
    </main>    
    
</body>
</html>