<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
	/* 이미지 슬라이드 크기 조정 */
	 .item>img{
	   min-width:100%;
	   min-height:500px;
	   max-height:500px;
	 }
	  .post-info{
	    font-size: 17px;
	    line-height: 35px;
	  }
	  .p-contents{
	    margin-bottom: 40px;
	    border-bottom: 2px solid #CCCC;
	    padding-bottom: 20px;
	  }
	  .rep-contents{
	    line-height: 40px;
	  }
	  .rep-wrapper{
	    margin: 20px 0;
	  }
	  .arep-wrapper{
	    margin:20px 0px 200px 20px;
	    border:1px solid #cccc;
	    padding: 10px;
	  }
  	.uploadResult{
		width:100%;
		background-color:gray;
	}
	
	.uploadResult ul{
		display:flex;
		flex-flow:row;
		justify-cotnet: center;
		align-items:center;
	}
	
	.uploadResult ul li {
		list-style:none;
		padding:10px;
	}
	
	.uploadResult ul li img{
		cursor:pointer;
		width:200px;
	}
	
	.uploadResult ul li span{
		color:white;
	}
</style>
<script>
//csrf 토큰값===========================================================================
var csrfHeaderName = "${_csrf.headerName}";
var csrfTokenValue = "${_csrf.token}";

//추천하기======================================================
function recomend(id){
		var rcmText = $("#recomendText").text().trim();

		console.log(id);
		//로그인 한 사용자만 추천 가능
		if(!id){
			alert("로그인한 사용자만 추천이 가능합니다.");
		}else{
			if(rcmText === "추천하기"  ){
				var check = confirm("해당 글을 추천하시겠습니까?")
			}else if(rcmText === '추천취소'){
				var check = confirm("추천을 취소하겠습니까?")
			}
			if(check){
				var bno = "${Gboard.bno}";
				console.log(bno);
				$.ajax({
					url: "/Gboard/recomend",
					data: {id:id,bno: bno},
					type: "post",
					beforeSend: function(xhr){
						xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
					},
					success:function(result){
						//추천하기 버튼을 눌렀을때 컨트롤러 로직에서 추천이 완료됫고 return 값으로 
						//recomend 이 됨 -> 추천이 완료됫기때문에 추천버튼에는 추천취소 버튼으로 바뀜
						if(result == 'recomend'){
							$("#recomendText").empty();
							$("#recomendText").text("추천취소");
						//위와는 반대로 추천이력이 없고 default 상태임	
						}else{
							$("#recomendText").empty();
							$("#recomendText").text("추천하기");
						}
						history.go(0);
					},
					error:function(request,status,error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				}); //end ajax
			}//end if
		}//end else
	}//end function recomend()
	
	$(document).ready(function(){
		
		//csrf 토큰값===========================================================================
		var csrfHeaderName = "${_csrf.headerName}";
		var csrfTokenValue = "${_csrf.token}";
		
		//jquery로 자동으로 토큰 값 전송====================================================================
		$(document).ajaxSend(function(e,xhr,options){
			xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		});
		
		(function(){
			var bno = '<c:out value="${Gboard.bno}"/>';
			console.log(bno);
			$.getJSON("/Gboard/getAttachList", {bno: bno}, function(arr){
				var str = "";
				//글내용 위에 이미지슬라이드
				$(arr).each(function(i,attach){
					if(attach.fileType){
						var fileCallPath = encodeURIComponent(attach.uploadPath+"/"+attach.uuid+"_"+attach.fileName);
						if(i===0){
							str += "<div class='item active'> <img src='/Gboard/display?fileName="+fileCallPath+"' style='width:100%;' ></div>"; 							
						}else{
							str += "<div class='item'> <img src='/Gboard/display?fileName="+fileCallPath+"' style='width:100%;' ></div>";	
						}						
					}
				});
				
				var imgResult = $('.items');
				imgResult.append(str);
				
				//첨부파일 목록 썸네일 이미지
				var list="";
				$(arr).each(function(i,attach){
					if(attach.fileType){
						var path = encodeURIComponent(attach.uploadPath+"/s_"+attach.uuid+"_"+attach.fileName);
						list += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
						list += "<img src='/Gboard/display?fileName="+path+"'>";
						list += "</div>";
						list += "</li>";
					}
				});
				$(".uploadResult ul").html(list);
				
			});//end getjson
		})();//end function
		
		$(".uploadResult").on("click","li",function(e){
			console.log("view image");
			var liObj = $(this);
			
			var imgPath = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));
			
			self.location = "/Gboard/download?fileName="+imgPath;
		});
		
		//메시지 전송=======================================================================================================
		$("#messageSend").on("click",function(){
			$.ajax({
				url:"/message/insert",
				type:"POST",
				data:$("#messageForm").serialize(),
				success:function(result){
					if(result==="success")
						$("#myModal").modal("hide");
				},
				error:function(request,status,error){
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});//end ajax
		});//end messageSend
		
		
			
	});
