<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@include file="../includes/header.jsp"%>

<div class="gallog_cont">
  <header>
	<div class="">
	  <h2 class="tit">내 최근 게시글<span class="num"></span></h2>
	</div>
  </header>
  <div class="cont_box">
  
	<ul class="cont_listbox">
<!-- ================================게시글 리스트 출력 ====================================== -->	
	<c:forEach items="${reply}" var="reply" varStatus="status">
    <li data-no="39">
    <!-- ============================= 제목 ============================= -->
	<div class="cont box1">
	
	  	<a href="#" target="_blank"> 
	  		<strong><c:out value="${reply.reply }"/></strong>
	  	</a>

	</div>
	<!-- ============================= 내용 ============================= -->
	<div class="cont box2 ">
			
       <a href="#" target="_blank">      [본문으로] </a><button onclick="deleteBno(${reply.rno})">삭제</button>
	</div>
		
		
		
		<!-- ============================= 게시판 타입 ============================= -->
		<div class="cont box3">
		
		  <span class="info">
			<a href="#"><c:out value="${reply.type }"/></a>
		 <span class="date"><fmt:formatDate value="${reply.replyDate }" pattern="yyyy-MM-dd HH:ss"/></span>
	     </span>
		</div>
  </li>
	</c:forEach>
</ul>

<!--============================================== 페이징 버튼 활성화 ==============================================  -->				
		<div class='pull-right'>
					<ul class="pagination">
		<c:if test="${pageMaker.prev}">
							<li class="paginate_button previous"><a
								href="${pageMaker.startPage -1}">Previous</a></li>
						</c:if>

						<c:forEach var="num" begin="${pageMaker.startPage}"
							end="${pageMaker.endPage}">
							<li class="paginate_button  ${pageMaker.cri.pageNum == num ? "active":""} ">
								<a href="${num}">${num}</a>
							</li>
						</c:forEach>

						<c:if test="${pageMaker.next}">
							<li class="paginate_button next"><a
								href="${pageMaker.endPage +1 }">Next</a></li>
						</c:if>


	</ul>
				</div>
				<!--  end Pagination -->
		      
			<form id='actionForm' action="/member/replyAll" method='get'>
				<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
				<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
				<input type="hidden" name="principal" value='<sec:authentication property="principal.username"/>'>
			</form>
<!--============================================================================================  -->							
	
  </div>
</div>
<!-- 내 댓글 -->
</div>


<%@include file="../includes/footer.jsp"%>
<script>
var csrfHeaderName = "${_csrf.headerName}";
var csrfTokenValue = "${_csrf.token}";

function deleteBno(rno){
	var check = confirm("정말 삭제하시겠습니까?");
	console.log(rno);
	if(check == true){
		$.ajax({
			url: "/member/deleteMyReply",
			type: "post",
			data: {rno:rno },
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			success : function(result){
				if(result=="success"){
					alert("삭제가 완료되었습니다.");
					history.go();
				}
			  }, error: function (request,status,errorData){   
    		    	alert('error code: '+request.status+"\n"
    		    			+'message:' +request.reponseText+'\n'
    		    			+ 'error :'+  errorData);
    		    }
			});//ajax
	}//if
	
}//deleteBno

$(document).ready(function(){
	var actionForm = $("#actionForm");
	
	
	
	//페이지 번호 누르면 이동 ==============================================	
	   $(".paginate_button a").on("click", function(e){
	    	e.preventDefault();
	    	
	    	console.log("페이지 버튼 틀릭");
	    	
	    	actionForm.find("input[name='pageNum']").val($(this).attr("href"));
	    	actionForm.submit();
	    });
	
	
	
	
	

});//document
</script>

