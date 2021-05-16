//회원가입 정보

// 아이디
var idValidate = function(){
    var login_id=$('#login_id').val().trim(); //id=login_id의 value
	var idCheck = /^(?=.*[a-z])(?=.*[0-9]).{4,10}$/; // 아이디의 영어+숫자로 유효성 검사(아이디의 수는 4~10)
     
    if(!isNaN(login_id.substr(0,1))) { // 입력된 첫번째 문자가 숫자인지 검사하는 조건문
		$('#login_id').nextAll("*").remove();
		$('#login_id').after('<span class="fail">아이디는 숫자로 시작할 수 없습니다.</span>');
	} else if(!idCheck.test(login_id)) {
		$('#login_id').nextAll("*").remove();
		$('#login_id').after('<span class="fail">아이디는 4~10자의 영어 소문자+숫자 조합으로 입력 가능 합니다.</span>');
	} else{
		$('#login_id').nextAll("*").remove();
		$.ajax({
            url: '/member/idCheck.do',
            type: 'post',
            data: {'login_id' : login_id},
            dataType: 'text',
            success: function(result)
            {
                if(result==1){
                	 $('#login_id').nextAll("*").remove();
                    $('#login_id').after('<span class="fail">사용 중 이거나 휴면상태인 아이디입니다.</span>');
                }
                else{
                	 $('#login_id').nextAll("*").remove();
                    $('#login_id').after('<span class="success">사용 가능한 아이디입니다.</span>');  
                }
            }
        });
    }
};

// 비밀번호 
var pwValidate = function() {
	var login_passwd=$('#login_passwd').val().trim(); // trim 앞, 뒤 공백제거 
	var pwCheck = /^(?=.*[a-z])(?=.*[0-9]).{6,12}$/; // 비밀번호의 영어+숫자로 유효성 검사(비밀번호의 수는 6~12)
    
    if(!pwCheck.test(login_passwd)) {
		$('#login_passwd').nextAll("*").remove();
        $('#login_passwd').after('<span class="fail">비밀번호는 6~12자의 영어 소문자+숫자 조합으로 입력 가능 합니다.</span>');
	} else {
        $('#login_passwd').nextAll("*").remove();
        $('#login_passwd').after('<span class="success"> </span>');
    }
};

// 비밀번호 확인
var pwValidate_confirm = function(){
	var login_passwd=$('#login_passwd').val().trim(); // trim 앞, 뒤 공백제거 
    var login_passwd_confirm=$("#login_passwd_confirm").val().trim();     
	
    if(login_passwd!=login_passwd_confirm){ // 비밀번호 일치 확인
        $('#login_passwd_confirm').nextAll("*").remove();
        $('#login_passwd_confirm').after('<span class="fail">비밀번호가 일치하지 않습니다.</span>');
    } else {
       $('#login_passwd_confirm').nextAll("*").remove();
       $('#login_passwd_confirm').after('<span class="success"> </span>');    
    }
};
    
// 이름
var nameValidate = function() {
	var login_name=$('#login_name').val().trim();
	var nameCheck = /^[가-힣]{2,8}|[a-zA-Z]{2,24}\s[a-zA-Z]{2,24}$/; // "|"를 사용 // 한글 또는 영문 사용하기(혼용X)
	
	if(!nameCheck.test(login_name)) {
		$('#login_name').nextAll("*").remove();
        $('#login_name').after('<span class="fail">이름은 한글 2~8자 또는 영어 2~24 이내로 입력 가능 합니다.</span>');
	} else {
        $('#login_name').nextAll("*").remove();
        $('#login_name').after('<span class="success"> </span>'); 
    }
};
	
// 이메일
var emailValidate = function() {
    var email=$('#email').val().trim();
    
    if(! email_check(email) ) {// 이메일 유효성 검사
        $('#email').nextAll("*").remove();
        $('#email').after('<span class="fail">잘못된 형식의 이메일 주소입니다.</span>');
    } else {
        $('#email').nextAll("*").remove();
        $('#email').after('<span class="success"> </span>');
    }    
};

function email_check(email) {
    var regex=/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return (email != '' && email != 'undefined' && regex.test(email));
}

// 입력필드에 blur 이벤트가 실행될 경우 유효성 검사를 진행한다.

//id 유효성 검사 
$('#login_id').blur(idValidate);

//비밀번호 유효성 검사 
$('#login_passwd').blur(pwValidate);

//비밀번호 확인 유효성 검사 
$('#login_passwd_confirm').blur(pwValidate_confirm);

//이름 유효성 검사 
$('#login_name').blur(nameValidate);

//전화번호 유효성 검사
$('#email').blur(emailValidate);



//등록 버튼 클릭 시 fail클래스의 개수 확인
$('#btnJoin').click(function(e){
	//사용자가 입력 필드에 입력한 내용을 수정하고 등록버튼을 누를수있으니 버튼클릭시 검사를 다시 진행한다.
	//id 유효성 검사 
    idValidate();

    //비밀번호 유효성 검사 
    pwValidate();

    //비밀번호 확인 유효성 검사 
    pwValidate_confirm();

    //이름 유효성 검사 
    nameValidate();

    //이메일 유효성 검사
    emailValidate();

    if($('.fail').length != 0 ) {
    	//기본이벤트를 막는다. 
        e.preventDefault();
    }
   
    //fail span 태그가 하나도 없으면 통과 기본이벤트 진행 
    
}); 