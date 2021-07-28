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

//CustomUser 에서 변환된 값을 리턴
@Log4j

public class RealUserDetailsService implements UserDetailsService {

	@Setter(onMethod_=@Autowired )
	private MemberMapper memberMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//시큐리티에서 username은 실제 유저 아이디로 인식
		log.warn("유저정보 로드: "+username);
		MemberVO vo=memberMapper.read(username);

		log.warn(vo);
		return vo==null ? null : new RealUser(vo);
	}

}
