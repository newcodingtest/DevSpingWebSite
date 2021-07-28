package org.yoon.service;

import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyVO;


public interface BoardService {
	
	//�� ���
	public void register(BoardVO board);
	//�� ��ȸ
	public BoardVO get(Long bno);
	//�� ����
	public int remove(Long bno);
	//�� ����
	public int modify(BoardVO board);
	//�� ����Ʈ ���+����¡ ����
	public List<BoardVO> getList(Criteria cri);
	//������ �� ���
	public List<BoardVO> getListByUser(Map<String, Object> map);
//	//����¡�� ���� �� ���
//	public List<BoardVO> getListPaging(Criteria cri);
	//����¡�� ���� �� ��ü ����
	public int getTotal(Criteria cri);
	//����¡�� ���� ���� �� �� ��ü ����
	public int getTotalByUser(String userid);
	//��ȸ��
	public int visit(Long bno);
	//�ش� ���� ÷������ ��������
	public List<BoardAttachVO>getAttachList(Long bno);
	//�ֽű� ��ȸ
	public List<BoardVO> getNewList();
		//����Ʈ�� ��ȸ
	public List<BoardVO> getBestList();
	//��õ�ϱ�
	public void recommend(HashMap map);
	//��õ���� ��ȸ
	public int checkRecommend(HashMap map);
	//��õ ���
	public void cancelRecommend(HashMap map);
	//���� �� ��(����,������) ��������
	public List<BoardVO> getNewboard(String userid);
		//���� �� ��(����,������) ��������
	public List<ReplyVO> getNewReply(String userid);
		//���� �� �� ���� ���
	public int getAllboardCount(String userid);
		//���� �� ��� ���� ���
	public int getAllReplyCount(String userid);
}
	
