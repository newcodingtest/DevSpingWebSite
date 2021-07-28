<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@include file="../includes/header.jsp"%>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">자유게시판</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           자유게시판
                           <button id='regBtn' type="button" class="btn btn-xs pull-right">글 등록</button>
                        </div>
                        <!-- /.panel-heading -->
			<div class="panel-body">
				<table width="100%"
					class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
							<th>추천</th>
						</tr>
					</thead>
					<c:forEach items="${list}" var="board">
						<tr>
							<th><c:out value="${board.bno}" /></th>
							<th><a class="move" href="<c:out value="${board.bno}"/>"><c:out
										value="${board.title}" /> <b>[<c:out
											value="${board.replycnt}" />]
								</b></a></th>
							<th><c:out value="${board.writer}" /></th>
							<th><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.regdate}" /></th>
							<th><c:out value="${board.visit}" /></th>
							<th><c:out value="${board.recomend}" /></th>
						</tr>
					</c:forEach>
				</table>
<!-- ============================================== 검색 조건 ============================================== -->				
				<div class='row'>
					<div class="col-lg-12">

						<form id='searchForm' action="/board/list" method='get'>
							<select name='type'>
								<option value=""
									<c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
								<option value="T"
									<c:out value="${pageMaker.cri.type eq 'T'?'selected':''}"/>>제목</option>
								<option value="C"
									<c:out value="${pageMaker.cri.type eq 'C'?'selected':''}"/>>내용</option>
								<option value="W"
									<c:out value="${pageMaker.cri.type eq 'W'?'selected':''}"/>>작성자</option>
								<option value="TC"
									<c:out value="${pageMaker.cri.type eq 'TC'?'selected':''}"/>>제목
									or 내용</option>
								<option value="TW"
									<c:out value="${pageMaker.cri.type eq 'TW'?'selected':''}"/>>제목
									or 작성자</option>
								<option value="TWC"
									<c:out value="${pageMaker.cri.type eq 'TWC'?'selected':''}"/>>제목
									or 내용 or 작성자</option>
							</select> 
								<input type='text' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' />
							 	<input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' />
							 	<input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
							 	<input type='hidden' name='type' value='<c:out value="${ pageMaker.cri.type }"/>'>
							<button class='btn btn-default'>검색</button>
						</form>
					</div>
				</div>
				
				
				
				
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
		      
			<form id='actionForm' action="/board/list" method='get'>
				<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
				<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
				<input type='hidden' name='type' value='<c:out value="${ pageMaker.cri.type }"/>'>
				<input	type='hidden' name='keyword' value='<c:out value="${ pageMaker.cri.keyword }"/>'>
			</form>
		      
<!--============================================== Modal  추가 ==============================================  -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Modal title</h4>
						</div>
				<!-- ======================= 처리 완료 알람 부분======================= -->		
						<div class="modal-body">처리가 완료되었습니다.</div>
				<!-- ======================= 처리 완료 알람 부분======================= -->	
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary">Save
								changes</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
<!--============================================== Modal  추가 끝 ==============================================  -->			
       

<%@include file="../includes/footer.jsp"%>
<script>
//
var actionForm = $("#actionForm")
//목록으로 이동 attr->해당 속성값 가져오기 append ->뒤에 정보추가
$(".move").on("click",function(e){
	e.preventDefault();
	
	actionForm.append("<input type='hidden' name='bno' value='"+$(this).attr("href")+"'>");
	actionForm.attr("action", "/board/get").submit();
	
	
});

//글 등록 창으로 이동=====================================
$("#regBtn").on("click", function(e){
	'<sec:authorize access="isAuthenticated()">'
	self.location ="/board/register";
	'</sec:authorize>'
	
	'<sec:authorize access="isAnonymous()">'
	alert("로그인후 가능합니다.");
	self.location="/member/Login";
	'</sec:authorize>'
});



$(document).ready(function(){
//페이지 번호 누르면 이동 ==============================================
    $(".paginate_button a").on("click", function(e){
    	e.preventDefault();
    	
    	console.log("페이지 버튼 틀릭");
    	
    	actionForm.find("input[name='pageNum']").val($(this).attr("href"));
    	actionForm.submit();
    });
    
//검색 버튼 후 이벤트 처리==============================================
	var searchForm = $("#searchForm");
	
	$("#searchForm button").on("click", function(e){
		
		if(!searchForm.find("option:selected").val()){
			alert("검색종류를 선택하세요");
			return false;
		}
		if(!searchForm.find("input[name='keyword']").val()){
			alert("키워드를 입력하세요");
			return false;
		}
		//검색을 하면 1페이지가 첫페이지로 나오게끔
		searchForm.find("input[name='pageNum']").val("1");
		e.preventDefault();
		
		searchForm.submit();
	});
	
	
});
</script>
<c:if test="${param.msg !=null}">
   	 <script>
   	 $(document).ready(function(){
   		 alert(msg);
   	 }); 	
     </script>
	</c:if>
