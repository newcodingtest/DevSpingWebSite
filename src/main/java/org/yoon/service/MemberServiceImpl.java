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
		log.info("==ȸ�� ���==");
		String pwd=pwen.encode(vo.getUserpw());
		vo.setUserpw(pwd);
		
		//���̹� �������� ���Խ�û�� ������ ���̹����������� �Բ� ȸ������
		if(vo.getNaverLogin()!=null) {
			mapper.insertN(vo);
		//�ƴϸ� �Ϲ����� ȸ������	
		}else {
			mapper.insert(vo);
		}
	}

	@Override
	public MemberVO read(String userid) {
		log.info("===ȸ�� ���� ��������==="+userid);
		return mapper.read(userid);
	}

	@Transactional
	@Override
	public int update(MemberVO vo) {
		// TODO Auto-generated method stub
		log.info("==������ ȸ������==: "+vo);
		//������ �����ϱ� ���ؼ� ������ ������ ������ ���� �������ش�.
		AttachMapper.deleteUser(vo.getUserid());
		//���� ����
		int cnt = mapper.update(vo);
		
		//���� ������ ���泻�뿡 ������ ������ ���ԵǾ� ������ ������ ���뵵 ���� �������ش�.
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
		log.info(userid+"ȸ���� ������ ���� ��������");
		return AttachMapper.findByUser(userid);
	}

	@Override
	public MemberVO naverChk(MemberVO vo) {
		log.info("====���̹� ���� ���� Ȯ��======");
		return mapper.naverChk(vo);
	}

	@Override
	public int updateN(MemberVO vo) {
		log.info("====����ȸ�� ���̹� ���� �߰�======");
		return mapper.updateN(vo);
	}

	@Override
	public int emailCheck(String useremail) {
		log.info("====�̸��� Ȯ��======");
		return mapper.emailCheck(useremail);
	}

	@Override
	public List<MemberVO> getList(Criteria cri) {
		log.info("====ȸ�� ����Ʈ ���======");
		return mapper.getListPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("====��ü ȸ�� ���� ���======");
		return mapper.getTotal(cri);
	}

	@Override
	public int delete(String userid) {
		log.info("====ȸ�� ����======");
		//ȸ�� ������ ����
		AttachMapper.deleteUser(userid);
		//ȸ�� �� ����=>����Ŭ �������� �ɾ��->�� ������ ���Ե� ��� ������
		bMapper.deleteByUser(userid);
	
		return mapper.deleteUser(userid);
	}

	@Override
	public MemberVO existUserId(MemberVO vo) {
		log.info("====ȸ�� ���̵� ã��======");
		return mapper.existUserId(vo);
	}

	@Override
	public MemberVO existUserPw(MemberVO vo) {
		log.info("====ȸ�� ��й�ȣ ã��======");
		return mapper.existUserPw(vo);
	}

	@Override
	public int updatePw(MemberVO vo) {
		log.info("====ȸ�� ��й�ȣ ����======");
		String pwd=pwen.encode(vo.getUserpw());
		vo.setUserpw(pwd);
		return mapper.updatePw(vo);
	}








}
