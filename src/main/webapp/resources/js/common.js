/**
 파일 업로드에 관련하여 공통으로 사용할 javaScript함수들을 따로 모아놓은 것들이다. 
 <script type="text/javascript" src="${path}/resources/js/common.js"></script> 추가 필요
 */
//이미지 파일 여부 판단
function checkImageType(fileName){
    var pattern = RegExp(/^.+\.(gif|png|jpg|jpeg)$/i);
    return pattern.test(fileName);
}

//업로드 파일 정보
function getFileInfo(fullName) {
    var fileName, imgsrc, getLink, fileLink;
    //이미지 파일일 경우
    if(checkImageType(fullName)){
    	console.log("이미지");
        // 이미지 파일 경로(썸네일)
        imgsrc = "/upload/displayFile?fileName="+fullName;
        console.log(imgsrc+" [imgsrc: common.js 이미지 파일 경로]");

        // 업로드 파일명
        fileLink = fullName.substr(14);
        console.log(fileLink+" [fileLink: common.js 이미지 파일 업로드 파일명]");

        // 날짜별 디렉토리 추출
        var front = fullName.substr(0,12);
        console.log(front+" [front: common_js 이미지 파일 날짜별 디렉토리]");

        // s_를 제거한 업로드이미지 파일명
        var end = fullName.substr(14);
        console.log(end+" [end: common.js s_를 제거한 업로드 이미지 파일명]");

        // 원본이미지 파일 디렉토리
        getLink = "/upload/displayFile?fileName="+front+end;
        console.log(getLink+" [getLink: common.js 원본이미지 파일 디렉토리]");
    // 이미지 파일이 아닐경우
    } else { 
    	console.log("일반 파일");
        // UUID를 제외한 원본파일명
        fileLink = fullName.substr(12);
        console.log(fileLink+" [fileLink: common.js 일반 파일의 UUID를 제외한 원본파일명]");

        // 일반파일 디렉토리
        getLink = "/upload/displayFile?fileName="+fullName;
        console.log(getLink+" [getLink: common.js 일반 파일 디렉토리]");
    }

    // 목록에 출력할 원본파일명
    fileName = fileLink.substr(fileLink.indexOf("_")+1);
    console.log(fileName+" [fileName: common.js 목록에 출력할 원본파일명]");
    // { 키:값 } json 객체의 형태의 문자열을 리턴
    return {fileName:fileName, imgsrc:imgsrc, getLink:getLink, fullName:fullName};
}
