package org.yoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yoon.service.BoardService;
import org.yoon.service.GBoardService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class MainController {
	
	@Autowired
	private GBoardService gservice;
	
	@Autowired
	private BoardService bservice;
	
	//�ֽű� �� �α�� �����ͼ� ���� ������
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Model model) {
		log.info("================���� ������===================");
		model.addAttribute("gNewList",gservice.getNewList());
		model.addAttribute("gBestList",gservice.getBestList());
		model.addAttribute("bNewList",bservice.getNewList());
		model.addAttribute("bBestList",bservice.getBestList());
		
		return "main";
	}
	@GetMapping("/test")
	public void test() {}

}
