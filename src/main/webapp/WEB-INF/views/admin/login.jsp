<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>login</title>
<link rel="stylesheet" href="${path}/resources/css/login_style.css">
<%@ include file="../include/header.jsp" %>
<script>
$(function(){
	$("#btnLogin").click(function(){ //id=btnLogin을 클릭하면
        var login_id=$("#login_id").val().trim(); //id=login_id의 value
        var login_passwd=$("#login_passwd").val().trim(); // trim 앞, 뒤 공백제거 
        if(login_id==""){
            alert("로그인 아이디를 입력하세요.");
            $("#login_id").focus();
            /* return; */
        }
        if(login_passwd==""){
            alert("로그인 비밀번호를 입력하세요.");
            $("#login_passwd").focus();
            /* return; */
        }
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
                   <h1>Administrator Login</h1>
               </header>
               
               <section>
                    <form name="loginForm" class="loginForm" method="POST"  action="/admin/login.do">
                        <div class="input-group">
	                        <label for="loginId">아이디</label>
							<input autocomplete="off" type="text" id="login_id" name="login_id" required="required" autofocus="autofocus" maxlength="30" placeholder="아이디 입력" />                        
						</div>
                        
                        <div class="input-group">
	                        <label for="password">비밀번호</label>
							<input autocomplete="off" type="password" id="login_passwd" name="login_passwd" required="required" maxlength="30" placeholder="6~12자 영어 소문자+숫자 조합" />
						</div>
						
                        <div class="input-group">
                        	<c:if test="${param.message == 'nologin' }">
				                <div style="color:red;" align="center">
				                    <small>먼저 로그인하세요.</small>
				                </div>
				            </c:if>
				            <c:if test="${message == 'error' }">
				                <div style="color:red;" align="center">
				                    <small>아이디 또는 비밀번호가 일치하지 않습니다.</small>
				                </div>
				            </c:if>
				            <c:if test="${message == 'logout' }">
				                <div style="color:red;" align="center">
				                    <small>로그아웃 되었습니다.</small>
				                </div>
                            </c:if>
                            
                            <button type="submit" id="btnLogin" value="로그인"><strong>Login</strong></button>
				            
                        </div>
                    </form>
                </section>

            </div>
        </section>
    </main>    
    
</body>
</html>