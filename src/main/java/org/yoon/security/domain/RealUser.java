package org.yoon.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.yoon.domain.MemberVO;
import org.yoon.mapper.MemberMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

//MemberVO 를 UserDetails 타입으로 변환
@Data
public class RealUser extends User {


	private static final long serialVersionUID =1L;
	
	private MemberVO member;
	
	public RealUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public RealUser(MemberVO vo) {
		super(vo.getUserid(), vo.getUserpw(), 
			vo.getAuthList().stream().map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
		
		this.member=vo;
	}

}
