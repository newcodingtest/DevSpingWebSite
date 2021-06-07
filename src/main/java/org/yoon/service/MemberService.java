package org.yoon.service;

import org.yoon.domain.BoardVO;
import org.yoon.domain.MemberVO;

public interface MemberService {
	
	//id중복체크
	public int idCheck(String userid);
	
	//회원 등록
	public void insert(MemberVO vo);

}
