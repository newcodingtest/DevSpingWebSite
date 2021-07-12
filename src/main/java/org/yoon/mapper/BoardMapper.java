package org.yoon.mapper;

import java.util.HashMap;  
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyVO;


public interface BoardMapper {
	
	//�ܼ� �� ���
	public List<BoardVO> getList();
	//�� ����¡ ���
	public List<BoardVO> getListPaging(Criteria cri);
	//�� ���
	public void insert(BoardVO board);
	//�� ���[��ϵ� �۹�ȣ Ȯ�ΰ���]
	public void insertSelectKey(BoardVO board);
	//�� ��ȸ
	public BoardVO read(Long bno);
	//�� ����
	public int delete(Long bno);
	//�� ����
	public int update(BoardVO board);
	//����¡�� ���� �� ��ü ����
	public int getTotal(Criteria cri);
	//��ȸ�� ����
	public int visit(Long bno);
	//��ۼ� ����
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount); 
	//�Խñ� ��õ
	public void recommend(HashMap map);
	//�Խñ� ��õ�� ����
	public int getMoreRecommend(Object bno);
	//�Խñ� ��õ�� ����
	public int reduceRecommend(Object bno);
	//��õ���� ��ȸ
	public int checkRecommend(HashMap map);
	//��õ����ϱ�
	public void cancelRecommend(HashMap map);
	//�ֽű� ��ȸ
	public List<BoardVO> getNewList();
	//����Ʈ�� ��ȸ
	public List<BoardVO> getBestList();

	//���� �� ��(����,������) ��������
		public List<BoardVO> getNewboard(String userid);
		//���� �� ��(����,������) ��������
		public List<ReplyVO> getNewReply(String userid);
		//���� �� �� ���� ���
		public int getAllboardCount(String userid);
		//���� �� ��� ���� ���
		public int getAllReplyCount(String userid);
	
}
