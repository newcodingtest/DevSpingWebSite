package org.yoon.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

@Log4j
//���ٱ����� �ο����� ���� ����ڰ� �����Ͽ������ 
//ex) ������ ����� ����ڰ� Ȩ�������� �α��� ������ �ʿ�� �ϴ� �������� �����Ͽ��� ���
public class CustomAccessDeniedHandler implements AccessDeniedHandler  {
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException ad) throws IOException, ServletException {
	
				request.setAttribute("msg", "������ �����ϴ�. ȸ������ �� �̿����ּ���");
			
				log.error("Access Denied Handler");
		
		response.sendRedirect("/err/denied-page");
	}

}
