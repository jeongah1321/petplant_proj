// 로그인
$(function(){
	$("#btnLogin").click(function(){ //id=btnLogin을 클릭하면
        var login_id=$("#login_id").val().trim(); //id=login_id의 value
        var login_passwd=$("#login_passwd").val().trim(); // trim 앞, 뒤 공백제거 
        if(login_id==""){
            alert("로그인 아이디를 입력하세요.");
            $("#login_id").focus();
        }
        if(login_passwd==""){
            alert("로그인 비밀번호를 입력하세요.");
            $("#login_passwd").focus();
        }
    });
});