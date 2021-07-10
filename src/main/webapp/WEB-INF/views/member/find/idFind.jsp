<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
                <br>
               
             <h4 id="checkId"><h3 id="checkId1"></h3></h4>
              
                <div class="info_txt">
                  <p><em class="tip_deco_dot"></em>이메일은 반드시 인증받은(or 이메일 변경을 통해 변경한) 이메일을 입력하셔야 합니다.</p>
                  <p><em class="tip_deco_dot"></em>회원 가입 시 실명이 아닌 다른 이름으로 가입했을 경우, 해당 가입 시 입력한 이름을 입력하셔야 합니다.</p>
                </div>
                <div class="bg_box">
                  <div class="form_box">
                   <form id="actionForm">
                    <input type="text" class="int" id="username" name="username"title="이름 입력" placeholder="이름" required="required">
                    <input type="text" class="int" id="useremail" name="useremail" title="이메일 입력" placeholder="이메일" required="required">
                    <button type="submit" id="id_find" class="btn_blue small btn_wrfull">확인</button>
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
/*입력 이메일 유효성 검사*/
function mailFormCheck(email){
	 var form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	 return form.test(email);
}

$(document).ready(function(){
	$("#id_find").on("click",function(e){
		e.preventDefault();
		console.log("아이디 찾기 클릭");
		var username = $('input[name=username]').val();
		var useremail = $('input[name=useremail]').val();
		var emailInput = $("#useremail").val();
		
		if(mailFormCheck(emailInput)==false){
			alert("올바르지 못한 이메일 형식입니다.");
			return false;
		}
	
		$.ajax({
			type: "get",
			url: "/member/idFind?username="+username+"&useremail="+useremail,
			dataType: "text",
			contentType: "application/json; charset=UTF-8",
			success: function(data){
				console.log(data);
				var result=JSON.stringify(data);
				
				if(data!=null && data!="x"){
					
					$("#checkId").html("해당 아이디 입니다.");
					$("#checkId1").html(result);
				
				}else if(data ==="x"){
					alert("해당 정보에 대한 계정이 존재하지 않습니다.");
					
				}
			},
			error:function(request,status,error){
				alert("요청실패");
			}
			
		});
		
	});
	
});

</script>

<c:if test="${param.msg !=null }">
	<script>
		$(document).ready(function(){
			alert(msg)
		});
	</script>
</c:if>