<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script>
// 3. 댓글 수정
$("#btnReplyUpdate").click(function(){
    var detailReplytext = $("#detailReplytext").val();
    $.ajax({
        type: "put",
        url: "${path}/reply/update/${dto.rno}",
        headers: { // 기본값 text/html을 json으로 변경
            "Content-Type":"application/json"
        },
        data: JSON.stringify({ // 데이터를 json형태로 변환
            replytext : detailReplytext
        }),
        dataType: "text",
        success: function(result){
            if(result == "success"){
                $("#modifyReply").css("visibility", "hidden");
                listReply("1"); // 댓글 목록 갱신
            }
        }
    });
});
    
// 4. 댓글 상세화면 닫기
$("#btnReplyClose").click(function(){
    $("#modifyReply").css("visibility", "hidden");
});

// 5. 댓글 삭제
$("#btnReplyDelete").click(function(){
    if(confirm("삭제하시겠습니까?")){
        $.ajax({
            type: "delete",
            url: "${path}/reply/delete/${dto.rno}",
            success: function(result){
                if(result == "success"){
                    alert("삭제되었습니다.");
                    $("#modifyReply").css("visibility", "hidden");
                    listReply("1");
                }
            }
        });
    }
});
</script>
</head>
<body>
    댓글 번호 : ${dto.rno}<br>
    <textarea id="detailReplytext" style="width:100%; height:80px;">${dto.replytext}</textarea>
    <div style="text-align: center;">
        <!-- 본인 댓글만 수정, 삭제가 가능하도록 처리 -->
        <c:if test="${sessionScope.login_id == dto.replyer || sessionScope.admin_login_id == dto.replyer}">
            <button type="button" id="btnReplyUpdate" >수정</button>
            <button type="button" id="btnReplyDelete" >삭제</button>
        </c:if>
        <button type="button" id="btnReplyClose" >닫기</button>
    </div>
</body>
</html>