</script>
<%@ include file="../includes/header.jsp"%>

	<!-- ==============================로그인 시 사용자 아이디 저장============================ -->
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal.username" var="id"/>
	</sec:authorize>
     <!----------------------- 게시판 헤더  ------------------------------->
      <div class="jumbotron">
        <h3>갤러리형 게시판</h3>
      </div>
    <!------------게시글 내용, 작성자, 작성일, 조회수, 추천수 ---------------------->
    <div class="container p-contents">
      <div class="row">
        <div class="col-lg-12">
          <h2><c:out value="${Gboard.title}" /></h2>
        </div>
          <div class="col-lg-12 post-info">
            <div class="col-lg-1">
              <img class="userimage" src="${pageContext.request.contextPath}/resources/img/userimage.jpg" style="width:70px;height:70px;"/>  
            </div>
            <div class="col-lg-2">
              <span class="post-info"><c:out value="${Gboard.writer}" /></span><br>
              <span class="post-info"> 
              <c:if test="${empty Gboard.updatedate}">
              	<fmt:formatDate pattern="yyyy-MM-dd" value="${Gboard.regdate}" />
              	작성 
              </c:if>
              <c:if test="${!empty Gboard.updatedate}">
              	<fmt:formatDate pattern="yyyy-MM-dd" value="${Gboard.updatedate}" />
              	수정
              </c:if>
              </span> 
            </div>
            <div class="col-lg-9 text-right">
              <span class="post-info">조회수 <c:out value="${Gboard.visit}"/></span> 
              <span class="post-info">추천수 <c:out value="${Gboard.recomend }"/></span><br>
             	<!-- =========================================================쪽지보내기=========================================================== -->
             	<!-- ===================================================로그인한 사용자만 쪽지보내기 가능============================================== -->
             	<sec:authorize access="isAuthenticated()">
             	<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal" id="msgBtn">
             			쪽지보내기
             	</button>
				</sec:authorize>
			<!-- Modal -->
			<div id="myModal" class="modal fade" role="dialog">
			  <div class="modal-dialog">
			
			    <!-- Modal content-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title">쪽지전송</h4>
			      </div>
			      <div class="modal-body">
			      <form id="messageForm">
			      <div class="row">
					<div class="col-lg-6">
						받는 아이디 <input type="text" name="receiver" value='<c:out value="${Gboard.writer}" />' class="form-control"/>
					</div>
					<div class="col-lg-6">
						보내는 아이디 <input type="text" name="sender" value='<c:out value="${id}" />' class="form-control"/>
					</div>
					</div>
					<div class="row">
						<div class="col-lg-12">
							내용
							<textarea rows="30" cols="200" name="content" class="form-control"></textarea>
						</div>
					</div>		
					</form>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" id="messageSend" >전송</button>
			      </div>
			    </div>
			
			  </div>
			</div><!-- end Modal -->
			<!-- ============================================================추천하기=================================================================================== -->             	
	              <button type="button" class="btn btn-default" id="recomend" onclick="recomend('${id}');">
	                 <span class="glyphicon glyphicon-thumbs-up"></span> 
	                 <span id="recomendText">
	          <c:if test="${!isRecomend}">        
	                 	추천하기
	          </c:if>
	          <c:if test="${isRecomend}">        
	                 	추천취소
	          </c:if>
	          		</span>
	              </button>
              
            </div>
          </div>
      </div>
    </div><!--//container-->

