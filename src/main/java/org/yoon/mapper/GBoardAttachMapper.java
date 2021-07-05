package org.yoon.mapper;

import java.util.List; 

import org.yoon.domain.BoardAttachVO;



public interface GBoardAttachMapper {
	
	public void insert(BoardAttachVO vo);
	
	public void delete(String uuid);
	
	public List<BoardAttachVO> findBygno(Long bno);
	
	public void deleteAll(Long bno);
	
}