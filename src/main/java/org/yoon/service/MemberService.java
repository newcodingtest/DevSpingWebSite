package org.yoon.service;

import java.util.List;

import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.GBoardVO;
import org.yoon.domain.MemberAttachVO;
import org.yoon.domain.MemberVO;

public interface MemberService {
	
	//id중복체크
	public int idCheck(String userid);
	//이메일 중복체크
	public int emailCheck(String useremail);
	//회원 등록
	public void insert(MemberVO vo);
	//회원 정보 가져오기
	public MemberVO read(String userid);
	//회원 정보 수정
	public int update(MemberVO vo);
	//회원 프로필 사진파일 가져오기
	public List<BoardAttachVO>getAttachList(String userid);
	//회원 페이징된 리스트로 가져오기
	public List<MemberVO> getList(Criteria cri);
	
	public int getTotal(Criteria cri);
	//네이버 연동 여부
	public MemberVO naverChk(MemberVO vo);
	//네이버 연동 컬럼수정
	public int updateN(MemberVO vo);
	
}
