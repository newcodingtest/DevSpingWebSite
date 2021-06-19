package org.yoon.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.GBoardVO;
import org.yoon.domain.MemberAttachVO;
import org.yoon.domain.MemberVO;
import org.yoon.mapper.AttachMapper;
import org.yoon.mapper.MemberMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{

	private MemberMapper mapper;
	private AttachMapper AttachMapper;
	
	private PasswordEncoder pwen;
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
		mapper.insert(vo);
		
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



}
