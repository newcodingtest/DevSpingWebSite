package org.yoon.mapper;

import java.util.HashMap; 
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;



public interface GBoardMapper {

	//�Խñ� ��� ��ȸ
	public List<BoardVO> getListPaging(Criteria cri);
	//�Խñ� ���
	public void insert(BoardVO Board);
	//�۹�ȣ Ȯ�� �� �Խñ� ���
	public void insertSelectKey(BoardVO Board);
	//�Խñ� ����
	public BoardVO read(Long bno);
	//�Խñ� ����
	public int delete(Long bno);
	//�Խñ� ����
	public int update(BoardVO vo);
	//�Խñ� �� ����
	public int getTotal(Criteria cri);
	//��ȸ�� ����
	public int getMoreVisit(Long bno);
	//�Խñ� ��õ
	public void recomend(HashMap map);
	//�Խñ� ��õ�� ����
	public int getMoreRecomend(Object bno);
	//�Խñ� ��õ�� ����
	public int reduceRecomend(Object bno);
	//��õ���� ��ȸ
	public int checkRecomend(HashMap map);
	//��õ����ϱ�
	public void cancelRecomend(HashMap map);
	//�ֽű� ��������
	public List<BoardVO> getNewList();
	//����Ʈ�� ��������
	public List<BoardVO> getBestList();
	
}