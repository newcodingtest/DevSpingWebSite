package org.yoon.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
//AuthenticationSuccessHandler: 로그인후 처리
public class RealLoginSuccessHandler implements AuthenticationSuccessHandler  {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {

		log.info("로그인 성공");
		List<String>roleNames = new ArrayList<>();
		//auth:인증된 사용자의 정보
		auth.getAuthorities().forEach(authority -> roleNames.add(authority.getAuthority()));
		
		log.warn("권한 :" +roleNames);
		
		//인증 권한이 어드민이면 어드민 페이지로 따로 이동
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("admin");
		}
		
		////로그인이 되면 "/" 로 보냄 ---->HomeController 참조
		response.sendRedirect("/");
	}

}
