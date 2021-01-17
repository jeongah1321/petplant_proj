<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="../include/header.jsp" %>
<title>findLoginPasswd</title>
<link rel="stylesheet" href="${path}/resources/css/findLoginPasswd_style.css">
<script>
$(function(){
	$("#btnfindLoginPasswd").click(function(){
		var login_id=$("#login_id").val().trim();
        var login_name=$("#login_name").val().trim();
        var email=$("#email").val().trim(); // trim 앞, 뒤 공백제거 
        
        if(login_id==""){
            alert("로그인 아이디를 입력하세요.");
            $("#login_id").focus();
            return;
        }
        if(login_name==""){
            alert("이름을 입력하세요.");
            $("#login_name").focus();
            return;
        }
        if(email==""){
            alert("이메일을 입력하세요.");
            $("#email").focus();
            return;
        }
        document.findLoginPasswdForm.action="${path}/member/findLoginPasswd.do";
        document.findLoginPasswdForm.submit();
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
					<form action="" name="findLoginPasswdForm" class="findLoginPasswdForm" method="POST">
						<div class="input-group">
	                        <label for="loginId">아이디</label>
							<input autocomplete="off" type="text" id="login_id" name="login_id" required="required" autofocus="autofocus" maxlength="30" placeholder="아이디" />
						</div>
						
						<div class="input-group">
	                        <label for="loginName">이름</label>
							<input autocomplete="off" type="text" id="login_name" name="login_name" required="required" autofocus="autofocus" maxlength="30" placeholder="이름" />
						</div>
                        
                        <div class="input-group">
	                        <label for="e-mail">이메일</label>
							<input autocomplete="off" type="email" id="email" name="email" required="required" maxlength="30" placeholder="이메일" />
						</div>
						
                        <div class="input-group">
                        	<!-- form 태그 안에 있는 button태그는 type이 submit으로 인식하여 submit 이벤트가 발생하므로
                            button태그는 type을 button으로 바꿔줘야 설정한 jquery 이벤트가 작동 -->
                            <c:if test="${message == 'error' }">
				                <div style="color:red;" align="center">
				                    <small>입력하신 정보가 일치하지 않습니다.</small>
				                </div>
					        </c:if>
                        
                            <button type="button" id="btnfindLoginPasswd" value="비밀번호 찾기"><strong>비밀번호 찾기</strong></button>
                            
                        </div>
                    </form>
                </section>
                
            </div>
        </section>
    </main>    
    
</body>
</html>