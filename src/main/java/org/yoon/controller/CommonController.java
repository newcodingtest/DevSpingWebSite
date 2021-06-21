package org.yoon.controller;

import java.security.Principal;
import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus; 
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.MemberAttachVO;
import org.yoon.domain.MemberVO;
import org.yoon.domain.ReplyVO;
import org.yoon.mapper.MemberMapper;
import org.yoon.security.domain.CustomUser;
import org.yoon.service.BoardService;
import org.yoon.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@AllArgsConstructor
@RequestMapping("/member/*")
public class CommonController {
	
	private MemberService service;
	
	//이메일 인증
	private JavaMailSender mailSender;
	
	
	//ajax로 받아온 이메일 
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheck(String email) {
		log.info("이메일 데이터 전송 확인");
		log.info("인증번호: "+email);
		
		//인증번호 난수생성
		Random random = new Random();
		int CheckNum = random.nextInt(888888)+111111;
		
		//인증 요청한 이메일에게 인증번호 보내기
		String setFrom = "pulpul8282@naver.com";
		String toMail = email;
		String title = "회원가입 인증 이메일 입니다.";
		String content = "홈페이지를 방문해주셔서 감사합니다."+"<br><br>"+"인증 번호는"+CheckNum+"입니다."+"<br>"+"해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
		
		//이메일 전송 코드
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content,true);
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String num = Integer.toString(CheckNum);
		log.info("인증번호: "+num);
		
		return num;
	}
	
	//프로필 사진 가져오기
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>>getAttachList(String userid){
		log.info("getAttachList: "+userid);
		log.info(service.getAttachList(userid));
		
		return new ResponseEntity<>(service.getAttachList(userid),HttpStatus.OK);
	}
	
	
	//마이프로필 수정
	@PreAuthorize("isAuthenticated()")
	@PostMapping("modify")
	public String modify(MemberVO vo,RedirectAttributes rttr) {
		log.info("/modify");
		int result = service.update(vo);
		if(result>0) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/member/myProfile";
	}
	
	@PreAuthorize("isAuthenticated()")
	//마이프로필 창으로 이동
	@GetMapping("myProfile")
	public void profile(Principal principal,Model model) {
		
		log.info("마이프로필 창으로 이동");
		log.info("유저아이디: "+principal.getName());
		String userid=principal.getName();
		MemberVO vo=service.read(userid);
		model.addAttribute("user", vo);
	}
	
	@PreAuthorize("isAuthenticated()")
	//마이프로필 창으로 이동
	@GetMapping("modify")
	public void profile() {
		log.info("마이프로필 수정페이지로 이동");
	
	}
	
	
	//회원가입 창으로 이동
	@GetMapping("customRegister")
	public void Register() {
		log.info("회원가입 창으로 이동");
	}
	
	//회원 등록
	@PostMapping("/customRegister")
	public String Register(MemberVO vo, RedirectAttributes rttr) {
		log.info("회원등록 post");
				
		service.insert(vo);	
	
		return "redirect:/member/customLogin";
	} 

	//로그인 창으로 이동
	@GetMapping("/customLogin")
	public void login(String error, String logout, Model model) {
		log.info("error: "+error);
		log.info("logout: "+logout);
		
		if(error != null) {
			model.addAttribute("error", "로그인 에러 발생!! 계정을 확인해주세요");
		}
		if(logout != null) {
			model.addAttribute("logout", "로그아웃");
		}
	}
	
	//로그아웃 창으로 이동
	@GetMapping("/customLogout")
	public void logoutGet() {
		log.info("로그아웃");
	}
	
	@PostMapping("/customLogout")
	public void logoutPost() {
		log.info("post 로그아웃");	
	}
	
	//id중복체크
	@GetMapping("/idCheckService")
	@ResponseBody
	public int idCheck(@RequestParam("userid") String id) {
		int result=service.idCheck(id);
		if(result == 1) {
			return 1;
		}else {
			return 0;
		}
	}
	
	
}