<!-----------------------------------작성 글 내용 ---------------------------->
<div class="container">
	<!--================================이미지 슬라이드 ======================================-->
	 <div id="myCarousel" class="carousel slide" data-ride="carousel">
	    <!-- Indicators -->
	    <ol class="carousel-indicators">
	      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
	      <li data-target="#myCarousel" data-slide-to="1"></li>
	      <li data-target="#myCarousel" data-slide-to="2"></li>
	    </ol>
	
	    <!-- Wrapper for slides -->
	    <div class="carousel-inner items">
	    </div>
	
	    <!-- Left and right controls -->
	    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
	      <span class="glyphicon glyphicon-chevron-left"></span>
	      <span class="sr-only">Previous</span>
	    </a>
	    <a class="right carousel-control" href="#myCarousel" data-slide="next">
	      <span class="glyphicon glyphicon-chevron-right"></span>
	      <span class="sr-only">Next</span>
	    </a>
	  </div>
	  <!-- ============================//이미지 슬라이드============================= -->
  <div class="row">
    <div class="col-lg-12 text-center">
      <p >
        <c:out value="${Gboard.content}"/>
      </p>  
    </div>
  </div>
</div>
 
<div class="container">
<!--===========================첨부파일============================ -->  
  <div class="well well-sm">
    <strong>첨부파일</strong>
  </div>
  <div class="uploadResult">
    <ul>
       	
    </ul>
  </div>

  
  <!-- ------------------------------목록, 수정, 삭제 버튼----------------------- -->
  <button data-oper="list" class="btn btn-default">목록</button> 
  <sec:authentication property="principal" var="pinfo"/>
  	<sec:authorize  access="isAuthenticated()">
  		<c:if test="${pinfo.username eq Gboard.writer}">
		  <button data-oper="modify" class="btn btn-default">글 수정</button>
		 
  		</c:if>
  	</sec:authorize> 
  <form id="actionForm" method="post">
  	<input type="hidden" name="bno" value='<c:out value="${Gboard.bno}"/>' >
	<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
	<input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
 	<input type='hidden' name='type' value='<c:out value="${cri.type }"/>'>
 	<input type='hidden' name='keyword' value='<c:out value="${cri.keyword }"/>'>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 </form>  
  
  <br><br>  
</div>

<!-- 댓글 작성란=============================================================================== -->
						<div class="card mb-2">
							<div class="card-header bg-light">
								<i class="fa fa-comment fa"></i> 
							</div>
							<div class="card-body">
								<ul class="list-group list-group-flush">
									<li class="list-group-item">
										<div class="form-inline mb-2">
											<label for="replyId"><i
												class="fa fa-user-circle-o fa-2x"></i></label>
	<!-- 로그인 sec ================================================================================================-->											
										<sec:authorize access="isAuthenticated()">
												 <input type="text" id="rmdReplayer" name="replayer" class="form-control ml-2" 
												  value='${id}' readonly="readonly">
												 <label for="replyPassword"
												class="ml-4"><i class="fa fa-unlock-alt fa-2x"></i></label>
											
										</div> <textarea id="rmdInput" class="form-control" name="reply"
											id="exampleFormControlTextarea1" rows="3" required="required"></textarea>
										<button id="rmdBtn"type="button" class="btn btn-dark mt-3">등록</button>
										<form name="superhide" method="post" action="#"><input type="checkbox" name="hidecheck" value="" onclick="hidebutton()"> 비밀댓글</form> <!-- 2021-06-22 form 및 onclick 함수 추가. -->
										</sec:authorize>
										
									</li>
								</ul>
							</div>
						 </div>
						 
