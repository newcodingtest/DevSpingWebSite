package org.yoon.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yoon.domain.MemberVO;
import org.yoon.mapper.MemberMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{

	private MemberMapper mapper;
	
	private PasswordEncoder pwen;
	@Override
	public int idCheck(String userid) {
		
		return mapper.idChk(userid);
	}


	@Override
	public void insert(MemberVO vo) {
		log.info("==회원 등록==");
		String pwd=pwen.encode(vo.getUserpw());
		vo.setUserpw(pwd);
		mapper.insert(vo);
		
	}

}
