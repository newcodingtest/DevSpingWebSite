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
//AuthenticationSuccessHandler: �α����� ó��
public class RealLoginSuccessHandler implements AuthenticationSuccessHandler  {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {

		log.info("�α��� ����");
		List<String>roleNames = new ArrayList<>();
		//auth:������ ������� ����
		auth.getAuthorities().forEach(authority -> roleNames.add(authority.getAuthority()));
		
		log.warn("���� :" +roleNames);
		
		//���� ������ �����̸� ���� �������� ���� �̵�
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("admin");
		}
		
		////�α����� �Ǹ� "/" �� ���� ---->HomeController ����
		response.sendRedirect("/");
	}

}
