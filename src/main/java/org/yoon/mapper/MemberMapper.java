package org.yoon.mapper;

import java.util.List;

import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.MemberVO;

public interface MemberMapper {

	//1:N����
	public MemberVO read(String userid);
	//����¡�� ȸ������Ʈ ���
	public List<MemberVO> getListPaging(Criteria cri);
	//����¡�� ���� ȸ�� ��ü ��
	public int getTotal(Criteria cri);
	//ȸ��Ż��
	public int deleteUser(String userid);
	//id�ߺ�üũ
	public int idChk(String userid);
	//�̸��� �ߺ�üũ
	public int emailCheck(String useremail);
	//ȸ������
	public void insert(MemberVO vo);
	//���̹� ����ȸ������
	public void insertN(MemberVO vo);
	//ȸ�� ����
	public int update(MemberVO vo);
	//���̹� ���� �÷�����
	public int updateN(MemberVO vo);
	//���̹� ���� ����
	public MemberVO naverChk(MemberVO vo);
	
	//�̸�+�̸��Ϸ� ���̵� ���� Ȯ��
	public MemberVO existUserId(MemberVO vo);
	//���̵�+�̸�+�̸��Ϸ� ���̵� ���� Ȯ��
	public MemberVO existUserPw(MemberVO vo);
	//ȸ�� ��й�ȣ ����
	public int updatePw(MemberVO vo);
	
}