<!-- 댓글 수정시 나오는 모달창=============================================================================== -->						 
<!-- Modal -->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">&times;</button>
              <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            </div>
            <div class="modal-body">
              <div class="form-group">
                <label>Reply</label> 
                <input class="form-control" name='reply' value='New Reply!!!!'>
              </div>      
              <div class="form-group">
                <label>Replyer</label> 
                <input class="form-control" name='replyer' value='replyer'>
              </div>
              <div class="form-group">
                <label>Reply Date</label> 
                <input class="form-control" name='replyDate' value='2018-01-01 13:13'>
              </div>
      
            </div>
<div class="modal-footer">
        <button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
        <button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
        <button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
        <button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
      </div>          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->

	

			
		</button>

		<!-- 댓글 목록 나오는 란======================================================================== -->
						<div class='row'>

							<div class="col-lg-12">

								<!-- /.panel -->
								<div class="panel panel-default">
									<div class="panel-heading">
										<i class="fa fa-comments fa-fw"></i> 댓글
									</div>
									<!-- /.panel-heading -->
									<div class="panel-body">
										
										<ul class="chat">
										 <li class="left clearfix" data-rno='10'>
										   <div>
										   	<div class="header">
										   	<!-- 댓글 작성자 나오는곳 <strong> -->
										   		<strong class="primary-font"></strong>
										   		<!-- 댓글 작성날짜 나오는곳 <small> -->	
										   		<small class="pull-right text-muted"></small><br>
										   	</div>
										   	<!-- 댓글 내용 나오는곳<p> -->	
										   	<br>
										   </div>
										  </li> 	
										</ul>
										<!-- ./ end ul -->
									</div>
									<!-- /.panel .chat-panel -->
<!-- 댓글 페이징 번호 생성란 [panel-footer]============================================================================================ -->				
									<div class="panel-footer"></div>


								</div>
							</div>
							<!-- ./ end row -->
						</div>						 





<script type="text/javascript">
	//목록/수정/삭제 버튼 클릭 시 이동=====================================================================
	var actionForm = $("#actionForm");
	$(document).ready(function(){
		$("button[data-oper='modify']").on('click',function(e){
			actionForm.attr("action","/Gboard/modify").attr("method","get").submit();
		});
		$("button[data-oper='list']").on('click',function(e){
			actionForm.attr("action","/Gboard/list").attr("method","get").empty().submit();
		});
	
	});
	
</script>

<%@ include file="../includes/footer.jsp"%>

<!--  여기서부터 추가. 2021-06-30 -->
<script type="text/javascript" src="/resources/js/reply.js"></script>

<script type="text/javascript">

var hidetemp = 0;
function hidebutton(){
	//비밀댓글 체크하면 1
	if (document.superhide.hidecheck.checked == true){
		hidetemp = 1;
	}//비밀댓글 체크안하면 0
	else { hidetemp = 0;}
	console.log(hidetemp);
}//End hidebutton

$(document).ready(function(){
	console.log(replyService)
	
	var actionForm = $("#operForm");
	
	//목록페이지로 이동
	$(".list").on("click",function(e){
		e.preventDefault();
		self.location ="/Gboard/list";
	});
	
	//수정페이지로 이동
	$(".update").on("click",function(e){
		e.preventDefault();
		actionForm.attr("action","/Gboard/modify").attr("method","get").submit();
	});
	
	var writer='<c:out value="${Gboard.writer}" />';
	var arr1 = new Array();
	arr1.push(writer);
	
	//유저 프로필 사진 가져오기
	$.each(arr1, function(i, value){
		console.log("작성자아이디: "+value);
		$.getJSON("/member/getAttachList",{userid:value}, function(attach){
			console.log("유저프로파일: "+arr1[i]);
			console.log("유저 파일이름:"+attach[i]);
			var userimage=$(".userimage");
				//받은 파일이 없을경우
				if(typeof attach == "undefined" || attach == null || attach == ""){
					
					userimage.attr('src','/resources/img/userimage.jpg');
				}//첨부파일이 이미지타입이 아닐 경우
				else {
					$(attach).each(function(i,attach){
					var fileCallPath = encodeURIComponent(attach.uploadPath + "/s_"+attach.uuid+"_"+attach.fileName);
					userimage.attr('src','/display?fileName='+fileCallPath);
					});
				}
		});//end function
});
	
	
	
});
</script>


