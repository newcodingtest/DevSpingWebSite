package org.yoon.service;

import java.util.HashMap; 
import java.util.List;

import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;


public interface GBoardService {

	//�Խñ� ���
	public void register(BoardVO bno);
	//�Խñ� �󼼺���
	public BoardVO get(Long bno);
	//�Խñ� ����
	public int modify(BoardVO bno);
	//�Խñ� ����
	public int delete(Long bno);
	//�Խñ� ��� ��ȸ
	public List<BoardVO> getList(Criteria cri);
	//����¡�� ���� �� ��ü ����
	public int getTotal(Criteria cri);
	//�۹�ȣ�� ÷������ ����Ʈ ��ȸ
	public List<BoardAttachVO> getAttachList(Long bno);
	//��õ�ϱ�
	public void recommend(HashMap map);
	//��õ���� ��ȸ
	public int checkRecommend(HashMap map);
	//��õ ���
	public void cancelRecommend(HashMap map);
	//�ֽű� ��ȸ
	public List<BoardVO> getNewList();
	//����Ʈ�� ��ȸ
	public List<BoardVO> getBestList();
}