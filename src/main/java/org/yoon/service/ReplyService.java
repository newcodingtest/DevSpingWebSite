package org.yoon.service;

import java.util.List;
import java.util.Map;

import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyPageDTO;
import org.yoon.domain.ReplyVO;

public interface ReplyService {
	
	//��� ���
	public int register(ReplyVO reply);
	//��� ��ȸ
	public ReplyVO get(Long rno);
	//��� ����
	public int remove(Long rno);
	//��� ����
	public int modify(ReplyVO reply);
	//��� ����Ʈ ���+����¡ ����
	public ReplyPageDTO getList(Criteria cri,Long rno);
	//������ e��� ���
	public List<ReplyVO> getListByUser(Criteria cri,String userid);
	//�Խñ� ��ȣ�� ��� ����
	public int deleteBno(Long bno);
	//���� �� ��� �� ��������
	public int getTotalByUser(String userid);

}