<script>
$(document).ready(function(){
var bnoValue = '<c:out value="${Gboard.bno}"/>';
var replyChat = $(".chat");
//1.댓글 등록 처리Test======================================================	
	console.log("================");
	console.log("댓글 처리 테스트");

/* 	//reply.js 에서 함수 호출
 	replyService.add(
		{reply:"댓글 등록 테스트", replayer:"윤주영", bno:bno},
		
		function(result){
			alert("결과:  " + result)
		}
	); */ 
//2.댓글 조회 Test=========================================================	
/* 	replyService.getList({bno: bnoValue, page:1}, function(list){
		console.log("댓글 조회 테스트");
		for(var i =0, len = list.length || 0; i <len; i++){
			console.log(list[i]);
		}
	}); */
	
/* //3.댓글 삭제 test========================================================
	replyService.remove(22, function(count){
		console.log(count);
		if(count==="success"){
			alert("삭제성공");
		}
	}, function(err){
		alert("삭제 에러 발생");
	}); */
	
//4.댓글 수정 test=========================================================
	/* replyService.update({
		rno : 34,
		bno : bnoValue,
		reply : "댓글 수정 테스트"
	}, function(result){
		alert("수정 완료");
	}); */
	
//5.특정 댓글 조회 test==========================================================
/* 	replyService.get(10, function(result){
		console.log(result);
	});
	 */
	 
	//댓글 페이징 번호 생성==============================================================================
	var pageNum =1;
	var replyPageFooter = $(".panel-footer"); // 페이징 생성되는 태그
	
	function showReplyPage(replyCnt){
		
		var endNum = Math.ceil(pageNum / 10.0) + 10;
		var startNum = endNum-9;
		
	    var prev = startNum != 1;
		var next = false;
		
		
		if(endNum *10 > replyCnt){
			next= true;
		}
		
		var str = "<ul class='pagination pull-right'>";
		
		if(prev){
			str += "<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Previous</a></li>";
		}
		
		for(var i = startNum; i<=endNum; i++){
			var active = pageNum ==i ? "active" :"";
			str += "<li class='page-item"+active+" '><a class='page-link' href='"+i+"'>"+i+"</a></li>";
		}
		
		if(next){
			str += "<li class='page-item'><a class='page-link' href='"+(endNum+1)+"'>Next</a></li>";
		}
		
		str += "</ul></div>";
		console.log(str);
		replyPageFooter.html(str);
	}	 
	 
	 

//게시글의 조회와 동시에 댓글 목록을 가져온다.===================================================
 	showList(1);
	 
	 //댓글 조회 메서드=========================
	 function showList(page){
		 console.log(page);
		 replyService.getList({bno:bnoValue, page:page||1}, function(replyCnt,list){
			 console.log("replyCnt:  " + replyCnt);
			 console.log("list:  " + list);

			 if(page == -1){
				 pageNum = Math.ceil(replyCnt/10.0);
				 showList(pageNum);
				 return;
			 }
			 
			 var str="";
			 if(list == null|| list.length == 0){
				 return;
			 }
			 for(var i=0, len=list.length||0; i<len; i++){
				// DB에서 읽은 hide의 상태에 따라 't' = 비밀댓글,
				 if(list[i].hide != 't' || loginUser==list[i].replayer || loginUser=="${Gboard.writer}" ){ 
					 str +=" <li id='rnoid' class='left claerfix' data-rno='"+list[i].rno+"'>";
					 str +="  <div><div id='replyID'><strong class='primary-font' id='span_id'>"+list[i].replayer+"</strong>";
					 str +="   <small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
					 str +="    <p id='Rereply'>"+list[i].reply+"</p><button id='Replyremove' type='button' data-rno='"+list[i].rno+"' data-id='"+list[i].replayer+"'>삭제</button></div></li>";
					console.log("비공개여부: "+list[i].hide);
					 //아니면 공개	
				 } else if (list[i].hide == 't' && loginUser!=list[i].replayer ){ 
					 str +=" <li id='rnoid' class='left claerfix' data-rno='"+list[i].rno+"'>"; //html문장 + 자바스크립트 + html태그 종료
					 str +="  <div><div id='replyID'><strong class='primary-font' id='span_id'>"+list[i].replayer+"</strong>";
					 str +="   <small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
					 str +="    <p id='Rereply'>비공개된 댓글입니다.</p><button id='Replyremove' type='button' data-rno='"+list[i].rno+"' data-id='"+list[i].replayer+"'>삭제</button></div></li>"; // else if 문 추가.
				 }else{
					 str +=" <li id='rnoid' class='left claerfix' data-rno='"+list[i].rno+"'>"; //html문장 + 자바스크립트 + html태그 종료
					 str +="  <div><div id='replyID'><strong class='primary-font' id='span_id'>"+list[i].replayer+"</strong>";
					 str +="   <small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
					 str +="    <p id='Rereply'>"+list[i].reply+"</p><button id='Replyremove' type='button' data-rno='"+list[i].rno+"' data-id='"+list[i].replayer+"'>삭제</button></div></li>";
				 }
			 }
			 replyChat.html(str);
			 
			showReplyPage(replyCnt);

	      });//end function
	      
	   }//end showList *
	
	//댓글 관련 버튼============================================================================
	var rmdBtn=$("#rmdBtn"); //댓글 등록버튼
	var rmdInput=$("#rmdInput"); //댓글 작성란
	var rmdReplayer=$("#rmdReplayer"); //댓글 작성자 작성란
	var Replymodify=$("#Replymodify"); //댓글 수정 버튼
	var	Replyremove=$("#Replyremove"); //댓글 삭제버튼
	var Rereply=$("#Rereply"); //
	var modal = $(".modal");

	
	//csrf 토큰값===========================================================================
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}";
	
	
    var loginUser = null;
   
//로그인 시큐리티    
   <sec:authorize access="isAuthenticated()">
    
    var loginUser = '<sec:authentication property="principal.username"/>';   
    
</sec:authorize>

	

	
	//댓글 등록 처리============================================================================
		////////// 2021-06-22 체크 여부를 판단해서, hide[비밀댓글 여부]를 t, f로 분간하여 보냄  ////
		////////// 2021-06-22 스펠체크(텍스트 필드 공간 null 여부,), 공백 문자 (blank_pattern) 인식 기능 추가.////
	rmdBtn.on("click", function(e){
		var spellCheck = document.getElementById("rmdInput");
		var blank_pattern = /^\s+|\s+$/g;
		if(spellCheck.value == '' || spellCheck.value == null){
			alert('내용을 적어주세요');
		}
		
		else if (spellCheck.value.replace(blank_pattern, '' ) == "" ){
			alert('내용을 적어주세요');
		}
		else{
		
		if(hidetemp == 1){
		var reply = {
				reply : rmdInput.val(),
				replayer : rmdReplayer.val(),
				bno : bnoValue,
				hide : 't'
				};
		
		alert('비밀댓글이 적용되었습니다.');
		}
		if (hidetemp == 0) {
			var reply = {
				reply : rmdInput.val(),
				replayer : rmdReplayer.val(),
				bno : bnoValue,
				hide : 'f'
				};
	}
		
		replyService.add(reply, function(result){
			alert("댓글 작성 성공");
			showList(1);
		});
		
		}
		});//end rmdBtn
		
	//댓글 삭제===============================================================================	 
	replyChat.on("click", "button", function(e){
		var rno=$(this).attr("data-rno");
		var rly=$(this).attr("data-id");
		console.log(rno);
		
		console.log("현재 로그인 한 사람: "+loginUser);
		console.log("댓글 원래 작성자: "+rly);
		
		if(!loginUser){
			alert("로그인후 삭제가 가능합니다.");
			return;
		}
		
		if(loginUser != rly){
			alert("자신이 작성한 댓글만 삭제가 가능합니다.");
			return
		}
		
		replyService.remove(rno, rly, function(result){
			alert("삭제 완료");
			//showList(1);
			showList(pageNum);
		});
	
	});

	
//댓긓 페이징된 번호를 눌렀을때================================================================================	
	 replyPageFooter.on("click","li a", function(e){
	        e.preventDefault();
	        console.log("page click");
	        
	        var targetPageNum = $(this).attr("href");
	        
	        console.log("targetPageNum: " + targetPageNum);
	        
	        pageNum = targetPageNum;
	        
	        showList(pageNum);
	      });   
	
//즉시 실행함수 실행하여 해당글의 파일 가져오기================================================================
(function(){
	var bno = '<c:out value="${Gboard.bno}" />';
	$.getJSON("/Gboard/getAttachList",{bno:bno}, function(arr){
		console.log(arr);
		
		var str="";
		$(arr).each(function(i,attach){
			
			//첨부파일이 이미지타입 일 경우
			if(attach.fileType){
				var fileCallPath = encodeURIComponent(attach.uploadPath + "/s_"+attach.uuid+"_"+attach.fileName);
				
				str +="<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"
				str +="<div><img src='/display?fileName="+fileCallPath+"'></div>";
				str +="</li>"
			}//첨부파일이 이미지타입이 아닐 경우
			else{
				
				var fileCallPath = encodeURIComponent(attach.uploadPath + "/"+attach.uuid+"_"+attach.fileName);
				console.log("인코딩된 파일다운 주소 테스트:  " + fileCallPath);
				str +="<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"'>"
				str +="<div><span>"+attach.fileName+"</span><br>";
				str +="<img src='/resources/img/attach.png'></div>";
				str +="</li>"
			}
		});
		$(".uploadResult ul").html(str);
	});//end getjson
})();//end function

//해당 파일 클릭시 이미지이면 확대, 아니면 다운로드
$(".uploadResult").on("click","li",function(e){
	console.log("이미지 확대");
	 
	var obj = $(this);
	var path = encodeURIComponent(obj.data("path")+"/"+obj.data("uuid")+"_"+obj.data("filename"));
	
	console.log("obj: "+obj);
	console.log("다운파일 경로 인코드 path : "+path);
	//파일이 이미지 타입이면 showImage 함수를 통해 사진을 보여주고
	if(obj.data("type")){
		showImage(path.replace(new RegExp(/\\/g),"/"));
		//showImage(path);
	//파일이 이미지 타입이 아니면	
	} else{
		self.location="/download?fileName="+path
	} 
 });
 
//원본 이미지가 켜지고 한번 더 누르면 원 상태로 돌아온다.
$(".bigPictureWrapper").on("click", function(e){
  $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
/* 	  setTimeout(() => {
    $(this).hide();
  }, 1000); */
  
  setTimeout(function() {
	  $(".bigPictureWrapper").hide();
  }, 1000);
  
});

//첨부파일 이미지 확대 함수======================================================================
function showImage(fileCallPath){
	  
	  //alert(fileCallPath);
	
	  $(".bigPictureWrapper").css("display","flex").show();
	  
	  $(".bigPicture")
	  .html("<img src='/display?fileName="+ fileCallPath+"'>")
	  .animate({width:'100%', height: '100%'}, 1000);

	}//ENd showImage


});	
	
</script>