<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../../includes/header.jsp"%>
<!--=========================== 초기화 =========================== -->
<style>
.correct{
	color : green;
}
.incorrect{
	color: red;
}
<style>
	*{ margin:0; padding:0; font: normal 12px 'Dotum';}
	a{ text-decoration: none;}
	img{border: 0;}
	ul{list-style:none;}
	
	body{ width: 980px; margin-top:30px; } /*내용 중앙으로*/
</style>
<!--=========================== 헤더 =========================== -->
<style>

	
</style>
<!--=========================== 사이드 =========================== -->
<style>
</style>
<!--=========================== 네비게이션 [위]=========================== -->
<style>

</style>
<!--=========================== 네비게이션 아래=========================== -->
<style></style>
<!--=========================== 목록 =========================== -->
<style></style>
<!--=========================== 본문 =========================== -->
<style></style>
<!--=========================== 초기화=========================== -->
<style></style>

      <div class="content idsearch">
      <br><br><br>
        <article>
          <h2 class="blind">비밀번호바꾸기</h2>
          <section id="pagemenu">
            <h2 class="blind">페이지 메뉴</h2>
         
          </section>
          <section>
            <div class="cont_head">
              <h3 class="head_tit">비밀번호바꾸기</h3>
            </div>
            <div id="contbox" class="con_box brd">
              <!-- 정보 입력 -->
              <div class="con innerbox">
              <!-- em><b>2013.02.18 이전 실명인증을 통해 가입한 아이디는 오른쪽 버튼을 통해 찾을 수 있습니다.</b></em>
				<a href="https://dcid.dcinside.com/join/member_find_idpw.php" target="_blank"><img src="./images/btn_id_search.gif" alt="실명인증 아이디 찾기" width="126"/></a>&nbsp;<span class="t_f_s">(인증전환회원  제외)</span-->
                <h4 class="tit font_red dotred">비밀번호를 정확히 기입해주시기 바랍니다.</h4>
                <div class="info_txt">
                  <p><em class="tip_deco_dot"></em>이메일은 반드시 회원가입시 기입한 이메일을 입력하셔야 합니다.</p>
                  <p><em class="tip_deco_dot"></em>회원 가입 시 실명이 아닌 다른 이름으로 가입했을 경우, 해당 가입 시 입력한 이름을 입력하셔야 합니다.</p>
                </div>
                <div class="bg_box">
                  <div class="form_box">
                  <form id="actionForm" action="/member/updatepw" method="post">
                  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                  	<input type="hidden" class="int" value="${user.id}" name="userid"  />
                    <input type="password" class="int" id="userpw" name="userpw" title="비밀번호" placeholder="비밀번호" required="required" />
                    <input type="password" class="int" id="userpwCheck" name="userpwCheck" title="비밀번호 확인" placeholder="비밀번호 확인" required="required" /><span id="checkCss"></span>
                    <button type="submit" id="id_find" class="btn_blue small btn_wrfull">확인</button>
                   </form>
                  </div>
                  <div>
                  <a href="/member/find/idFind">아이디 찾기</a>
                  </div>
                </div>
                <div class="info_txt mt25">
                  <p>
                    <em class="tip_deco_dot"></em><b>2013.02.18 이전 실명인증을 통해 가입한 아이디</b>는 실명인증 아이디 찾기를 통해 찾을 수 있습니다.<br>

                  </p>
                </div>
              </div>
            </div>
          </section>

        </article>
      </div>
  </div>

<%@ include file="../../includes/footer.jsp"%>
<script>
<!-- 비밀번호 유효성 체크 알림  -->
function chkPW(){
	 var pw = $("#userpw").val();
	 var num = pw.search(/[0-9]/g);
	 var eng = pw.search(/[a-z]/ig);
	 var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
	 if(pw.length < 10 || pw.length > 20){
	  alert("10자리 ~ 20자리 이내로 입력해주세요.");
	  return false;
	 }else if(pw.search(/\s/) != -1){
	  alert("비밀번호는 공백 없이 입력해주세요.");
	  return false;
	 }else if( (num < 0 && eng < 0) || (eng < 0 && spe < 0) || (spe < 0 && num < 0) ){
	  alert("영문,숫자, 특수문자 중 2가지 이상을 혼합하여 입력해주세요.");
	  return false;
	 }else {
		 alert("사용가능한 비밀번호입니다.");
		console.log("통과");	
		return true;
	 }
	}

$(document).ready(function(){
//비밀번호 확인란 로직==================================================================	
	var email= "<c:out value="${user.email}" />";
	var id= "<c:out value="${user.id}" />";
	
	
	$("#id_find").on("click",function(e){
		var pw = $("#userpw").val();
		var pwCheck = $("#userpwCheck").val();
		var checkCss=$("#checkCss");
		var actionForm=$("#actionForm");
	
		e.preventDefault();
		
		//비밀번호 유효성체크
		if(chkPW()==false){
			return;
		}
		
		//비밀번호 비교
		if(pw == pwCheck){
			checkCss.html("비밀번호가 일치합니다.")
			checkCss.attr("class", "correct"); 
		}else{
			checkCss.html("비밀번호가 불일치합니다.")
			checkCss.attr("class", "incorrect");
			return;
		}
		actionForm.submit();
		
		
	});
	
		
	
	});
</script>