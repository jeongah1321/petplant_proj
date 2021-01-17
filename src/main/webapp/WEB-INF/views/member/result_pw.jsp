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
	$("#btnUpdateLoginPasswd").click(function(){ 
        var login_passwd=$("#login_passwd").val().trim(); // trim 앞, 뒤 공백제거 
        var login_passwd_confirm=$("#login_passwd_confirm").val().trim();

		// 비밀번호
        if(!login_passwd){ //if(login_passwd=="" && login_passwd==null)
            alert("로그인 비밀번호를 입력하세요.");
            $("#login_passwd").focus();
            return;
        }
		var pwCheck = /^(?=.*[a-z])(?=.*[0-9]).{6,12}$/; // 비밀번호의 영어+숫자로 유효성 검사(비밀번호의 수는 6~12)
		if(!pwCheck.test(login_passwd)) {
			alert("비밀번호는 6~12자의 영어 소문자+숫자 조합으로 입력 가능 합니다.")
			login_passwd.focus();
			return ;
		}
        if(login_passwd!=login_passwd_confirm){ // 비밀번호 일치 확인
            alert("비밀번호가 일치하지 않습니다.");
            $("#login_passwd_confirm").focus();
            return;
        }
        
        document.findLoginPasswd_resultForm.action="${path}/member/updateLoginPasswd.do";
        document.findLoginPasswd_resultForm.submit();
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
					<form action="" name="findLoginPasswd_resultForm" class="findLoginPasswdForm" method="POST">
						<input type="hidden" name="login_id" value="${login_id}"/>
                        <div class="input-group">
	                        <label for="loginPasswd">비밀번호</label>
							<input autocomplete="off" type="password" id="login_passwd" name="login_passwd" required="required" maxlength="30" placeholder="6~12자 영어 소문자+숫자 조합" />
						</div>
                        
                        <div class="input-group">
                        	<label for="loginPasswdConfirm">비밀번호 재확인</label>
							<input autocomplete="off" type="password" id="login_passwd_confirm" name="login_passwd_confirm" required="required" maxlength="30" placeholder="비밀번호 재확인" />
                        </div>
                        
                        <div class="input-group">
                            <button type="button" id="btnUpdateLoginPasswd" value="비밀번호 변경"><strong>비밀번호 변경</strong></button>
                        </div>
                    </form>
                </section>
                
            </div>
        </section>
    </main>    
    
</body>
</html>