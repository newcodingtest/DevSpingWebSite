package org.yoon.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

@Log4j
//접근권한이 부여되지 않은 사용자가 접근하였을경우 
//ex) 세션이 만료된 사용자가 홈페이지의 로그인 권한을 필요로 하는 페이지에 접근하였을 경우
public class CustomAccessDeniedHandler implements AccessDeniedHandler  {
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		log.error("Access Denied Handler");
		
		response.sendRedirect("/accessError");
	}

}
