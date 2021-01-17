<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="../include/header.jsp" %>
<title>join</title>
<link rel="stylesheet" href="${path}/resources/css/join_style.css">
<script src="${path}/resources/js/join.js" defer></script>
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
                    <form name="joinForm" class="joinForm" method="POST">
                        <div class="input-group">
	                        <label for="loginId">아이디</label>
							<input autocomplete="off" type="text" id="login_id" name="login_id" required="required" autofocus="autofocus" maxlength="30" placeholder="4~10자 영어 소문자+숫자 조합" />                        
						</div>
						
                        <div class="input-group">
	                        <label for="loginPasswd">비밀번호</label>
							<input autocomplete="off" type="password" id="login_passwd" name="login_passwd" required="required" maxlength="30" placeholder="6~12자 영어 소문자+숫자 조합" />
						</div>
                        
                        <div class="input-group">
                        	<label for="loginPasswdConfirm">비밀번호 재확인</label>
							<input autocomplete="off" type="password" id="login_passwd_confirm" name="login_passwd_confirm" required="required" maxlength="30" placeholder="비밀번호 재확인" />
                        </div>
                        
                        <div class="input-group">
                        	<label for="loginName">이름</label>
							<input autocomplete="off" type="text" id="login_name" name="login_name" required="required" maxlength="30" placeholder="이름" />
                        </div>
                        
                        <div class="input-group">
                        	<label for="e-mail">이메일</label>
							<input autocomplete="off" type="email" id="email" name="email" required="required" maxlength="50" placeholder="이메일" />
                        </div>
                        
                        <div class="input-group">
                            <button type="button" id="btnJoin" value="회원가입">Join</button>
                        </div>
                    </form>
                </section>
                
            </div>
        </section>
    </main>    
    
</body>
</html>