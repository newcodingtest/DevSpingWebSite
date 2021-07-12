package org.yoon.mapper;

import java.util.List;

import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.MemberVO;

public interface MemberMapper {

	//1:N관계
	public MemberVO read(String userid);
	//페이징된 회원리스트 출력
	public List<MemberVO> getListPaging(Criteria cri);
	//페이징에 쓰일 회원 전체 수
	public int getTotal(Criteria cri);
	//회원탈퇴
	public int deleteUser(String userid);
	//id중복체크
	public int idChk(String userid);
	//이메일 중복체크
	public int emailCheck(String useremail);
	//회원가입
	public void insert(MemberVO vo);
	//네이버 연동회원가입
	public void insertN(MemberVO vo);
	//회원 수정
	public int update(MemberVO vo);
	//네이버 연동 컬럼수정
	public int updateN(MemberVO vo);
	//네이버 연동 여부
	public MemberVO naverChk(MemberVO vo);
	
	//이름+이메일로 아이디 존재 확인
	public MemberVO existUserId(MemberVO vo);
	//아이디+이름+이메일로 아이디 존재 확인
	public MemberVO existUserPw(MemberVO vo);
	//회원 비밀번호 변경
	public int updatePw(MemberVO vo);
	
}
