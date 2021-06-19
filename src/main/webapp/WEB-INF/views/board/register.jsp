<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@include file="../includes/header.jsp"%>
 <div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">글 쓰기 페이지</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
	<style>
.uploadResult {
	width: 100%;
	background-color: gray;
}

.uploadResult ul {
	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li {
	list-style: none;
	padding: 10px;
}

.uploadResult ul li img {
	width: 100px;
}
</style>

<style>
.bigPictureWrapper {
  position: absolute;
  display: none;
  justify-content: center;
  align-items: center;
  top:0%;
  width:100%;
  height:100%;
  background-color: gray; 
  z-index: 100;
}

.bigPicture {
  position: relative;
  display:flex;
  justify-content: center;
  align-items: center;
}
</style>

<div class="row">
  <div class="col-lg-12">
    <div class="panel panel-default">

      <div class="panel-heading">글 쓰기 페이지</div>
      <!-- /.panel-heading -->
      <div class="panel-body">
<!--form=================================================================== -->      
		<form role="form" id="actionForm" action="/board/register" method="post">
      	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      	
        <div class="form-group">
          <label>Title</label> <input class="form-control" name='title'>
             
        </div>

        <div class="form-group">
          <label>Text area</label>
          <textarea class="summernote" style="width: 100%; height:412px;" class="form-control" rows="3" name='content'></textarea>
        </div>
<!--sec 로그인된 사용자이름 자동 작성=================================================================== -->
        <div class="form-group">
          <label>Writer</label> <input class="form-control" name='writer' value='<sec:authentication property="principal.username"/>' readonly="readonly">
        </div>
	</form>        
        <button type="submit" class="update">글 등록</button>
	    <button type="submit" class="list"><a herf="/board/list">목록으로</a></button>

            </div>
      <!--  end panel-body -->

    </div>
    <!--  end panel-body -->
  </div>
  <!-- end panel -->
</div>
<!-- /.row -->  
<!-- 첨부파일 부분 시작========================================================================= -->
					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">

								<div class="panel-heading">파일첨부</div>
								<!-- /.panel-heading -->
								<div class="panel-body">
									<div class="form-group uploadDiv">
										<input type="file" name='uploadFile' multiple>
									</div>
<!-- 첨부파일 사진 나오는 부분 uploadResult ====================================================================== -->

									<div class='uploadResult'>
										<ul>

										</ul>
									</div>


								</div>
								<!--  end panel-body -->

							</div>
							<!--  end panel-body -->
						</div>
						<!-- end panel -->
					</div>
					<!-- /.row -->

<!-- 첨부파일 부분 끝========================================================================= -->    


						<div class="card mb-2">
							<div class="card-header bg-light">
								<i class="fa fa-comment fa"></i> 
							</div>
							<div class="card-body">
								<ul class="list-group list-group-flush">
									<li class="list-group-item">
										<div class="form-inline mb-2">
											<label for="replyId"><i
												class="fa fa-user-circle-o fa-2x"></i></label> <input type="text"
												class="form-control ml-2" placeholder="아이디"
												id="replyId"> <label for="replyPassword"
												class="ml-4"><i class="fa fa-unlock-alt fa-2x"></i></label>
											<input type="password" class="form-control ml-2"
												placeholder="비밀번호" id="replyPassword">
										</div> <textarea class="form-control"
											id="exampleFormControlTextarea1" rows="3"></textarea>
										<button type="button" class="btn btn-dark mt-3"
											onClick="javascript:addReply();">등록</button>
										
									</li>
								</ul>
							</div>
							
							
							
						</div>

					</div>

<input id="bno" type="hidden" value="<c:out value='${board.bno}' />" />


					<%@include file="../includes/footer.jsp"%>
					

	
<script>

//업로드 결과를 화면에 처리하는 함수=================================================================================
	function showUploadResult(uploadResultArr){
		if(!uploadResultArr || uploadResultArr.length == 0){
			return;
		}
		var uploadUL = $(".uploadResult ul");
		var str ="";
		$(uploadResultArr).each(function(i,obj){
			
			//이미지 타입 확인
			if(obj.image){
				var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);
				str += "<li data-path='"+obj.uploadPath+"'";
				str +=" data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'"
				str +" ><div>";
				str += "<span> "+ obj.fileName+"</span>";
				str += "<button type='button' data-file=\'"+fileCallPath+"\' " //button x표시
				str += "data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
				str += "<img src='/display?fileName="+fileCallPath+"'>";
				str += "</div>";
				str +"</li>";
			}else if(!obj.image){
				var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);			      
			    var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
			      
				str += "<li "
				str += "data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"' ><div>";
				str += "<span> "+ obj.fileName+"</span>";
				str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' " //button x표시
				str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
				str += "<img src='/resources/img/attach.png'></a>";
				str += "</div>";
				str +"</li>";
			}
		uploadUL.append(str);
	});//End showUploadResult
	}



$(document).ready(function(){
	
//목록으로 이동===================================================================
	$(".list").on("click",function(e){
		e.preventDefault();
		formObj.attr("action","/board/list");
		formObj.attr("method","get");
		formObj.submit();
		//self.location ="/board/list";
	});
	
//게시물 등록시 히든태그로 파일 정보까지 같이 전송========================================	
var formObj = $("form[role='form']");
	
	$("button[type='submit']").on("click", function(e){
		e.preventDefault();
		console.log("submit clicked");
		var str ="";
		
		$(".uploadResult ul li").each(function(i, obj){
		      
		      var jobj = $(obj);
		      
		      console.dir(jobj);
		      console.log("-------------------------");
		      console.log(jobj.data("filename"));
		      
		      
		      str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
		      str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
		      str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
		      str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+ jobj.data("type")+"'>";
		      
		    });
		console.log(str);
		formObj.append(str).submit();
		
	});

	
//업로드 파일 타입 제한==============================================================	
var regex = new RegExp("(.*?)\.exe|sh|zip|alz$");
var maxSize = 5242880; // 5mb
	
	//파일 타입,용량 확인 메서드======================
	function checkExtension(fileName, fileSize){
		if(fileSize >= maxSize){
			alert("파일 용량 초과");
			return false;
		}
		if(regex.test(fileName)){
			alert("업로드 될수 없는 타입입니다.");
			return false;
		}
		return true;
		}
		
//파일타입의 변경유무가 있을시(파일을 업로드시)========================================================================	
	//csrf토큰===================================
	var csrfHeaderName ="${_csrf.headerName}";
	var csrfTokenValue ="${_csrf.token}"; 
	
	$("input[type='file']").change(function(e){
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;
		
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
	$(".uploadResult").on("click", "button", function(e){
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
	
	//첨부파일 이미지 출력하는 함수========================================================
/* 		function showImage(fileCallPath){
		  
		  //alert(fileCallPath);
		
		  $(".bigPictureWrapper").css("display","flex").show();
		  
		  $(".bigPicture")
		  .html("<img src='/display?fileName="+ encodeURI(fileCallPath)+"'>")
		  .animate({width:'100%', height: '100%'}, 1000);

		}
		
		$(".bigPictureWrapper").on("click", function(e){
		  $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
	 	  setTimeout(() => {
		    $(this).hide();
		  }, 1000); 
		  
		  setTimeout(function() {
			  $(".bigPicture").hide();
		  }, 1000);
		  
		});  */
	
});
</script>
      