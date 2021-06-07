package org.yoon.mapper;

import org.yoon.domain.BoardVO;
import org.yoon.domain.MemberVO;

public interface MemberMapper {

	//1:N관계
	public MemberVO read(String userid);
	
	//id중복체크
	public int idChk(String userid);
	
	//회원가입
	public void insert(MemberVO vo);
}
