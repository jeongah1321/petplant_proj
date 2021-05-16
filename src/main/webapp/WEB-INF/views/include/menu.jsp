<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 다른 페이지에 삽입하기 위한 페이지 -->
    <!-- header 부분 -->
    <header id="header">
       <!-- Nav container -->
        <nav class="navbar">

          <!-- Logo with text -->       
            <div class="navbar_logo">
                <i class="fas fa-seedling"></i>
                <a href="${path}/">Pet Plant</a>
            </div>

            <!-- Menu -->
            <ul class="navbar_menu">
               <li><a href="${path}/">Home</a></li>
               <li><a href="${path}/info">Information</a></li>
               <li><a href="${path}/product/list.do">Plants</a></li>
               <c:if  test="${sessionScope.resultDTO.login_id != null}">
               		<li><a href="${path}/like/list.do">Like</a></li>
               </c:if>
               <li><a href="${path}/gallery">Gallery</a></li>
               <li><a href="${path}/board/list.do">Board</a></li>
            </ul>

            <!-- Icons -->
            <ul class="navbar_icons">
            	<c:set var="login_id" value="${sessionScope.resultDTO.login_id}"/>
            	<c:set var="login_name" value="${sessionScope.resultDTO.login_name}"/>
            	<c:set var="admin_login_id" value="${sessionScope.aresultDTO.login_id}"/>            	
            	<c:choose>
            		<c:when test="${login_id == null && admin_login_id == null}">
		               <li><a href="${path}/member/login.do"><i class="far fa-user"></i></a></li>
		               <li><a href="${path}/admin/login.do"><i class="fas fa-user-cog"></i></a></li>
		            </c:when>
		            <c:otherwise>
		            	<c:if test="${login_id != null}">
							<strong>${login_name}</strong> (${login_id})님 로그인 &nbsp;
							<a href="${path}/member/logout.do"><i class="fas fa-sign-out-alt"></i></a>
				        </c:if>
		            	<c:if test="${admin_login_id != null}">
							<strong>${admin_login_id}</strong>님 로그인 &nbsp;
							<a href="${path}/admin/logout.do"><i class="fas fa-sign-out-alt"></i></a>
				        </c:if>						
					</c:otherwise>
	            </c:choose>
            </ul>

            <!-- Toggle button -->
            <a href="#" class="navbar_toggleBtn">
                <i class="fas fa-bars"></i>
            </a>
        </nav>
    </header>