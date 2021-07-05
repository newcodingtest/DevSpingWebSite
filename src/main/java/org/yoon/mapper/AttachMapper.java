package org.yoon.mapper;

import java.util.List;  

import org.yoon.domain.AttachFileDTO;
import org.yoon.domain.BoardAttachVO;


public interface AttachMapper {
	
	public void insert(BoardAttachVO vo);
	
	public void insertUser(BoardAttachVO vo);
	
	public void delete(String uuid);
	
	public void deleteAll(Long bno);
	
	public void deleteUser(String userid);
	
	//�Խù� ��ȣ�� ÷������ ã��
	public List<BoardAttachVO> findByBno(Long bno);
	
	//ȸ�� ���̵�� ���������� ã��
	public List<BoardAttachVO> findByUser(String userid);
	
	public List<BoardAttachVO>getOldFiles();

}
