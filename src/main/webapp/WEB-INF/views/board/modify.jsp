<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@include file="../includes/header.jsp"%>

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
                    <h1 class="page-header">수정게시판</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           글작성
                        </div>
                        <div class="panel-body">
                            <div class="row">
					<div class="col-lg-6">
<!-- form 태그=============================================================================  -->
					<form id="actionForm" role="form" action="/board/modify" method="post">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="hidden" value='<c:out value="${board.bno }"/>' name="bno">
					
					<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
					<input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
					<input type='hidden' name='type' value='<c:out value="${cri.type }"/>'>
					<input type='hidden' name='keyword' value='<c:out value="${cri.keyword }"/>'> 
				
							<div class="form-group">
								<label>제목</label>
								 <input class="form-control" name="title"
									value="<c:out value="${board.title}" />" >

							</div>
							<div class="form-group">
								<label>내용</label> <input class="form-control" name="content"
									value="<c:out value="${board.content}" />" >
							</div>
							<div class="form-group">
								<label>작성자</label>
								 <input type="text" class="form-control" name="writer"
									value="<c:out value="${board.writer}" />" readonly >
							</div>
							<div class="form-group">
								<label>첨부파일</label>
								<input type="file" name='uploadFile' multiple="multiple">
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
							</div>
<!-- 첨부파일 사진 나오는 부분 uploadResult 끝====================================================================== -->
						<sec:authentication property="principal" var="pinfo"/>
						 <sec:authorize access="isAuthenticated()">
						  <c:if test="${pinfo.username eq board.writer}">		 
							<button class="update" data-oper="update">등록</button>
							<button class="remove" data-oper="remove">삭제</button>
						  </c:if>	
						 </sec:authorize>	
							<button class="list" data-oper="list">목록으로</button>
				 <br>
</form>
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
</script>
<script>
//===============================================================document.ready========================================
$(document).ready(function(){
	var pageNumTag = $("input[name='pageNum']").val();
	  console.log(pageNumTag);
	
	
	var actionForm = $("#actionForm");
	
/* 	//목록 페이지로 이동
	$(".list").on("click",function(e){
		e.preventDefault();
		self.location ="/board/list";
	}); */
	
//1.버튼의 data-oper속성을 통해 수정/삭제 판단 후 실행==========================================================
	$('button').on("click", function(e){
		e.preventDefault();
		//button 의 data-oper 값 가져오기
		var operation = $(this).data("oper");
		console.log(operation);
		
		//2.delete 같은 패턴으로 목록/수정등록 또한 가능========================================================
		if(operation === 'remove'){
			actionForm.attr("action","/board/remove");
		//3.list 이전에 봤던 페이지 정보 같이 넘기기==============================================================	
		}else if(operation === 'list'){
			
			actionForm.attr("action", "/board/list").attr("method","get");
		        
			  var pageNumTag = $("input[name='pageNum']").clone();
			  console.log(pageNumTag);
		      var amountTag = $("input[name='amount']").clone();
		      var keywordTag = $("input[name='keyword']").clone();
		      var typeTag = $("input[name='type']").clone();      
		      
		      actionForm.empty();
		      
		      actionForm.append(pageNumTag);
		      actionForm.append(amountTag);
		      actionForm.append(keywordTag);
		      actionForm.append(typeTag);
			
			
		//4.update 게시물 수정================================================================================
		}else if(operation == 'update'){
			
			console.log("수정 버튼 클릭");
			var str="";
			
			 $(".uploadResult ul li").each(function(i, obj){
		          
		          var jobj = $(obj);
		          
		          console.dir(jobj);
		          
		          str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
		          str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
		          str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
		          str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+ jobj.data("type")+"'>";
		          
		        });
			 actionForm.append(str).submit();
	        }
	    
		actionForm.submit();
		  });
	
	//즉시 실행함수 실행하여 해당글의 파일 가져오기================================================================	
	(function(){
		var bno = '<c:out value="${board.bno}" />';
		$.getJSON("/board/getAttachList",{bno:bno}, function(arr){
			console.log(arr);
			
			var str="";
			$(arr).each(function(i,attach){
				
				//첨부파일이 이미지타입 일 경우 썸네일 사진 가져오기
				if(attach.fileType){
					var fileCallPath = encodeURIComponent(attach.uploadPath + "/s_"+attach.uuid+"_"+attach.fileName);
					
					str +="<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"
					str +="<div><span>"+attach.fileName+"</span>"
					str +="<button type='button' data-file=\'"+fileCallPath+"\' data-type='image' class='btn btn-warning btn-circle'>"
					str +="<i class='fa fa-times'></i></button><br>"
					str +="<img src='/display?fileName="+fileCallPath+"'></div>";
					str +="</li>"
				}//첨부파일이 이미지타입이 아닐 경우 기본사진 가져오기(resources-img-attach.png)
				else{
					
					var fileCallPath = encodeURIComponent(attach.uploadPath + "/"+attach.uuid+"_"+attach.fileName);
					console.log("인코딩된 파일다운 주소 테스트:  " + fileCallPath);
					str +="<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"
					str +="<div><span>"+attach.fileName+"</span><br>";
					str +="<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' class='btn btn-warning btn-circle'>"
					str +="<i class='fa fa-times'></i></button><br>"
					str +="<img src='/resources/img/attach.png'></div>";
					str +="</li>"
				}
			});
			$(".uploadResult ul").html(str);
		});//end getjson
	})();//end function	
	
//파일의 삭제 버튼 누를시 화면 내에서만 첨부파일이 사라짐(아직까지 서버내에선 사라지지 않음): 수정을 완료하지 않고 브라우저를 강제종료했을때 수정내용이 적용되지 않도록 의도=====================================================
	$(".uploadResult").on("click","button", function(e){
		console.log("파일 삭제");
		
		if(confirm("첨부파일을 삭제 하시겠습니까?")){
			//closest 해당 요소들에 포함된 자식들까지 포함
			var targetLi = $(this).closest("li");
			targetLi.remove();
		}
	});
	
//수정시 업로드 파일 타입 제한==============================================================	
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
				data: formData,
				type: 'POST',
				beforeSend: function(xhr){
					xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
				},
				dataType: 'json',
				success: function(result){
					console.log(result)
					showUploadResult(result);
				}
			}); // End ajax
			
		}); //End change	
	
	
	
});
</script>
