package org.yoon.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;


import org.yoon.domain.MemberVO;
import org.yoon.mapper.AttachMapper;
import org.yoon.mapper.BoardMapper;
import org.yoon.mapper.MemberMapper;
import org.yoon.mapper.MessageMapper;
import org.yoon.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{

	private MemberMapper mapper;
	private AttachMapper AttachMapper;
	private BoardMapper bMapper;
    private MessageMapper mMapper;
	
	private BCryptPasswordEncoder pwen;
	@Override
	public int idCheck(String userid) {
		
		return mapper.idChk(userid);
	}

	@Transactional
	@Override
	public void insert(MemberVO vo) {
		log.info("==회원 등록==");
		String pwd=pwen.encode(vo.getUserpw());
		vo.setUserpw(pwd);
		
		//네이버 연동으로 가입신청으 왔으면 네이버연동정보와 함께 회원가입
		if(vo.getNaverLogin()!=null) {
			mapper.insertN(vo);
		//아니면 일반적인 회원가입	
		}else {
			mapper.insert(vo);
		}
	}

	@Override
	public MemberVO read(String userid) {
		log.info("===회원 정보 가져오기==="+userid);
		return mapper.read(userid);
	}

	@Transactional
	@Override
	public int update(MemberVO vo) {
		// TODO Auto-generated method stub
		log.info("==수정된 회원정보==: "+vo);
		//프로필 변경하기 위해서 기존의 프로필 파일을 먼저 삭제해준다.
		AttachMapper.deleteUser(vo.getUserid());
		//변경 적용
		int cnt = mapper.update(vo);
		
		//변경 적용후 변경내용에 프로필 사진이 포함되어 있으면 프로필 내용도 새로 갱신해준다.
		if(cnt!=0 && vo.getAttachList()!=null && vo.getAttachList().size()>0) {
			vo.getAttachList().forEach(attach ->{
				attach.setUserid(vo.getUserid());
				AttachMapper.insertUser(attach);
			});
		}
		
		return cnt;
	}


	@Override
	public List<BoardAttachVO> getAttachList(String userid) {
		log.info(userid+"회원의 프로필 파일 가져오기");
		return AttachMapper.findByUser(userid);
	}

	@Override
	public MemberVO naverChk(MemberVO vo) {
		log.info("====네이버 연동 여부 확인======");
		return mapper.naverChk(vo);
	}

	@Override
	public int updateN(MemberVO vo) {
		log.info("====기존회원 네이버 연동 추가======");
		return mapper.updateN(vo);
	}

	@Override
	public int emailCheck(String useremail) {
		log.info("====이메일 확인======");
		return mapper.emailCheck(useremail);
	}

	@Override
	public List<MemberVO> getList(Criteria cri) {
		log.info("====회원 리스트 출력======");
		return mapper.getListPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("====전체 회원 숫자 출력======");
		return mapper.getTotal(cri);
	}

	@Override
	public int delete(String userid) {
		log.info("====회원 삭제======");
		//회원 프로필 삭제
		AttachMapper.deleteUser(userid);
		//회원 글 삭제=>오라클 종속제거 걸어둠->글 삭제시 포함된 댓글 삭제됨
		bMapper.deleteByUser(userid);
	
		return mapper.deleteUser(userid);
	}

	@Override
	public MemberVO existUserId(MemberVO vo) {
		log.info("====회원 아이디 찾기======");
		return mapper.existUserId(vo);
	}

	@Override
	public MemberVO existUserPw(MemberVO vo) {
		log.info("====회원 비밀번호 찾기======");
		return mapper.existUserPw(vo);
	}

	@Override
	public int updatePw(MemberVO vo) {
		log.info("====회원 비밀번호 수정======");
		String pwd=pwen.encode(vo.getUserpw());
		vo.setUserpw(pwd);
		return mapper.updatePw(vo);
	}








}
