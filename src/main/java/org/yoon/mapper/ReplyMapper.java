package org.yoon.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyVO;

public interface ReplyMapper {
	//ȸ���� �� ��� ���
	public List<ReplyVO> getListByUser(@Param("cri") Criteria cri,@Param("userid") String userid);
	//��� ���	
	public int insert(ReplyVO vo);
	//��� ��ȸ
	public ReplyVO read(Long rno);
	//��� ����
	public int delete(Long rno);
	//��� ����
	public int update(ReplyVO reply);
	//�ΰ��̻� ������ �Ķ���ͷ� �� 1.map 2.������ü 3.param
	public List<ReplyVO> getListPaging(@Param("cri") Criteria cri,@Param("bno") Long bno);
	//��� ���� �ľ�
	public int getCountByBno(Long bno);
	//�Խñ� ������ ���� ��� ����
	public int deleteBno(Long bno);
	//���� �� ��� ���� 
	public int getTotalByUser(String userid);
	
}
