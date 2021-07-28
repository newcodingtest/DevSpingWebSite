<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
body{padding-top:30px;}

.glyphicon {  margin-bottom: 10px;margin-right: 10px;}

small {
display: block;
line-height: 1.428571429;
color: #999;
}

.uploadResult ul li img {
	width: 380px;
	height: 500px;
}
@import url('https://fonts.googleapis.com/css?family=Josefin+Sans&display=swap');

*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  list-style: none;
  font-family: 'Josefin Sans', sans-serif;
}

body{
   background-color: #f3f3f3;
}

.wrapper{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  width: 450px;
  display: flex;
  box-shadow: 0 1px 20px 0 rgba(69,90,100,.08);
}

.wrapper .left{
  width: 35%;
  background: linear-gradient(to right,#01a9ac,#01dbdf);
  padding: 30px 25px;
  border-top-left-radius: 5px;
  border-bottom-left-radius: 5px;
  text-align: center;
  color: #fff;
}

.wrapper .left img{
  border-radius: 5px;
  margin-bottom: 10px;
}

.wrapper .left h4{
  margin-bottom: 10px;
}

.wrapper .left p{
  font-size: 12px;
}

.wrapper .right{
  width: 65%;
  background: #fff;
  padding: 30px 25px;
  border-top-right-radius: 5px;
  border-bottom-right-radius: 5px;
}

.wrapper .right .info,
.wrapper .right .projects{
  margin-bottom: 25px;
}

.wrapper .right .info h3,
.wrapper .right .projects h3{
    margin-bottom: 15px;
    padding-bottom: 5px;
    border-bottom: 1px solid #e0e0e0;
    color: #353c4e;
  text-transform: uppercase;
  letter-spacing: 5px;
}

.wrapper .right .info_data,
.wrapper .right .projects_data{
  display: flex;
  justify-content: space-between;
}

.wrapper .right .info_data .data,
.wrapper .right .projects_data .data{
  width: 45%;
}

.wrapper .right .info_data .data h4,
.wrapper .right .projects_data .data h4{
    color: #353c4e;
    margin-bottom: 5px;
}

.wrapper .right .info_data .data p,
.wrapper .right .projects_data .data p{
  font-size: 13px;
  margin-bottom: 10px;
  color: #919aa3;
}

.wrapper .social_media ul{
  display: flex;
}

.wrapper .social_media ul li{
  width: 45px;
  height: 45px;
  background: linear-gradient(to right,#01a9ac,#01dbdf);
  margin-right: 10px;
  border-radius: 5px;
  text-align: center;
  line-height: 45px;
}

.wrapper .social_media ul li a{
  color :#fff;
  display: block;
  font-size: 18px;
}

</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>사람을 만나는 방법, HowMeet</title>
</head>
<style>
.form-group {
  width:200px;
  height:100px;
  font-size:15px;
  align: center;
}

.box {
    width: 150px;
    height: 150px; 
    border-radius: 70%;
    overflow: hidden;
    align: center;
}
img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
</style>
<body>
<div class="info">
	<form role="form" action="/member/modify" method="post" id="actionForm">
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div>
<!-- ============================================ 프로필 사진 나오는 부분 ============================================ -->
			<div class="box" style="background: #bdbdbd;">
   				
			</div>
		</div>
		
		<div>
			<label for="ex_file">프로필 사진 바꾸기</label> 
			<input name="uploadFile" type="file" accept="image/gif, image/jpeg, image/png"/>
		</div>
	
			<div class="form-group" >
			<label for="name">아이디</label>
			<input type="text" class="form-control w200" 
				name="userid" placeholder="<c:out value="${user.userid}" />" value='<sec:authentication property="principal.username" />' readonly="readonly" />
		</div>
		
		<div class="form-group">
			<label for="website">이름</label>
			<input type="text" class="form-control w200"
				name="username" required="required" />
		</div>
		
		<div class="form-group">
			<label for="website">소개</label>
			<input type="text" class="form-control w200"
				name="introduce" required="required" />
		</div>
		</form>
	
		<button type="submit" id='update' class="btn btn-default" data-oper="update">완료</button>
		<button type="button" id="reject" class="btn btn-default" >취소</button>
</div>
</body>
</html>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<script>
//업로드 결과를 화면에 처리하는 함수=================================================================================
function showUploadResult(uploadResultArr){
	if(!uploadResultArr || uploadResultArr.length == 0){
		return;
	}
	var uploadUL = $(".box");
	var str ="";
	$(uploadResultArr).each(function(i,obj){

		if(obj.image){
			var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);
			str += "<li data-path='"+obj.uploadPath+"'";
			str +=" data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'"
			str +" ><div>";
			str += "<span> "+ obj.fileName+"</span>";
			str += "<button type='button' data-file=\'"+fileCallPath+"\' " //button x표시
			str += "data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'>x</i></button><br>";
			str += "<img src='/display?fileName="+fileCallPath+"'>";
			str += "</div>";
			str +"</li>";
			
		}else if(!obj.image){
			return false;
				
			}
					

	uploadUL.append(str);
});	
}//End showUploadResult		

$(document).ready(function(){
	
	var formObj = $("form[role='form']");
	
//완료 버튼 클릭시 수정 진행==========================================================
			$("button[type='submit']").on("click", function(e){
				e.preventDefault();
			//button 의 data-oper 값 가져오기
				console.log("완료 버튼 클릭");
				var str="";
				
				$(".box li").each(function(i,obj){
					var obj = $(obj);
					console.dir("obj");
					console.log(obj.data("filename"));
					//5.수정된 파일정보 히든값으로 넘기기=====================================
				       str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+obj.data("filename")+"'>";
				          str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+obj.data("uuid")+"'>";
				          str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+obj.data("path")+"'>";
				          str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+ obj.data("type")+"'>";
				});
				console.log(str);
				formObj.append(str).submit();
			
		});
	
	
	
	//수정 취소시 리스트로 가게된다.
	$("#reject").on("click", function(e){
		e.preventDefault();
		self.location="/board/list";
	});
	
	
	//업로드 파일 타입 제한==============================================================	
	var regex = /(.*?)\.(jpg|jpeg|png|gif|bmp|pdf)$/;
	var maxSize = 5242880; // 5mb
		
		//파일 타입,용량 확인 메서드======================
		function checkExtension(fileName, fileSize){
			if(fileSize >= maxSize){
				alert("파일 용량 초과");
				return false;
			}
			if(!regex.test(fileName)){
				alert("업로드 될수 없는 타입입니다.");
				return;
			}
			return true;
			}
	
		//파일타입의 변경유무가 있을시(파일을 업로드시)========================================================================	
		//csrf토큰===================================
		var csrfHeaderName ="${_csrf.headerName}";
		var csrfTokenValue ="${_csrf.token}"; 
		
		$("input[type='file']").change(function(e){
			
		/* 	//파일 변경 시마다 이미지도 갱신
			if(this.files && this.files[0]){
				var reader = new FileReader;
				reader.onload = function(data){
					$(".box img").attr("src", data.target.result).width(500);
				}
				reader.readAsDataURL(this.files[0]);
			} */
			
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
			//var filess=e.target.files;
			//var filesArr=Array.prototype.slice.call(filess);
			
		/* 	filesArr.forEach(function(f){
				if(!f.type.match("image.*")){
					alert("이미지 타입만 가능합니다");
					return;
				}
			}); */
			
			for(var i=0; i<files.length; i++){
				//확인 메서드에서 false가 나오면 안됨
				if(!checkExtension(files[i].name, files[i].size)){
					return false;
				}
				//append:요소추가
				formData.append("uploadFile",files[i]);
			}
			//ajax로 서버(uploadAjaxAction)에 전송
			$.ajax({
				url: '/uploadAjaxAction',
				processData: false, 
				contentType: false,
				//ajax로 csrf토큰 전송
				beforeSend: function(xhr){
					xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
				},
				data: formData,
				type: 'POST',
				dataType: 'json',
				success: function(result){
					console.log(result)
					showUploadResult(result);
				}
			}); // End ajax
			
		}); //End change

			//파일 삭제===========================================================================	
			$(".box").on("click", "button", function(e){
				console.log("삭제 파일");
				
				var targetFile = $(this).data("file");
				var type = $(this).data("type");
				
				var targetLi = $(this).closest("li");
				
				$.ajax({
					url: '/deleteFile',
					data: {fileName: targetFile, type: type},
					//ajax로 csrf토큰 전송
					beforeSend: function(xhr){
						xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
					},
					dataType: 'text',
					type: 'post',
					success: function(result){
						alert(result);
						targetLi.remove();
					}
				});//ajax
			});//End uploadResult click	
			

});//End document
</script>