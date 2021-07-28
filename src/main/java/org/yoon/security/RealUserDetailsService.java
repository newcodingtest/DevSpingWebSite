package org.yoon.security;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.yoon.domain.MemberVO;
import org.yoon.mapper.MemberMapper;
import org.yoon.security.domain.RealUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

//CustomUser ���� ��ȯ�� ���� ����
@Log4j

public class RealUserDetailsService implements UserDetailsService {

	@Setter(onMethod_=@Autowired )
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//��ť��Ƽ���� username�� ���� ���� ���̵�� �ν�
		log.warn("�������� �ε�: "+username);
		MemberVO vo=memberMapper.read(username);

		log.warn(vo);
		return vo==null ? null : new RealUser(vo);
	}

}
