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
 <br><br><br>
      <div class="cont_head">
              <h3 class="head_tit">비밀번호 재설정</h3>
            </div>
            <div id="contbox" class="con_box brd">
            <!-- em><b>2013.02.18 이전 실명인증을 통해 가입한 비밀번호는 오른쪽 버튼을 통해 찾을 수 있습니다.</b></em>
					<a href="https://dcid.dcinside.com/join/member_find_idpw.php" target="_blank"><img src="./images/btn_search_pw.gif" alt="실명인증 비밀번호 찾기"/></a>&nbsp;<span class="t_f_s">(인증전환회원  제외)</span-->
              <!-- 정보 입력 -->
              <div class="con innerbox">
                <h4 class="tit font_red dotred">가입 시 인증한 이메일(인증받은)을 통해 비밀번호를 재설정하실 수 있습니다.</h4>
                <div class="info_txt">
                  <p><em class="tip_deco_dot"></em>아이디를 잊으신 경우 아이디 찾기를 통해 아이디 확인을 먼저 해주시기 바랍니다.</p>
                  <p><em class="tip_deco_dot"></em>이메일은 반드시 인증받은(또는 이메일 변경을 통해 변경한) 메일 주소를 입력하셔야 합니다.</p>
                  <p><em class="tip_deco_dot"></em>회원 가입 시 실명이 아닌 다른 이름으로 가입했을 경우, 해당 가입 시 입력한 이름을 입력하셔야 합니다.</p>
                </div>
                <div class="bg_box">
                  <div class="form_box">
                    <input type="text" class="int" title="아이디" id="user_id" placeholder="아이디">
                    <input type="text" class="int" title="이름" id="real_name" placeholder="이름">
                    <input type="text" class="int" title="이메일" id="email" placeholder="이메일">
                    <button type="button"  id="pw_find" class="btn_blue small btn_wrfull">확인</button>
                  </div>
                  <div>
                  	<a href="/member/find/idFind">아이디 찾기</a>
                  </div>
                </div>
                <div class="info_txt mt25"> 
                  <p>
                    <em class="tip_deco_dot"></em><b>2013.02.18 이전 실명인증을 통해 가입한 아이디의 비밀번호</b>는 실명인증 아이디 찾기를 통해<br>재설정 받을 수 있습니다.
                    <span class="name_auth_search">
                      <a href="../join/member_find_pw.php"  target="_blank" >실명인증 비밀번호 재발급</a> (인증 전환 회원 제외)
                    </span>
                  </p>
                </div>
              </div> 
              <!-- //정보 입력 -->
            </div>
          </section>

        </article>
      </div>
      </div>
  

<%@ include file="../../includes/footer.jsp"%>