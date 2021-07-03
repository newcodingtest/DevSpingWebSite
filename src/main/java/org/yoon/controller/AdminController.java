package org.yoon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yoon.domain.Criteria;
import org.yoon.domain.PageDTO;
import org.yoon.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/admin/*")
@AllArgsConstructor
public class AdminController {

	private MemberService service;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/memberList")
	public void list(Criteria cri, Model model){
		log.info("회원 정보 리스트로 이동");
		model.addAttribute("member", service.getList(cri));
		model.addAttribute("pageMaker", new PageDTO(cri,service.getTotal(cri)));
	}
}
