<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../../includes/header.jsp"%>
<!--=========================== 초기화 =========================== -->
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
          <h2 class="blind">아이디 찾기</h2>
          <section id="pagemenu">
            <h2 class="blind">페이지 메뉴</h2>
         
          </section>
          <section>
            <div class="cont_head">
              <h3 class="head_tit">아이디 찾기</h3>
            </div>
            <div id="contbox" class="con_box brd">
              <!-- 정보 입력 -->
              <div class="con innerbox">
              <!-- em><b>2013.02.18 이전 실명인증을 통해 가입한 아이디는 오른쪽 버튼을 통해 찾을 수 있습니다.</b></em>
				<a href="https://dcid.dcinside.com/join/member_find_idpw.php" target="_blank"><img src="./images/btn_id_search.gif" alt="실명인증 아이디 찾기" width="126"/></a>&nbsp;<span class="t_f_s">(인증전환회원  제외)</span-->
                <h4 class="tit font_red dotred">회원가입 시 입력한 이름과 이메일(인증받은) 정보를 입력해 주시기 바랍니다.</h4>
                <div class="info_txt">
                  <p><em class="tip_deco_dot"></em>이메일은 반드시 인증받은(or 이메일 변경을 통해 변경한) 이메일을 입력하셔야 합니다.</p>
                  <p><em class="tip_deco_dot"></em>회원 가입 시 실명이 아닌 다른 이름으로 가입했을 경우, 해당 가입 시 입력한 이름을 입력하셔야 합니다.</p>
                </div>
                <div class="bg_box">
                  <div class="form_box">
                   <form id="actionForm" action="/member/find/pwChange" method="post">
                   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                   <input type='hidden' name='useremail' value="${user.email}" />
                   <input type='hidden' id="userid" name='userid' value="${user.id}"/>'>
                   <h3>이메일 인증 확인 </h3>
                    
                    <input type="text" class="mail_check_input" required="required">
                    <br>
                    <button type="button" id="resubmit">재전송</button>
                    <button type="button" id="check" class="btn_blue small btn_wrfull">확인</button>
                   </form>
                  </div>
                  <div>
                  <a href="/member/find/pwFind">비밀번호 찾기</a>
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

$(document).ready(function(){
	//자동으로 이메일 인증 실행
	var code="";
	var email= "<c:out value="${user.email}" />";
	var id= $("#userid").val();
	
	//post 형식으로 보낼때 꼭 같이 보내주자 csrf 토큰(보안문제 해결)
	var csrfHeaderName ="${_csrf.headerName}";
	var csrfTokenValue ="${_csrf.token}"; 	
 	//console.log(email);
	//console.log(id); 
	
	$("#resubmit").on("click",function(){
		console.log("이메일 재전송");
		alert("인증메일이 재전송되었습니다.");
		$.ajax({
			type : "get",
			beforeSend: function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			url: "/member/mailCheck",
			data : {useremail: email},		
			success:function(data){
				code=data;
			}		
		});
	})
	
 	//받아온 이메일 정보가 있을경우만 자동으로 인증메세지 전송->새로고침시 자동으로 보내지는 문제 해결
	if(email==null){
		alert("이전 페이지에서 정보를 다시 입력해주세요");
		self.location="/member/find/pwFind";
	}else{
			(function(){
				$.ajax({
					type : "get",
					url: "/member/mailCheck",
					data : {email: email},	
					success:function(data){
						code=data;
					}		
				});
			})();
	}
	
	 
	//이메일 인증 확인
	$("#check").on("click",function(e){
		e.preventDefault();
		
		var inputCode=$(".mail_check_input").val();
		
		if(inputCode.length ==0){
			alert("인증번호가 입력되지 않았습니다.");
			return false;
		}else if(inputCode == code){
			alert("인증번호가 확인되었습니다.");
		}else {
			alert("인증번호가 불일치합니다. 다시 입력해주세요");
			return false;
		}
		
		$("#actionForm").submit();
	
	});	
});
</script>

