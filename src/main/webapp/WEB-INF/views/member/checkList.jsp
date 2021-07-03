<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
</head>
<body>
<%@include file="../includes/header.jsp"%>

<div class="gallog_cont">
  <header>
	<div class="">
	  <h2 class="tit">내 최근 게시글<span class="num">(${cnt})</span></h2>
	  <a href="/rlarudxo123/posting" class="btn_blue smallest go">전체보기</a>
	</div>
  </header>
  <div class="cont_box">
  
	<ul class="cont_listbox">
<!-- ================================게시글 리스트 출력 ====================================== -->	
	<c:forEach items="${user}" var="user" >
    <li data-no="39">
    <!-- ============================= 제목 ============================= -->
	<div class="cont box1">
	
	  <c:if test="${user.type == '자유'}">
	  	<a href="/board/get?bno=${user.bno}" target="_blank"> 
	  		<strong><c:out value="${user.title}" /></strong>
	  	</a>
	  </c:if>
	   <c:if test="${user.type == '갤러리'}">
	  	<a href="/Gboard/get?gno=${user.bno}" target="_blank"> 
	  		<strong><c:out value="${user.title}" /></strong>
	  	</a>
	  </c:if>
	  
	</div>
	<!-- ============================= 내용 ============================= -->
	<div class="cont box2 ">
				<c:if test="${user.type == '자유'}">
            	<a href="/board/get?bno=${user.bno}" target="_blank">      [본문으로] 		<!--  /a-->     
            	</c:if>
            	<c:if test="${user.type == '갤러리'}">
            	<a href="/Gboard/get?gno=${user.bno}" target="_blank">      [본문으로] 		<!--  /a-->     
            	</c:if>
      	</a>
		</div>
		<!-- ============================= 게시판 타입 ============================= -->
		<div class="cont box3">
		  <span class="info">
			<a href="#"></a><c:out value="${user.type}" /></a>
		<span class="date"><fmt:formatDate value="${user.regdate }" pattern="yyyy-MM-dd HH:ss"/></span>
	  </span>
	</div>
  </li>
 </c:forEach> 
</ul>

	
  </div>
</div>
</section>
<!-- 내 댓글 -->
<section>
<div class="gallog_cont">
  <header>
	<div class="">
	  <h2 class="tit">내 최근 댓글<span class="num">(29)</span></h2>
	  <a href="/rlarudxo123/comment" class="btn_blue smallest go">전체보기</a>
	</div>
  </header>
  <div class="cont_box">
  
	<ul class="cont_listbox">
<!-- ================================댓글 리스트 출력 ====================================== -->	
        <li data-no="38">
	<div class="cont box1">
	  <a href="https://gall.dcinside.com/mgallery/board/view?id=owgenji&no=2198011" target="_blank">
	  			<strong>아니 블리자드 PC충 시발련들아</strong>
	  </a>
	</div>
	<div class="cont box2 ">

            	<a href="https://gall.dcinside.com/mgallery/board/view?id=owgenji&no=2198011" target="_blank">      		옵치도 ㄹㅇ 망해가는구나 ㅋㅋㅋㅋㅋㅋㅋㅋㅋ      		<!--  /a-->
      	</a>
     
		</div>
		<div class="cont box3">
		  <span class="info">
			<a href="https://gall.dcinside.com/mgallery/board/lists?id=owgenji" target="_blank" class="gall_name">겐지</a>
		<span class="date">2021.06.22 13:46:41</span>
	  </span>
	</div>
  </li>
          <li data-no="37">
	<div class="cont box1">
	  <a href="https://gall.dcinside.com/mgallery/board/view?id=owgenji&no=2198011" target="_blank">
	  			<strong>아니 블리자드 PC충 시발련들아</strong>
	  </a>
	</div>
	<div class="cont box2 ">

            	<a href="https://gall.dcinside.com/mgallery/board/view?id=owgenji&no=2198011" target="_blank">      		쿵쾅이 어서오고      		<!--  /a-->
      	</a>
     
		</div>
		<div class="cont box3">
		  <span class="info">
			<a href="https://gall.dcinside.com/mgallery/board/lists?id=owgenji" target="_blank" class="gall_name">겐지</a>
		<span class="date">2021.06.22 13:45:55</span>
	  </span>
	</div>
  </li>
          <li data-no="28">
	<div class="cont box1">
	  <a href="https://gall.dcinside.com/board/view?id=wows&no=912069" target="_blank">
	  			<strong>KT 1자리 NDTAK 3자리 클랜 인원구함</strong>
	  </a>
	</div>
	<div class="cont box2 ">
	  
	  	  
	  	  
	  	  
            	<a href="https://gall.dcinside.com/board/view?id=wows&no=912069" target="_blank">      		본인 난독증인데 쌉가능?      		<!--  /a-->
      	</a>
      	  
	        
      	  
	            
		</div>
		<div class="cont box3">
		  <span class="info">
			<a href="https://gall.dcinside.com/board/lists?id=wows" target="_blank" class="gall_name">월드 오브 워쉽</a>
		<span class="date">2021.05.26 13:32:51</span>
	  </span>
	</div>
  </li>
          <li data-no="27">
	<div class="cont box1">
	  <a href="https://gall.dcinside.com/board/view?id=wows&no=912069" target="_blank">
	  			<strong>KT 1자리 NDTAK 3자리 클랜 인원구함</strong>
	  </a>
	</div>
	<div class="cont box2 ">
	  
	  	  
	  	  
	  	  
            	<a href="https://gall.dcinside.com/board/view?id=wows&no=912069" target="_blank">      		ㅈ벤 씹덕 네캎 씹떡볶이 인 사람인데 나 고닉임 쌉가능??      		<!--  /a-->
      	</a>
      	  
	        
      	  
	            
		</div>
		<div class="cont box3">
		  <span class="info">
			<a href="https://gall.dcinside.com/board/lists?id=wows" target="_blank" class="gall_name">월드 오브 워쉽</a>
		<span class="date">2021.05.26 12:54:27</span>
	  </span>
	</div>
  </li>
          <li data-no="26">
	<div class="cont box1">
	  <a href="https://gall.dcinside.com/board/view?id=wows&no=877659" target="_blank">
	  			<strong>아까 복귀한 북미 뉴비 고닉했어</strong>
	  </a>
	</div>
	<div class="cont box2 ">
	  
	  	  
	  	  
	  	  
            	<a href="https://gall.dcinside.com/board/view?id=wows&no=877659" target="_blank">      		이젝게이야 같이하자      		<!--  /a-->
      	</a>
      	  
	        
      	  
	            
		</div>
		<div class="cont box3">
		  <span class="info">
			<a href="https://gall.dcinside.com/board/lists?id=wows" target="_blank" class="gall_name">월드 오브 워쉽</a>
		<span class="date">2021.03.01 05:37:42</span>
	  </span>
	</div>
  </li>
      
</ul>

	
  </div>
</div>
</section>
<!-- //내 댓글 -->

<!-- 스크랩 -->
<section>
<div class="gallog_cont">
  <header>
	<div class="">
	  <h2 class="tit">스크랩<span class="num">(0)</span></h2>
	  <a href="/rlarudxo123/scrap" class="btn_blue smallest go">전체보기</a>
	</div>
  </header>
  <div class="cont_box">
  
	
<div class="gallog_empty small">
  스크랩된 글이 없습니다.</div>
	
  </div>
</div>
</div>
</section>

</body>
</html>