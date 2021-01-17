//회원가입 정보
$(function(){
	// 회원가입 버튼 클릭
	$("#btnJoin").click(function(){ 
        var login_id=$("#login_id").val().trim(); //id=login_id의 value
        var login_passwd=$("#login_passwd").val().trim(); // trim 앞, 뒤 공백제거 
        var login_passwd_confirm=$("#login_passwd_confirm").val().trim();
        var login_name=$("#login_name").val().trim();
        var email=$("#email").val().trim();
        
        // 아이디
        if(!login_id){
            alert("로그인 아이디를 입력하세요.");
            $("#login_id").focus();
            return;
        }
		var idCheck = /^(?=.*[a-z])(?=.*[0-9]).{4,10}$/; // 아이디의 영어+숫자로 유효성 검사(아이디의 수는 4~10)
		if(!idCheck.test(login_id)) {
			alert("아이디는 4~10자의 영어 소문자+숫자 조합으로 입력 가능 합니다.")
			$("#login_id").focus();
			return false;
		}
		if(!isNaN(login_id.substr(0,1))) { // 입력된 첫번째 문자가 숫자인지 검사하는 조건문
			alert("아이디는 숫자로 시작할 수 없습니다.");
			$("#login_id").focus();
			return;
		}

		// 비밀번호
        if(!login_passwd){
            alert("로그인 비밀번호를 입력하세요.");
            $("#login_passwd").focus();
            return;
        }
		var pwCheck = /^(?=.*[a-z])(?=.*[0-9]).{6,12}$/; // 비밀번호의 영어+숫자로 유효성 검사(비밀번호의 수는 6~12)
		if(!pwCheck.test(login_passwd)) {
			alert("비밀번호는 6~12자의 영어 소문자+숫자 조합으로 입력 가능 합니다.")
			login_passwd.focus();
			return false;
		}
        if(login_passwd!=login_passwd_confirm){ // 비밀번호 일치 확인
            alert("비밀번호가 일치하지 않습니다.");
            $("#login_passwd_confirm").focus();
            return;
        }
        
		// 이름
        if(!login_name){
            alert("이름을 입력하세요.");
            $("#login_name").focus();
            return;
        }
		var nameCheck = /^[가-힣]{2,8}|[a-zA-Z]{2,24}\s[a-zA-Z]{2,24}$/; // "|"를 사용 // 한글 또는 영문 사용하기(혼용X)
		if(!nameCheck.test(login_name)) {
			alert("이름은 한글 2~8자 또는 영어 2~24 이내로 입력 가능 합니다.");
			$("#login_name").focus();
			return;
		}

		
		// 이메일
        if(!email){
            alert("이메일을 입력하세요.");
            $("#email").focus();
            return;
        }
        if(! email_check(email) ) {// 이메일 유효성 검사
            alert('잘못된 형식의 이메일 주소입니다.');
            $("#email").focus();
            return false;
        }
        document.joinForm.action="/member/insert.do";
        document.joinForm.submit();
    });
});

function email_check(email) {
    var regex=/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return (email != '' && email != 'undefined' && regex.test(email));
}