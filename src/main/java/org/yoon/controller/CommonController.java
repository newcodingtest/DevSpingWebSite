package org.yoon.controller;

import java.io.IOException;  

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import org.yoon.auth.Naver.NaverLogin;
import org.yoon.auth.Naver.NaverLoginApi;
import org.yoon.domain.BoardAttachVO;

import org.yoon.domain.MemberAttachVO;
import org.yoon.domain.MemberVO;
import org.yoon.domain.ReplyVO;
import org.yoon.mapper.MemberMapper;
import org.yoon.security.CustomUserDetailsService;
import org.yoon.security.domain.CustomUser;
import org.yoon.service.BoardService;
import org.yoon.service.MemberService;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.scribejava.core.model.OAuth2AccessToken;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member/*")
public class CommonController {
	@NonNull
	private MemberService service;
	@NonNull
	private CustomUserDetailsService cusd;
	// 이메일 인증-
	@NonNull
	private JavaMailSender mailSender;
	//네이버
	@NonNull
	private NaverLogin naverlogin;
	private String apiResult = null;

	// 네이버 로그인 성공시 callback호출
		@RequestMapping(value="/logincallback", method= {RequestMethod.GET,RequestMethod.POST})
		public String callBack(Model model, @RequestParam String code, @RequestParam String state, HttpSession session, HttpServletRequest request) throws Exception{
			OAuth2AccessToken oauthToken;
			oauthToken = naverlogin.getAccessToken(session, code, state);
			
			//로그인 사용자 정보 읽어오기 
			// String형식의 json데이터
			apiResult = naverlogin.getUserProfile(oauthToken);
			
			/**
			 * apiResult json 구조 {"resultcode":"00", "message":"success",
			 * "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
			 **/
			
			//2. String 형식인 apiResult를 json형태로 바꿈 
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(apiResult);
			JSONObject jsonObj = (JSONObject)obj;
			
			//3.데이터 파싱
			// Top레벨 단계 _response 파싱
			JSONObject response_obj = (JSONObject)jsonObj.get("response");

			// 네이버에서 주는 고유 ID
			String naverId = (String) response_obj.get("id");
			// 네이버에서 설정된 사용자 이름
			String naverNickname = (String) response_obj.get("nickname");
			// 네이버에서 설정된 이메일
			String naverEmail = (String) response_obj.get("email");
			
			//네이버 같은경우 진짜ID+@ 형식이기 때문에 문자열 잘라서 id값을 추출하는 작업을 펼친다.
			String target ="@";
			int target_num = naverEmail.indexOf(target);
			//네이버 진짜 ID
			String newId=(String) naverEmail.substring(0,target_num);
			System.out.println(newId);
			
			MemberVO member = new MemberVO();
			member.setUserid(newId); //아이디
			member.setUsername(naverNickname); //닉네임
			member.setUseremail(naverEmail); //이메일
			member.setNaverLogin(naverId); //네이버 고유id번호
			
			
			//네이버로 연동된 회원정보 찾기 =>[가입된 이메일] 또는 [네이버 고유번호id]를 조회하여 비교
			MemberVO naverIdChk = service.naverChk(member);
		
			//1.쌩판 홈페이지에 연동된 정보가 없는경우=>등록된 네이버 이메일x,네이버고유번호idx , 회원 가입절차 시작
			if (naverIdChk == null) {
					session.setAttribute("user", member);
					return "member/Register";
			//2.가입된 이메일은 있으나 네이버와의 연동이 안된경우		
			}else if(naverIdChk.getNaverLogin() == null && naverIdChk.getUseremail() !=null) {
					//2-1 가입된 계정에 네이버 연동 진행
					service.updateN(member);
					//2-2 연동 끝났으면 자동 로그인
					//네이버 고유id번호를 가진 계정의 id값을 추출하여 자동로그인 설정
					String id=service.naverChk(member).getUserid();
					UserDetails navervo = (UserDetails)cusd.loadUserByUsername(id);
					System.out.println(id);
					Authentication authentication = new UsernamePasswordAuthenticationToken(navervo, navervo.getPassword(),
							navervo.getAuthorities());
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(authentication);
					session = request.getSession(true);
					session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
					return "redirect:/board/list";

			//3.둘다 아니라면 네이버로 가입 된 상태임. 네이버 로그인시 바로 로그인됨
			}else {
					//가입된 상태에서 네이버아이디=웹사이트아이디 인지, 혹은 다른아이디인지(네이버 고유번호로 id찾음)
					//시큐리티의 파라미터로 id값을 결정해서 로그인처리를 한다.
					String id=service.naverChk(member).getUserid()==null?newId:service.naverChk(member).getUserid();
					UserDetails navervo = (UserDetails)cusd.loadUserByUsername(id);
					System.out.println(newId);
					Authentication authentication = new UsernamePasswordAuthenticationToken(navervo, navervo.getPassword(),
							navervo.getAuthorities());
					SecurityContext securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(authentication);
					session = request.getSession(true);
					session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
					return "redirect:/board/list";
			}
		}

	// ajax로 받아온 이메일
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheck(String email) {
		log.info("이메일 데이터 전송 확인");
		log.info("인증번호: " + email);

		// 인증번호 난수생성
		Random random = new Random();
		int CheckNum = random.nextInt(888888) + 111111;

		// 인증 요청한 이메일에게 인증번호 보내기
		String setFrom = "pulpul8282@naver.com";
		String toMail = email;
		String title = "회원가입 인증 이메일 입니다.";
		String content = "홈페이지를 방문해주셔서 감사합니다." + "<br><br>" + "인증 번호는" + CheckNum + "입니다." + "<br>"
				+ "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

		// 이메일 전송 코드
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String num = Integer.toString(CheckNum);
		log.info("인증번호: " + num);

		return num;
	}

	// 프로필 사진 가져오기
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(String userid) {
		log.info("getAttachList: " + userid);
		log.info(service.getAttachList(userid));

		return new ResponseEntity<>(service.getAttachList(userid), HttpStatus.OK);
	}

	// 마이프로필 수정
	@PreAuthorize("isAuthenticated()")
	@PostMapping("modify")
	public String modify(MemberVO vo, RedirectAttributes rttr) {
		log.info("/modify");
		int result = service.update(vo);
		if (result > 0) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/member/myProfile";
	}
	
	@GetMapping("/checkList")
	public void checkList(Principal principal,Model model) {
		log.info("===내 활동 기록 확인===");
		String userid=principal.getName();
		
		//내가 쓴 최근 글 리스트 전달(5개)
		model.addAttribute("user",service.getAllboard(userid));
		//내가 쓴글 개수 전달
		model.addAttribute("cnt",service.getAllboardCount(userid));
		//내가 쓴 최근 댓글 리스트 전달(5개)
		 //model.addAttribute("reply",service.getAllreply(userid)); 
		
	}

	@PreAuthorize("isAuthenticated()")
	// 마이프로필 창으로 이동
	@GetMapping("myProfile")
	public void profile(Principal principal, Model model) {

		log.info("마이프로필 창으로 이동");
		log.info("유저아이디: " + principal.getName());
		String userid = principal.getName();
		MemberVO vo = service.read(userid);
		model.addAttribute("user", vo);
	}

	@PreAuthorize("isAuthenticated()")
	// 마이프로필 창으로 이동
	@GetMapping("modify")
	public void profile() {
		log.info("마이프로필 수정페이지로 이동");

	}

	// 회원가입 창으로 이동
	@GetMapping("Register")
	public void Register(Model model, HttpSession session) {
		log.info("회원가입 창으로 이동");
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
		//String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		// https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
		// redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
		//log.info("네이버: " + naverAuthUrl);
		// 네이버
		//model.addAttribute("url", naverAuthUrl);
	}

	// 회원 등록
	@PostMapping("/Register")
	public String Register(MemberVO vo, RedirectAttributes rttr) {
		log.info("회원등록 post");
		service.insert(vo);	

		return "redirect:/member/Login";
	}
	
	//회원 탈퇴
	@GetMapping("/delete")
	public String memDelete(Principal principal,RedirectAttributes rttr ) {
		//로그인 중인 사용자 아이디 가져오기
		String userid=principal.getName();
		//아이디 존재하는지 db조회
		MemberVO vo=service.read(userid);
		//아이디가 존재하면
		if(vo!=null) {
			//탈퇴 메소드 실행시 db내에선 member와 member_auth 두 테이블이 서로 종속관계에 있기때문에 기본키 삭제시 외래키 자동삭제를 넣어줘야함
			service.delete(userid);
			SecurityContextHolder.clearContext();
			rttr.addFlashAttribute("mgs","탈퇴가 완료되었습니다.");
		}else {
			rttr.addFlashAttribute("mgs","탈퇴에 실패하였습니다..");
		}
		
		return "redirect:/board/list";
		
	}

	// 로그인 창으로 이동
	@GetMapping("/Login")
	public void login(String error, String logout, Model model, HttpSession session) {
		log.info("error: " + error);
		log.info("logout: " + logout);

	    String naverUrl = naverlogin.getAuthorizationUrl(session);
	    model.addAttribute("naverUrl",naverUrl);
	}

	// 로그아웃 창으로 이동
	@GetMapping("/Logout")
	public void logoutGet() {
		log.info("로그아웃");
	}

	@PostMapping("/Logout")
	public void logoutPost() {
		log.info("post 로그아웃");
	}

	// id중복체크
	@GetMapping("/idCheckService")
	@ResponseBody
	public int idCheck(@RequestParam("userid") String id) {
		int result = service.idCheck(id);
		if (result == 1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	
	//email 중복체크
	@GetMapping("/emailCheckService")
	@ResponseBody
	public int emailCheck(@RequestParam("useremail") String useremail) {
		int result = service.emailCheck(useremail);
		if (result == 1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	//회원정보 또는 접근권한이 없는 사용자 로그인시 에러페이지 송출
	@GetMapping(value = "/err/denied-page")
	public String accessDenied(){
	    return "err/deniedPage";
	}
}
