<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="../include/header.jsp" %>
<title>findLoginId</title>
<link rel="stylesheet" href="${path}/resources/css/findLoginId_style.css">
<script>
$(function(){
	$("#btnfindLoginId").click(function(){ 
        var login_name=$("#login_name").val().trim();
        var email=$("#email").val().trim(); // trim 앞, 뒤 공백제거 
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
        document.findLoginIdForm.action="${path}/member/findLoginId.do";
        /*document.findLoginIdForm.submit();  */
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
					<form action="" name="findLoginIdForm" class="findLoginIdForm" method="POST">                       
					 	<div class="input-group">
	                        <label for="loginName">이름</label>
							<input autocomplete="off" type="text" id="login_name" name="login_name" required="required" autofocus="autofocus" maxlength="30" placeholder="이름" />
						</div>
                        
                        <div class="input-group">
	                        <label for="e-mail">이메일</label>
							<input autocomplete="off" type="email" id="email" name="email" required="required" maxlength="30" placeholder="이메일" />
						</div>
						
                        <div class="input-group">
                        	<c:if test="${message == 'error' }">
				                <div style="color:red;" align="center">
				                    <small>입력하신 정보가 일치하지 않습니다.</small>
				                </div>
				            </c:if>
                        
                            <button type="submit" id="btnfindLoginId" value="아이디 찾기"><strong>아이디 찾기</strong></button>
				            
                        </div>
                    </form>
                </section>
                
            </div>
        </section>
    </main>    
    
</body>
</html>