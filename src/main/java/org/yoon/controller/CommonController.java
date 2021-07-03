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
	// �̸��� ����-
	@NonNull
	private JavaMailSender mailSender;
	//���̹�
	@NonNull
	private NaverLogin naverlogin;
	private String apiResult = null;

	// ���̹� �α��� ������ callbackȣ��
		@RequestMapping(value="/logincallback", method= {RequestMethod.GET,RequestMethod.POST})
		public String callBack(Model model, @RequestParam String code, @RequestParam String state, HttpSession session, HttpServletRequest request) throws Exception{
			OAuth2AccessToken oauthToken;
			oauthToken = naverlogin.getAccessToken(session, code, state);
			
			//�α��� ����� ���� �о���� 
			// String������ json������
			apiResult = naverlogin.getUserProfile(oauthToken);
			
			/**
			 * apiResult json ���� {"resultcode":"00", "message":"success",
			 * "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}}
			 **/
			
			//2. String ������ apiResult�� json���·� �ٲ� 
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(apiResult);
			JSONObject jsonObj = (JSONObject)obj;
			
			//3.������ �Ľ�
			// Top���� �ܰ� _response �Ľ�
			JSONObject response_obj = (JSONObject)jsonObj.get("response");

			// ���̹����� �ִ� ���� ID
			String naverId = (String) response_obj.get("id");
			// ���̹����� ������ ����� �̸�
			String naverNickname = (String) response_obj.get("nickname");
			// ���̹����� ������ �̸���
			String naverEmail = (String) response_obj.get("email");
			
			//���̹� ������� ��¥ID+@ �����̱� ������ ���ڿ� �߶� id���� �����ϴ� �۾��� ��ģ��.
			String target ="@";
			int target_num = naverEmail.indexOf(target);
			//���̹� ��¥ ID
			String newId=(String) naverEmail.substring(0,target_num);
			System.out.println(newId);
			
			MemberVO member = new MemberVO();
			member.setUserid(newId); //���̵�
			member.setUsername(naverNickname); //�г���
			member.setUseremail(naverEmail); //�̸���
			member.setNaverLogin(naverId); //���̹� ����id��ȣ
			
			
			//���̹��� ������ ȸ������ ã�� =>[���Ե� �̸���] �Ǵ� [���̹� ������ȣid]�� ��ȸ�Ͽ� ��
			MemberVO naverIdChk = service.naverChk(member);
		
			//1.���� Ȩ�������� ������ ������ ���°��=>��ϵ� ���̹� �̸���x,���̹�������ȣidx , ȸ�� �������� ����
			if (naverIdChk == null) {
					session.setAttribute("user", member);
					return "member/Register";
			//2.���Ե� �̸����� ������ ���̹����� ������ �ȵȰ��		
			}else if(naverIdChk.getNaverLogin() == null && naverIdChk.getUseremail() !=null) {
					//2-1 ���Ե� ������ ���̹� ���� ����
					service.updateN(member);
					//2-2 ���� �������� �ڵ� �α���
					//���̹� ����id��ȣ�� ���� ������ id���� �����Ͽ� �ڵ��α��� ����
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

			//3.�Ѵ� �ƴ϶�� ���̹��� ���� �� ������. ���̹� �α��ν� �ٷ� �α��ε�
			}else {
					//���Ե� ���¿��� ���̹����̵�=������Ʈ���̵� ����, Ȥ�� �ٸ����̵�����(���̹� ������ȣ�� idã��)
					//��ť��Ƽ�� �Ķ���ͷ� id���� �����ؼ� �α���ó���� �Ѵ�.
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

	// ajax�� �޾ƿ� �̸���
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheck(String email) {
		log.info("�̸��� ������ ���� Ȯ��");
		log.info("������ȣ: " + email);

		// ������ȣ ��������
		Random random = new Random();
		int CheckNum = random.nextInt(888888) + 111111;

		// ���� ��û�� �̸��Ͽ��� ������ȣ ������
		String setFrom = "pulpul8282@naver.com";
		String toMail = email;
		String title = "ȸ������ ���� �̸��� �Դϴ�.";
		String content = "Ȩ�������� �湮���ּż� �����մϴ�." + "<br><br>" + "���� ��ȣ��" + CheckNum + "�Դϴ�." + "<br>"
				+ "�ش� ������ȣ�� ������ȣ Ȯ�ζ��� �����Ͽ� �ּ���.";

		// �̸��� ���� �ڵ�
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
		log.info("������ȣ: " + num);

		return num;
	}

	// ������ ���� ��������
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(String userid) {
		log.info("getAttachList: " + userid);
		log.info(service.getAttachList(userid));

		return new ResponseEntity<>(service.getAttachList(userid), HttpStatus.OK);
	}

	// ���������� ����
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
		log.info("===�� Ȱ�� ��� Ȯ��===");
		String userid=principal.getName();
		
		//���� �� �ֱ� �� ����Ʈ ����(5��)
		model.addAttribute("user",service.getAllboard(userid));
		//���� ���� ���� ����
		model.addAttribute("cnt",service.getAllboardCount(userid));
		//���� �� �ֱ� ��� ����Ʈ ����(5��)
		 //model.addAttribute("reply",service.getAllreply(userid)); 
		
	}

	@PreAuthorize("isAuthenticated()")
	// ���������� â���� �̵�
	@GetMapping("myProfile")
	public void profile(Principal principal, Model model) {

		log.info("���������� â���� �̵�");
		log.info("�������̵�: " + principal.getName());
		String userid = principal.getName();
		MemberVO vo = service.read(userid);
		model.addAttribute("user", vo);
	}

	@PreAuthorize("isAuthenticated()")
	// ���������� â���� �̵�
	@GetMapping("modify")
	public void profile() {
		log.info("���������� ������������ �̵�");

	}

	// ȸ������ â���� �̵�
	@GetMapping("Register")
	public void Register(Model model, HttpSession session) {
		log.info("ȸ������ â���� �̵�");
		/* ���̹����̵�� ���� URL�� �����ϱ� ���Ͽ� naverLoginBOŬ������ getAuthorizationUrl�޼ҵ� ȣ�� */
		//String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		// https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
		// redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
		//log.info("���̹�: " + naverAuthUrl);
		// ���̹�
		//model.addAttribute("url", naverAuthUrl);
	}

	// ȸ�� ���
	@PostMapping("/Register")
	public String Register(MemberVO vo, RedirectAttributes rttr) {
		log.info("ȸ����� post");
		service.insert(vo);	

		return "redirect:/member/Login";
	}
	
	//ȸ�� Ż��
	@GetMapping("/delete")
	public String memDelete(Principal principal,RedirectAttributes rttr ) {
		//�α��� ���� ����� ���̵� ��������
		String userid=principal.getName();
		//���̵� �����ϴ��� db��ȸ
		MemberVO vo=service.read(userid);
		//���̵� �����ϸ�
		if(vo!=null) {
			//Ż�� �޼ҵ� ����� db������ member�� member_auth �� ���̺��� ���� ���Ӱ��迡 �ֱ⶧���� �⺻Ű ������ �ܷ�Ű �ڵ������� �־������
			service.delete(userid);
			SecurityContextHolder.clearContext();
			rttr.addFlashAttribute("mgs","Ż�� �Ϸ�Ǿ����ϴ�.");
		}else {
			rttr.addFlashAttribute("mgs","Ż�� �����Ͽ����ϴ�..");
		}
		
		return "redirect:/board/list";
		
	}

	// �α��� â���� �̵�
	@GetMapping("/Login")
	public void login(String error, String logout, Model model, HttpSession session) {
		log.info("error: " + error);
		log.info("logout: " + logout);

	    String naverUrl = naverlogin.getAuthorizationUrl(session);
	    model.addAttribute("naverUrl",naverUrl);
	}

	// �α׾ƿ� â���� �̵�
	@GetMapping("/Logout")
	public void logoutGet() {
		log.info("�α׾ƿ�");
	}

	@PostMapping("/Logout")
	public void logoutPost() {
		log.info("post �α׾ƿ�");
	}

	// id�ߺ�üũ
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
	
	
	//email �ߺ�üũ
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
	
	//ȸ������ �Ǵ� ���ٱ����� ���� ����� �α��ν� ���������� ����
	@GetMapping(value = "/err/denied-page")
	public String accessDenied(){
	    return "err/deniedPage";
	}
}
