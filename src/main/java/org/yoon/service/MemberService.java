package org.yoon.service;

import java.util.List; 

import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;


import org.yoon.domain.MemberVO;

public interface MemberService {
	
	//id�ߺ�üũ
	public int idCheck(String userid);
	//�̸��� �ߺ�üũ
	public int emailCheck(String useremail);
	//ȸ�� ���
	public void insert(MemberVO vo);
	//ȸ�� ���� ��������
	public MemberVO read(String userid);
	//ȸ�� Ż��
	public int delete(String userid);
	//ȸ�� ���� ����
	public int update(MemberVO vo);
	//ȸ�� ������ �������� ��������
	public List<BoardAttachVO>getAttachList(String userid);
	//ȸ�� ����¡�� ����Ʈ�� ��������
	public List<MemberVO> getList(Criteria cri);
	//ȸ�� ����Ʈ ����¡
	public int getTotal(Criteria cri);
	//���̹� ���� ����
	public MemberVO naverChk(MemberVO vo);
	//���̹� ���� �÷�����
	public int updateN(MemberVO vo);
	//���� �� ��,��� ��������
	public List<BoardVO> getAllboard(String userid);
	//���� �� �� ���� ���
	public int getAllboardCount(String userid);
	//�̸�+�̸��Ϸ� ���̵� ã��
	public MemberVO existUserId(MemberVO vo);
	//���̵�+�̸�+�̸��Ϸ� ��й�ȣ ã��
	public MemberVO existUserPw(MemberVO vo);
	//ȸ�� ��й�ȣ ����
	public int updatePw(MemberVO vo);
	
	
}
